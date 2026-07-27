package com.mastermarisa.maid_restaurant.init.registry;

import com.mastermarisa.maid_restaurant.init.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;

public final class CommonRegistry {
    private CommonRegistry() {
    }

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .register(entries -> {
                    entries.accept(ModItems.ORDER_MENU.get());
                    entries.accept(ModItems.ORDER_ITEM.get());
                });
    }
}
