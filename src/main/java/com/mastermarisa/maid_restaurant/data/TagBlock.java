package com.mastermarisa.maid_restaurant.data;

import com.mastermarisa.maid_restaurant.MaidRestaurant;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;

public final class TagBlock {
    public static final TagKey<Block> STORAGE_BLOCK = TagKey.create(Registries.BLOCK, MaidRestaurant.resourceLocation("storage_block"));
    public static final TagKey<Block> SIT_BLOCK = TagKey.create(Registries.BLOCK, MaidRestaurant.resourceLocation("sit_block"));
    public static final TagKey<Block> SERVE_MEAL_BLOCK = TagKey.create(Registries.BLOCK, MaidRestaurant.resourceLocation("serve_blockmeal_block"));

    public static void register() {
    }
}
