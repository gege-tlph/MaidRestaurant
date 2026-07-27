package com.mastermarisa.maid_restaurant.init;

import com.mastermarisa.maid_restaurant.MaidRestaurant;
import com.mastermarisa.maid_restaurant.entity.SitEntity;
import com.mastermarisa.maid_restaurant.utils.RegistryRef;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import java.util.Optional;

public final class ModEntities {
    public static final RegistryRef<MemoryModuleType<PositionTracker>> TARGET_POS =
            memory("target_pos", new MemoryModuleType<>(Optional.empty()));
    public static final RegistryRef<MemoryModuleType<Integer>> TARGET_TYPE =
            memory("target_type", new MemoryModuleType<>(Optional.of(Codec.INT)));
    public static final RegistryRef<MemoryModuleType<PositionTracker>> CACHED_WORK_BLOCK =
            memory("cached_work_block", new MemoryModuleType<>(Optional.empty()));
    public static final RegistryRef<MemoryModuleType<PositionTracker>> CHAIR_POS =
            memory("chair_pos", new MemoryModuleType<>(Optional.empty()));

    public static final RegistryRef<EntityType<Entity>> SIT_ENTITY = new RegistryRef<>(
            Registry.register(BuiltInRegistries.ENTITY_TYPE,
                    Identifier.fromNamespaceAndPath(MaidRestaurant.MOD_ID, "sit_entity"),
                    SitEntity.TYPE));

    private static <T> RegistryRef<MemoryModuleType<T>> memory(String id, MemoryModuleType<T> value) {
        return new RegistryRef<>(Registry.register(BuiltInRegistries.MEMORY_MODULE_TYPE,
                Identifier.fromNamespaceAndPath(MaidRestaurant.MOD_ID, id), value));
    }

    public static void register() {
        // Calling this method initializes the static Fabric registry entries.
    }
}
