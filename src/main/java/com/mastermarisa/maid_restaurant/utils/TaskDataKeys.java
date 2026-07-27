package com.mastermarisa.maid_restaurant.utils;

import cn.sh1rocu.touhoulittlemaid.api.entity.data.TaskDataKey;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Small adapter for TLM's 1.21.11 task-data API. The old NeoForge port used
 * AttachmentType for these values; Fabric TLM stores them as TaskDataKey
 * entries owned by EntityMaid.
 */
public final class TaskDataKeys {
    private static final Map<TaskDataKey<?>, Supplier<?>> DEFAULTS = new IdentityHashMap<>();

    private TaskDataKeys() {
    }

    public static <T> TaskDataKey<T> create(String path, Supplier<T> factory,
                                             Function<T, CompoundTag> writer,
                                             Function<CompoundTag, T> reader) {
        Identifier id = Identifier.fromNamespaceAndPath("maid_restaurant", path);
        TaskDataKey<T> key = new TaskDataKey<>() {
            @Override
            public Identifier getKey() {
                return id;
            }

            @Override
            public CompoundTag writeSaveData(T data) {
                return writer.apply(data);
            }

            @Override
            public T readSaveData(CompoundTag compound) {
                return reader.apply(compound);
            }
        };
        DEFAULTS.put(key, factory);
        return key;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getOrCreate(EntityMaid maid, TaskDataKey<T> key) {
        Supplier<T> factory = (Supplier<T>) DEFAULTS.get(key);
        if (factory == null) {
            throw new IllegalArgumentException("No default factory registered for " + key.getKey());
        }
        T current = maid.getData(key);
        return current != null ? current : maid.getOrCreateData(key, factory.get());
    }
}
