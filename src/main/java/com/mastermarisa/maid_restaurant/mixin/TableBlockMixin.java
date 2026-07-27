package com.mastermarisa.maid_restaurant.mixin;

import com.github.ysbbbbbb.kaleidoscopecookery.block.decoration.TableBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.decoration.TableBlockEntity;
import com.mastermarisa.maid_restaurant.data.TagItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {TableBlock.class},remap = false)
public class TableBlockMixin {
    @Inject(method = "useWithOther", at = @At("HEAD"), cancellable = true)
    private void useWithOther(Level level, BlockPos pos, Player player, TableBlockEntity table, ItemStack itemInHand, CallbackInfoReturnable<InteractionResult> cir) {
        if (itemInHand.is(TagItem.TABLE_BLACKLIST)) {
            cir.setReturnValue(InteractionResult.FAIL);
            cir.cancel();
        }
    }
}
