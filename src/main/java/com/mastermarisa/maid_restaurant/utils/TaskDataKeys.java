package com.mastermarisa.maid_restaurant.utils;

import cn.sh1rocu.touhoulittlemaid.api.entity.data.TaskDataKey;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Small adapter for TLM's 1.21.11 task-data API. The old NeoForge port used
 * AttachmentType for these values; Fabric TLM stores them as TaskDataKey
 * entries owned by EntityMaid.
 */
public final class TaskDataKeys {
    private TaskDataKeys() {
    }

    public static <T> TaskDataKey<T> create(String path, Supplier<T> factory,
                                             Function<T, CompoundTag> writer,
                                             Function<CompoundTag, T> reader) {
        Identifier id = Identifier.fromNamespaceAndPath("maid_restaurant", path);
        return new TaskDataKey<>() {
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
    }
}
