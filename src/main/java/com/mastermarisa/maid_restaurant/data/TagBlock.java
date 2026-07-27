package com.mastermarisa.maid_restaurant.data;

import com.mastermarisa.maid_restaurant.MaidRestaurant;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.TagKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public final class TagBlock extends FabricTagProvider.BlockTagProvider {
    public static final TagKey<Block> STORAGE_BLOCK = TagKey.create(Registries.BLOCK, MaidRestaurant.resourceLocation("storage_block"));
    public static final TagKey<Block> SIT_BLOCK = TagKey.create(Registries.BLOCK, MaidRestaurant.resourceLocation("sit_block"));
    public static final TagKey<Block> SERVE_MEAL_BLOCK = TagKey.create(Registries.BLOCK, MaidRestaurant.resourceLocation("serve_blockmeal_block"));

    public TagBlock(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        valueLookupBuilder(STORAGE_BLOCK)
                .add(Blocks.CHEST)
                .add(Blocks.BARREL);

        getOrCreateRawBuilder(SIT_BLOCK)
                .addOptionalTag(Identifier.parse("kaleidoscope_cookery:cook_stool"))
                .addOptionalTag(Identifier.parse("kaleidoscope_cookery:chair"));

        getOrCreateRawBuilder(SERVE_MEAL_BLOCK)
                .addOptionalTag(Identifier.parse("kaleidoscope_cookery:table"))
                .addOptionalElement(Identifier.parse("kaleidoscope_cookery:fruit_basket"));
    }
}
