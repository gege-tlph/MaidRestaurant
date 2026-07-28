package com.mastermarisa.maid_restaurant.mixin;

import net.minecraft.world.inventory.RecipeBookType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Farmer's Delight Refabricated 3.4.9 also resolves its missing enum extension
 * from common code when the recipe book is synchronized on world join.
 */
@Pseudo
@Mixin(targets = "vectorwing.farmersdelight.refabricated.FDRecipeBookTypes")
public abstract class FarmersDelightRecipeBookTypeMixin {
    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/inventory/RecipeBookType;valueOf(Ljava/lang/String;)Lnet/minecraft/world/inventory/RecipeBookType;"
            ),
            require = 0
    )
    private static RecipeBookType maidRestaurant$allowMissingRecipeBookType(String name) {
        try {
            return RecipeBookType.valueOf(name);
        } catch (IllegalArgumentException exception) {
            if ("FARMERSDELIGHT_COOKING".equals(name)) {
                return RecipeBookType.CRAFTING;
            }
            throw exception;
        }
    }
}
