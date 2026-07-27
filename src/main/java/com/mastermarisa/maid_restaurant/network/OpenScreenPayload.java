package com.mastermarisa.maid_restaurant.network;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.maid_restaurant.MaidRestaurant;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record OpenScreenPayload(int actionCode, int id) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<OpenScreenPayload> TYPE =
            new CustomPacketPayload.Type<>(MaidRestaurant.resourceLocation("open_screen"));

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static final StreamCodec<FriendlyByteBuf, OpenScreenPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.INT,
                    OpenScreenPayload::actionCode,
                    ByteBufCodecs.INT,
                    OpenScreenPayload::id,
                    OpenScreenPayload::new
            );

    public static void handle(OpenScreenPayload payload) {
        // Client GUI migration is staged after the common networking layer.
    }
}
