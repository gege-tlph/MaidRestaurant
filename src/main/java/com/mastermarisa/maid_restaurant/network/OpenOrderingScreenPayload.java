package com.mastermarisa.maid_restaurant.network;

import com.mastermarisa.maid_restaurant.MaidRestaurant;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record OpenOrderingScreenPayload(long[] targets) implements CustomPacketPayload {
    public static final Type<OpenOrderingScreenPayload> TYPE =
            new Type<>(MaidRestaurant.resourceLocation("open_ordering_screen"));

    public static final StreamCodec<FriendlyByteBuf, OpenOrderingScreenPayload> STREAM_CODEC =
            StreamCodec.composite(
                    NetworkHandler.LONG_ARRAY_STREAM_CODEC,
                    OpenOrderingScreenPayload::targets,
                    OpenOrderingScreenPayload::new
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
