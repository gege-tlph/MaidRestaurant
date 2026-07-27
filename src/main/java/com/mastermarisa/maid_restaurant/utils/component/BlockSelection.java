package com.mastermarisa.maid_restaurant.utils.component;

import com.mastermarisa.maid_restaurant.utils.EncodeUtils;
import cn.sh1rocu.touhoulittlemaid.api.entity.data.TaskDataKey;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;

public class BlockSelection {
    public List<BlockPos> menu = new ArrayList<>();
    public List<BlockPos> order = new ArrayList<>();

    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();
        tag.putLongArray("menu", EncodeUtils.encode(menu));
        tag.putLongArray("order", EncodeUtils.encode(order));
        return tag;
    }

    public void deserialize(CompoundTag tag) {
        if (tag.contains("menu")) {
            menu = new ArrayList<>(EncodeUtils.decode(tag.getLongArray("menu").orElse(new long[0])));
        }
        if (tag.contains("order")) {
            order = new ArrayList<>(EncodeUtils.decode(tag.getLongArray("order").orElse(new long[0])));
        }
    }

    public static final TaskDataKey<BlockSelection> TYPE = new TaskDataKey<>() {
        @Override
        public net.minecraft.resources.Identifier getKey() {
            return net.minecraft.resources.Identifier.fromNamespaceAndPath("maid_restaurant", "block_selection");
        }

        @Override
        public CompoundTag writeSaveData(BlockSelection data) {
            return data.serialize();
        }

        @Override
        public BlockSelection readSaveData(CompoundTag compound) {
            BlockSelection value = new BlockSelection();
            value.deserialize(compound);
            return value;
        }
    };

    public static final AttachmentType<BlockSelection> ATTACHMENT = AttachmentRegistry.createDefaulted(
            net.minecraft.resources.Identifier.fromNamespaceAndPath("maid_restaurant", "block_selection"),
            BlockSelection::new);
}
