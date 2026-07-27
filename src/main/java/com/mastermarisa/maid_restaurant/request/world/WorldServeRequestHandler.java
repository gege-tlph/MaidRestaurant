package com.mastermarisa.maid_restaurant.request.world;

import com.mastermarisa.maid_restaurant.api.request.RequestHandler;
import com.mastermarisa.maid_restaurant.request.ServeRequest;
import com.mastermarisa.maid_restaurant.utils.TaskDataKeys;
import cn.sh1rocu.touhoulittlemaid.api.entity.data.TaskDataKey;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

public class WorldServeRequestHandler extends RequestHandler<ServeRequest> {
    @Override
    protected ServeRequest fromCompound(HolderLookup.Provider provider, CompoundTag tag) {
        ServeRequest request = new ServeRequest();
        request.deserializeNBT(provider,tag);
        return request;
    }

    public static final TaskDataKey<WorldServeRequestHandler> TYPE = TaskDataKeys.create(
            "world_serve_request_handler", WorldServeRequestHandler::new,
            value -> value.serializeNBT(null),
            tag -> {
                WorldServeRequestHandler value = new WorldServeRequestHandler();
                value.deserializeNBT(null, tag);
                return value;
            });
    public static final AttachmentType<WorldServeRequestHandler> ATTACHMENT = AttachmentRegistry.createDefaulted(
            net.minecraft.resources.Identifier.fromNamespaceAndPath("maid_restaurant", "world_serve_request_handler"),
            WorldServeRequestHandler::new);
}
