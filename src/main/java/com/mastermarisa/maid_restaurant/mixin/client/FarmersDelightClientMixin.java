package com.mastermarisa.maid_restaurant.mixin.client;

import net.minecraft.client.gui.screens.recipebook.SearchRecipeBookCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Farmer's Delight Refabricated 3.4.9 validates an enum extension that its
 * bundled early-riser fails to add on 1.21.11. The looked-up value is discarded,
 * so falling back to the vanilla crafting search category preserves startup
 * without changing recipe-book behavior used by Maid Restaurant.
 */
@Pseudo
@Mixin(targets = "vectorwing.farmersdelight.client.FarmersDelightClient")
public abstract class FarmersDelightClientMixin {
    @Redirect(
            method = "onInitializeClient",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/recipebook/SearchRecipeBookCategory;valueOf(Ljava/lang/String;)Lnet/minecraft/client/gui/screens/recipebook/SearchRecipeBookCategory;"
            ),
            require = 0
    )
    private SearchRecipeBookCategory maidRestaurant$allowMissingSearchCategory(String name) {
        try {
            return SearchRecipeBookCategory.valueOf(name);
        } catch (IllegalArgumentException exception) {
            if ("FARMERSDELIGHT_COOKING".equals(name)) {
                return SearchRecipeBookCategory.CRAFTING;
            }
            throw exception;
        }
    }
}
