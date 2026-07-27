package com.mastermarisa.maid_restaurant.init;

import com.mastermarisa.maid_restaurant.MaidRestaurant;
import com.mastermarisa.maid_restaurant.item.OrderItem;
import com.mastermarisa.maid_restaurant.item.OrderMenuItem;
import com.mastermarisa.maid_restaurant.utils.RegistryRef;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import java.util.function.Function;

public final class ModItems {
    public static final RegistryRef<Item> ORDER_MENU = new RegistryRef<>(register("order_menu", OrderMenuItem::new));
    public static final RegistryRef<Item> ORDER_ITEM = new RegistryRef<>(register("order_item", OrderItem::new));

    private static Item register(String path, Function<Identifier, Item> factory) {
        Identifier id = Identifier.fromNamespaceAndPath(MaidRestaurant.MOD_ID, path);
        return Registry.register(BuiltInRegistries.ITEM, id, factory.apply(id));
    }

    public static void register() {
        // Static fields perform registration before common initialization.
    }
}
