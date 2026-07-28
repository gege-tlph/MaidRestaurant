package com.mastermarisa.maid_restaurant.network;

import com.mastermarisa.maid_restaurant.MaidRestaurant;
import com.mastermarisa.maid_restaurant.utils.CookTasks;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public record OpenOrderingScreenPayload(long[] targets, List<RecipeSummary> recipes) implements CustomPacketPayload {
    public static final Type<OpenOrderingScreenPayload> TYPE =
            new Type<>(MaidRestaurant.resourceLocation("open_ordering_screen"));

    private static final StreamCodec<RegistryFriendlyByteBuf, List<RecipeSummary>> RECIPE_LIST_STREAM_CODEC =
            ByteBufCodecs.collection(ArrayList::new, RecipeSummary.STREAM_CODEC, 8192);

    public static final StreamCodec<RegistryFriendlyByteBuf, OpenOrderingScreenPayload> STREAM_CODEC =
            StreamCodec.composite(
                    NetworkHandler.LONG_ARRAY_STREAM_CODEC,
                    OpenOrderingScreenPayload::targets,
                    RECIPE_LIST_STREAM_CODEC,
                    OpenOrderingScreenPayload::recipes,
                    OpenOrderingScreenPayload::new
            );

    public static OpenOrderingScreenPayload from(long[] targets, net.minecraft.world.level.Level level) {
        List<RecipeSummary> recipes = CookTasks.getRegistered().stream()
                .flatMap(task -> task.getAllRecipeData(level).stream())
                .map(data -> new RecipeSummary(
                        data.ID.toString(),
                        CookTasks.getUID(data.type),
                        data.result.copy()
                ))
                .toList();
        return new OpenOrderingScreenPayload(targets, recipes);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public record RecipeSummary(String id, String type, ItemStack result) {
        private static final StreamCodec<RegistryFriendlyByteBuf, RecipeSummary> STREAM_CODEC =
                StreamCodec.composite(
                        ByteBufCodecs.STRING_UTF8,
                        RecipeSummary::id,
                        ByteBufCodecs.STRING_UTF8,
                        RecipeSummary::type,
                        ItemStack.STREAM_CODEC,
                        RecipeSummary::result,
                        RecipeSummary::new
                );
    }
}
