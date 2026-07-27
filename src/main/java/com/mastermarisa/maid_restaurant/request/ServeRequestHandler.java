package com.mastermarisa.maid_restaurant.request;

import com.mastermarisa.maid_restaurant.api.request.RequestHandler;
import com.mastermarisa.maid_restaurant.utils.TaskDataKeys;
import cn.sh1rocu.touhoulittlemaid.api.entity.data.TaskDataKey;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

public class ServeRequestHandler extends RequestHandler<ServeRequest> {
    @Override
    protected ServeRequest fromCompound(HolderLookup.Provider provider, CompoundTag tag) {
        ServeRequest request = new ServeRequest();
        request.deserializeNBT(provider,tag);
        return request;
    }

    public static final TaskDataKey<ServeRequestHandler> TYPE = TaskDataKeys.create(
            "serve_request_handler", ServeRequestHandler::new,
            value -> value.serializeNBT(null),
            tag -> {
                ServeRequestHandler value = new ServeRequestHandler();
                value.deserializeNBT(null, tag);
                return value;
            });
}
