package com.mastermarisa.maid_restaurant.request.world;

import com.mastermarisa.maid_restaurant.api.request.RequestHandler;
import com.mastermarisa.maid_restaurant.request.CookRequest;
import com.mastermarisa.maid_restaurant.utils.TaskDataKeys;
import cn.sh1rocu.touhoulittlemaid.api.entity.data.TaskDataKey;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

public class WorldCookRequestHandler extends RequestHandler<CookRequest> {
    @Override
    protected CookRequest fromCompound(HolderLookup.Provider provider, CompoundTag tag) {
        CookRequest request = new CookRequest();
        request.deserializeNBT(provider,tag);
        return request;
    }

    public static final TaskDataKey<WorldCookRequestHandler> TYPE = TaskDataKeys.create(
            "world_cook_request_handler", WorldCookRequestHandler::new,
            value -> value.serializeNBT(null),
            tag -> {
                WorldCookRequestHandler value = new WorldCookRequestHandler();
                value.deserializeNBT(null, tag);
                return value;
            });
    public static final AttachmentType<WorldCookRequestHandler> ATTACHMENT = AttachmentRegistry.createDefaulted(
            net.minecraft.resources.Identifier.fromNamespaceAndPath("maid_restaurant", "world_cook_request_handler"),
            WorldCookRequestHandler::new);
}
