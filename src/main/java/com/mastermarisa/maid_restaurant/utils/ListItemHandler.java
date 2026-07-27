package com.mastermarisa.maid_restaurant.utils;

import cn.sh1rocu.touhoulittlemaid.util.itemhandler.IItemHandler;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/** Adapter for the list-backed inventories used by Kaleidoscope Cookery. */
public final class ListItemHandler implements IItemHandler {
    private final List<ItemStack> items;

    public ListItemHandler(List<ItemStack> items) {
        this.items = items;
    }

    @Override public int getSlots() { return items.size(); }
    @Override public ItemStack getStackInSlot(int slot) { return items.get(slot); }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (stack.isEmpty() || !isItemValid(slot, stack)) return stack;
        ItemStack current = items.get(slot);
        int limit = Math.min(getSlotLimit(slot), stack.getMaxStackSize());
        if (!current.isEmpty() && !ItemStack.isSameItemSameComponents(current, stack)) return stack;
        int room = limit - current.getCount();
        if (room <= 0) return stack;
        int moved = Math.min(room, stack.getCount());
        if (!simulate) {
            if (current.isEmpty()) items.set(slot, stack.copyWithCount(moved));
            else current.grow(moved);
        }
        return stack.copyWithCount(stack.getCount() - moved);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount <= 0) return ItemStack.EMPTY;
        ItemStack current = items.get(slot);
        if (current.isEmpty()) return ItemStack.EMPTY;
        int moved = Math.min(amount, current.getCount());
        return simulate ? current.copyWithCount(moved) : current.split(moved);
    }

    @Override public int getSlotLimit(int slot) { return 64; }
    @Override public boolean isItemValid(int slot, ItemStack stack) { return true; }
}
