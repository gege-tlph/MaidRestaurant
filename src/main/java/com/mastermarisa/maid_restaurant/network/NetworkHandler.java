package com.mastermarisa.maid_restaurant.network;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.PotRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.StockpotRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import com.mastermarisa.maid_restaurant.MaidRestaurant;
import com.mastermarisa.maid_restaurant.request.CookRequest;
import com.mastermarisa.maid_restaurant.request.CookRequestHandler;
import com.mastermarisa.maid_restaurant.request.ServeRequestHandler;
import com.mastermarisa.maid_restaurant.utils.CookTasks;
import com.mastermarisa.maid_restaurant.utils.Debug;
import com.mastermarisa.maid_restaurant.utils.EncodeUtils;
import com.mastermarisa.maid_restaurant.utils.RequestManager;
import com.mastermarisa.maid_restaurant.utils.RecipeAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class NetworkHandler {
    public static void register() {
        PayloadTypeRegistry.playC2S().register(SendOrderPayload.TYPE, SendOrderPayload.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(ModifyAttributePayload.TYPE, ModifyAttributePayload.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(CancelRequestPayload.TYPE, CancelRequestPayload.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(ChangeHandlerAcceptValuePayload.TYPE, ChangeHandlerAcceptValuePayload.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(OpenScreenPayload.TYPE, OpenScreenPayload.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SendOrderPayload.TYPE,
                (payload, context) -> context.server().execute(() -> handleSendOrdersOnServer(payload, context.player())));
        ServerPlayNetworking.registerGlobalReceiver(ModifyAttributePayload.TYPE,
                (payload, context) -> context.server().execute(() -> handleModifyAttributesOnServer(payload, context.player())));
        ServerPlayNetworking.registerGlobalReceiver(CancelRequestPayload.TYPE,
                (payload, context) -> context.server().execute(() -> handleCancelRequestOnServer(payload, context.player())));
        ServerPlayNetworking.registerGlobalReceiver(ChangeHandlerAcceptValuePayload.TYPE,
                (payload, context) -> context.server().execute(() -> handleChangeHandlerAcceptValueOnServer(payload, context.player())));
    }

    public static void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
        ServerPlayNetworking.send(player, payload);
    }

    private static void handleSendOrdersOnServer(SendOrderPayload payload, ServerPlayer player) {
        String[] IDs = payload.IDs();
        String[] types = payload.types();
        int[] counts = payload.counts();
        Debug.Log(" received_send_order_packet, length:" + IDs.length);
        for (int i = 0;i < IDs.length;i++) {
            List<CookRequest> mapped = tryMap(player.level(), new CookRequest(
                    Identifier.parse(IDs[i]),
                    CookTasks.getType(types[i]),
                    counts[i],
                    counts[i],
                    payload.targets(),
                    payload.attributes()
            ));

            for (var request : mapped) {
                RequestManager.post((ServerLevel) player.level(), request, CookRequest.TYPE);
            }
        }
    }

    private static List<CookRequest> tryMap(Level level, CookRequest request) {
        List<CookRequest> mapped = new ArrayList<>();

        if (request.type.equals(ModRecipes.POT_RECIPE)) {
            PotRecipe recipe = RecipeAccess.<PotRecipe>require(level, request.id).value();
            ItemStack result = recipe.result();
            int count = result.getCount() * request.requested;
            if (result.is(ModItems.MEAT_PIE) && result.getCount() != 9) {
                if (count > 9) mapped.add(new CookRequest(
                        Identifier.parse("kaleidoscope_cookery:pot/stuffed_dough_food_to_meat_pie_9"),
                        ModRecipes.POT_RECIPE,
                        count / 9,
                        count / 9,
                        request.targets,
                        request.attributes.getAttributes()
                ).copy());

                if (count % 9 != 0) mapped.add(new CookRequest(
                        Identifier.parse("kaleidoscope_cookery:pot/stuffed_dough_food_to_meat_pie_" + count % 9),
                        ModRecipes.POT_RECIPE,
                        1,
                        1,
                        request.targets,
                        request.attributes.getAttributes()
                ).copy());
            } else if (result.is(ModItems.FRIED_EGG) && result.getCount() != 9) {
                if (count > 9) mapped.add(new CookRequest(
                        Identifier.parse("kaleidoscope_cookery:pot/egg_to_fried_egg_9"),
                        ModRecipes.POT_RECIPE,
                        count / 9,
                        count / 9,
                        request.targets,
                        request.attributes.getAttributes()
                ).copy());

                if (count % 9 != 0) mapped.add(new CookRequest(
                        Identifier.parse("kaleidoscope_cookery:pot/egg_to_fried_egg_" + count % 9),
                        ModRecipes.POT_RECIPE,
                        1,
                        1,
                        request.targets,
                        request.attributes.getAttributes()
                ).copy());
            } else if (result.is(EncodeUtils.decode("kaleidoscope_cookery:sweet_and_sour_ender_pearls")) && result.getCount() != 3) {
                if (count > 3) mapped.add(new CookRequest(
                        Identifier.parse("kaleidoscope_cookery:pot/sweet_and_sour_ender_pearls_3"),
                        ModRecipes.POT_RECIPE,
                        count / 3,
                        count / 3,
                        request.targets,
                        request.attributes.getAttributes()
                ).copy());

                if (count % 3 != 0) mapped.add(new CookRequest(
                        Identifier.parse("kaleidoscope_cookery:pot/sweet_and_sour_ender_pearls_" + count % 3),
                        ModRecipes.POT_RECIPE,
                        1,
                        1,
                        request.targets,
                        request.attributes.getAttributes()
                ).copy());
            } else if (result.is(ModItems.EGG_FRIED_RICE) && result.getCount() != 3) {
                if (count > 3) mapped.add(new CookRequest(
                        Identifier.parse("kaleidoscope_cookery:pot/egg_fried_rice_3"),
                        ModRecipes.POT_RECIPE,
                        count / 3,
                        count / 3,
                        request.targets,
                        request.attributes.getAttributes()
                ).copy());

                if (count % 3 == 2) mapped.add(new CookRequest(
                        Identifier.parse("kaleidoscope_cookery:pot/egg_fried_rice_2"),
                        ModRecipes.POT_RECIPE,
                        1,
                        1,
                        request.targets,
                        request.attributes.getAttributes()
                ).copy());
            }
        } else if (request.type.equals(ModRecipes.STOCKPOT_RECIPE)) {
            StockpotRecipe recipe = RecipeAccess.<StockpotRecipe>require(level, request.id).value();
            ItemStack result = recipe.result();
            int count = result.getCount() * request.requested;
            if (result.is(ModItems.DUMPLING) && result.getCount() != 9) {
                if (count > 9) mapped.add(new CookRequest(
                        Identifier.parse("kaleidoscope_cookery:stockpot/dumpling_count_9"),
                        ModRecipes.STOCKPOT_RECIPE,
                        count / 9,
                        count / 9,
                        request.targets,
                        request.attributes.getAttributes()
                ).copy());

                if (count % 9 != 0) mapped.add(new CookRequest(
                        Identifier.parse("kaleidoscope_cookery:stockpot/dumpling_count_" + count % 9),
                        ModRecipes.STOCKPOT_RECIPE,
                        1,
                        1,
                        request.targets,
                        request.attributes.getAttributes()
                ).copy());
            } else if(result.is(EncodeUtils.decode("kaleidoscope_cookery:shengjian_mantou")) && result.getCount() != 2) {
                if (count > 2) mapped.add(new CookRequest(
                        Identifier.parse("kaleidoscope_cookery:stockpot/shengjian_mantou_count_2"),
                        ModRecipes.STOCKPOT_RECIPE,
                        count / 2,
                        count / 2,
                        request.targets,
                        request.attributes.getAttributes()
                ).copy());

                if (count % 2 != 0) mapped.add(new CookRequest(
                        Identifier.parse("kaleidoscope_cookery:stockpot/shengjian_mantou_count_1"),
                        ModRecipes.STOCKPOT_RECIPE,
                        1,
                        1,
                        request.targets,
                        request.attributes.getAttributes()
                ).copy());
            }
        }

        if (mapped.isEmpty()) mapped.add(request);
        return mapped;
    }

    private static void handleModifyAttributesOnServer(ModifyAttributePayload payload, ServerPlayer player) {
        ServerLevel level = (ServerLevel) player.level();
        if (level.getEntity(payload.uuid()) instanceof EntityMaid maid) {
            CookRequestHandler handler = maid.getData(CookRequestHandler.TYPE);
            if (handler.size() > payload.index()) {
                Objects.requireNonNull(handler.getAt(payload.index())).attributes.setAttributes(payload.attributes());
            }
        }
    }

    private static void handleCancelRequestOnServer(CancelRequestPayload payload, ServerPlayer player) {
        ServerLevel level = (ServerLevel) player.level();
        if (level.getEntity(payload.uuid()) instanceof EntityMaid maid) {
            switch (payload.actionCode()) {
                case 0 -> {
                    CookRequestHandler handler = maid.getData(CookRequestHandler.TYPE);
                    handler.removeAt(payload.index());
                    maid.setAndSyncData(CookRequestHandler.TYPE, handler);
                }
                case 1 -> {
                    ServeRequestHandler handler = maid.getData(ServeRequestHandler.TYPE);
                    handler.removeAt(payload.index());
                    maid.setAndSyncData(ServeRequestHandler.TYPE, handler);
                }
            }
        }
    }

    private static void handleChangeHandlerAcceptValueOnServer(ChangeHandlerAcceptValuePayload payload, ServerPlayer player) {
        ServerLevel level = (ServerLevel) player.level();
        if (level.getEntity(payload.uuid()) instanceof EntityMaid maid) {
            switch (payload.t()) {
                case 0 -> {
                    CookRequestHandler handler = maid.getData(CookRequestHandler.TYPE);
                    handler.accept = payload.value();
                    maid.setAndSyncData(CookRequestHandler.TYPE, handler);
                }
                case 1 -> {
                    ServeRequestHandler handler = maid.getData(ServeRequestHandler.TYPE);
                    handler.accept = payload.value();
                    maid.setAndSyncData(ServeRequestHandler.TYPE, handler);
                }
            }
        }
    }

    public static final StreamCodec<FriendlyByteBuf, long[]> LONG_ARRAY_STREAM_CODEC = StreamCodec.of(
            (buf, array) -> {
                buf.writeVarInt(array.length);
                for (long l : array) {
                    buf.writeLong(l);
                }
            },
            buf -> {
                int length = buf.readVarInt();
                long[] array = new long[length];
                for (int i = 0; i < length; i++) {
                    array[i] = buf.readLong();
                }
                return array;
            }
    );

    public static final StreamCodec<FriendlyByteBuf, int[]> INT_ARRAY_STREAM_CODEC = StreamCodec.of(
            (buf, array) -> {
                buf.writeVarInt(array.length);
                for (int i : array) {
                    buf.writeInt(i);
                }
            },
            buf -> {
                int length = buf.readVarInt();
                int[] array = new int[length];
                for (int i = 0; i < length; i++) {
                    array[i] = buf.readInt();
                }
                return array;
            }
    );

    public static final StreamCodec<FriendlyByteBuf, String[]> STRING_ARRAY_STREAM_CODEC = StreamCodec.of(
            (buf, array) -> {
                buf.writeVarInt(array.length);
                for (String s : array) {
                    buf.writeUtf(s);
                }
            },
            buf -> {
                int length = buf.readVarInt();
                String[] array = new String[length];
                for (int i = 0; i < length; i++) {
                    array[i] = buf.readUtf();
                }
                return array;
            }
    );

    public static final StreamCodec<FriendlyByteBuf, UUID> UUID_STREAM_CODEC = StreamCodec.of(
            (buf, uuid) -> buf.writeUUID(uuid),
            buf -> buf.readUUID()
    );
}
