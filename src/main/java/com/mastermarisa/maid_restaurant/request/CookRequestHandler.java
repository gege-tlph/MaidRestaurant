package com.mastermarisa.maid_restaurant.request;

import com.mastermarisa.maid_restaurant.api.request.RequestHandler;
import com.mastermarisa.maid_restaurant.utils.TaskDataKeys;
import cn.sh1rocu.touhoulittlemaid.api.entity.data.TaskDataKey;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;

public class CookRequestHandler extends RequestHandler<CookRequest> {
    @Override
    public @Nullable CookRequest removeFirst() {
        if (requests.isEmpty()) return null;
        CookRequest request = requests.removeFirst();
        if (request.attributes.cycle()) {
            CookRequest toInsert = request.copy();
            requests.add(toInsert);
            toInsert.remain = toInsert.requested;
        }
        return request;
    }

    @Override
    protected CookRequest fromCompound(HolderLookup.Provider provider, CompoundTag tag) {
        CookRequest request = new CookRequest();
        request.deserializeNBT(provider,tag);
        return request;
    }

    public static final TaskDataKey<CookRequestHandler> TYPE = TaskDataKeys.create(
            "cook_request_handler", CookRequestHandler::new,
            value -> value.serializeNBT(null),
            tag -> {
                CookRequestHandler value = new CookRequestHandler();
                value.deserializeNBT(null, tag);
                return value;
            });
}
