package com.mastermarisa.maid_restaurant.compat.farmersdelight;

import cn.sh1rocu.touhoulittlemaid.util.itemhandler.IItemHandler;
import net.minecraft.world.item.ItemStack;
import vectorwing.farmersdelight.refabricated.inventory.ItemStackHandler;

final class FarmersItemHandlerAdapter implements IItemHandler {
    private final ItemStackHandler delegate;

    FarmersItemHandlerAdapter(ItemStackHandler delegate) {
        this.delegate = delegate;
    }

    @Override public int getSlots() { return delegate.getSlotCount(); }
    @Override public ItemStack getStackInSlot(int slot) { return delegate.getStackInSlot(slot); }
    @Override public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) { return delegate.insertItem(slot, stack, simulate); }
    @Override public ItemStack extractItem(int slot, int amount, boolean simulate) { return delegate.extractItem(slot, amount, simulate); }
    @Override public int getSlotLimit(int slot) { return delegate.getSlotLimit(slot); }
    @Override public boolean isItemValid(int slot, ItemStack stack) { return delegate.isItemValid(slot, stack); }
}
