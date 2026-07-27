package com.mastermarisa.maid_restaurant.data;

import com.mastermarisa.maid_restaurant.MaidRestaurant;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

public final class TagItem {
    public static final TagKey<Item> TABLE_BLACKLIST = TagKey.create(Registries.ITEM, MaidRestaurant.resourceLocation("table_blacklist"));
    public static final TagKey<Item> BASKET_BLACKLIST = TagKey.create(Registries.ITEM, MaidRestaurant.resourceLocation("basket_blacklist"));
    public static final TagKey<Item> BRAISED_FISH_INGREDIENT = TagKey.create(Registries.ITEM, MaidRestaurant.resourceLocation("braised_fish_ingredient"));

    public static void register() {
    }
}
