package com.mastermarisa.maid_restaurant.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.maid_restaurant.init.ModItems;
import com.mastermarisa.maid_restaurant.maid.TaskCook;
import com.mastermarisa.maid_restaurant.maid.TaskWaiter;
import com.mastermarisa.maid_restaurant.network.NetworkHandler;
import com.mastermarisa.maid_restaurant.network.OpenScreenPayload;
import com.mastermarisa.maid_restaurant.request.CookRequestHandler;
import com.mastermarisa.maid_restaurant.request.ServeRequestHandler;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.world.InteractionResult;

public final class MaidScreenOpening {
    private MaidScreenOpening() {
    }

    public static void register() {
        UseEntityCallback.EVENT.register((player, world, hand, target, hit) -> {
            if (world.isClientSide() || !(target instanceof EntityMaid maid)
                    || !player.getItemInHand(hand).is(ModItems.ORDER_MENU.get())) {
                return InteractionResult.PASS;
            }
            CookRequestHandler cook = maid.getOrCreateData(CookRequestHandler.TYPE, new CookRequestHandler());
            ServeRequestHandler serve = maid.getOrCreateData(ServeRequestHandler.TYPE, new ServeRequestHandler());
            if (maid.getTask() instanceof TaskCook) {
                NetworkHandler.sendToPlayer((net.minecraft.server.level.ServerPlayer) player,
                        new OpenScreenPayload(0, maid.getId()));
                return InteractionResult.SUCCESS;
            }
            if (maid.getTask() instanceof TaskWaiter && serve.size() > 0) {
                NetworkHandler.sendToPlayer((net.minecraft.server.level.ServerPlayer) player,
                        new OpenScreenPayload(1, maid.getId()));
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        });
    }
}
