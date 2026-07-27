package com.mastermarisa.maid_restaurant.data;

import com.mastermarisa.maid_restaurant.MaidRestaurant;
import com.mastermarisa.maid_restaurant.init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.TagKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;

public final class TagItem extends FabricTagProvider.ItemTagProvider {
    public static final TagKey<Item> TABLE_BLACKLIST = TagKey.create(Registries.ITEM, MaidRestaurant.resourceLocation("table_blacklist"));
    public static final TagKey<Item> BASKET_BLACKLIST = TagKey.create(Registries.ITEM, MaidRestaurant.resourceLocation("basket_blacklist"));
    public static final TagKey<Item> BRAISED_FISH_INGREDIENT = TagKey.create(Registries.ITEM, MaidRestaurant.resourceLocation("braised_fish_ingredient"));

    public TagItem(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        valueLookupBuilder(TABLE_BLACKLIST)
                .add(ModItems.ORDER_MENU.get())
                .add(ModItems.ORDER_ITEM.get());

        valueLookupBuilder(BASKET_BLACKLIST)
                .add(ModItems.ORDER_MENU.get())
                .add(ModItems.ORDER_ITEM.get());

        getOrCreateRawBuilder(BRAISED_FISH_INGREDIENT)
                .addOptionalTag(Identifier.parse("c:foods/raw_cod"))
                .addOptionalTag(Identifier.parse("c:foods/raw_salmon"));
    }
}
