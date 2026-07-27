package com.mastermarisa.maid_restaurant.api.request;

import net.minecraft.nbt.CompoundTag;

/**
 * Serialization helper retained for callers that used the NeoForge
 * AttachmentSyncHandler. Fabric TLM task data uses CompoundTag directly.
 */
public final class RequestSyncer<F extends RequestHandler<?>> {
    public CompoundTag write(F handler) {
        return handler.serializeNBT(null);
    }

    public F read(F handler, CompoundTag tag) {
        handler.deserializeNBT(null, tag);
        return handler;
    }
}
