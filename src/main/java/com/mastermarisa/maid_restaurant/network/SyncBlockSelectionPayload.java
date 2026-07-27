package com.mastermarisa.maid_restaurant.network;

import com.mastermarisa.maid_restaurant.MaidRestaurant;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record SyncBlockSelectionPayload(long[] menu, long[] order) implements CustomPacketPayload {
    public static final Type<SyncBlockSelectionPayload> TYPE =
            new Type<>(MaidRestaurant.resourceLocation("sync_block_selection"));

    public static final StreamCodec<FriendlyByteBuf, SyncBlockSelectionPayload> STREAM_CODEC =
            StreamCodec.composite(
                    NetworkHandler.LONG_ARRAY_STREAM_CODEC,
                    SyncBlockSelectionPayload::menu,
                    NetworkHandler.LONG_ARRAY_STREAM_CODEC,
                    SyncBlockSelectionPayload::order,
                    SyncBlockSelectionPayload::new
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
