package com.mastermarisa.maid_restaurant.utils;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * Compatibility holder exposing the old DeferredHolder#get()/toStack() style
 * to the rest of the addon while registrations use Fabric's direct registry.
 */
public final class RegistryRef<T> {
    private final T value;

    public RegistryRef(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public ItemStack toStack() {
        if (value instanceof Item item) {
            return new ItemStack(item);
        }
        throw new IllegalStateException("RegistryRef does not contain an item");
    }
}
