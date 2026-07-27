package com.mastermarisa.maid_restaurant.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import cn.sh1rocu.touhoulittlemaid.util.itemhandler.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface IMaidStorage {
    String getUID();

    ItemStack getIcon();

    boolean isValid(Level level, BlockPos pos);

    @Nullable IItemHandler getHandler(Level level, BlockPos pos);

    ItemStack extract(Level level, BlockPos pos, int slot, int amount, boolean simulate);

    ItemStack insert(Level level, BlockPos pos, ItemStack stack, boolean simulate);
}
