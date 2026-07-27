package com.mastermarisa.maid_restaurant.init;

import com.mastermarisa.maid_restaurant.MaidRestaurant;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;

public final class ModDataComponents {
    public static final DataComponentType<Boolean> ORDER_ITEM_HAS_REQUESTS = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath(MaidRestaurant.MOD_ID, "order_item_has_requests"),
            DataComponentType.<Boolean>builder()
                    .persistent(Codec.BOOL)
                    .networkSynchronized(ByteBufCodecs.BOOL)
                    .build()
    );

    private ModDataComponents() {
    }

    public static void register() {
        // Calling this method initializes the static Fabric registry entry.
    }
}
