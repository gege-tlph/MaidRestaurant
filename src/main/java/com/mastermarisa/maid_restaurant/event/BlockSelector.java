package com.mastermarisa.maid_restaurant.event;

import com.mastermarisa.maid_restaurant.init.ModItems;
import com.mastermarisa.maid_restaurant.maid.TaskWaiter;
import com.mastermarisa.maid_restaurant.network.NetworkHandler;
import com.mastermarisa.maid_restaurant.network.SyncBlockSelectionPayload;
import com.mastermarisa.maid_restaurant.utils.EncodeUtils;
import com.mastermarisa.maid_restaurant.utils.component.BlockSelection;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;

public final class BlockSelector {
    private BlockSelector() {
    }

    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
            if (world.isClientSide() || !player.getItemInHand(hand).is(ModItems.ORDER_MENU.get())) {
                return InteractionResult.PASS;
            }
            if (!(player instanceof ServerPlayer serverPlayer)) {
                return InteractionResult.PASS;
            }
            BlockPos pos = hit.getBlockPos();
            BlockSelection selection = player.getAttachedOrCreate(BlockSelection.ATTACHMENT);
            if (selection.menu.removeIf(existing -> existing.asLong() == pos.asLong())) {
                player.setAttached(BlockSelection.ATTACHMENT, selection);
                sync(serverPlayer, selection);
                player.displayClientMessage(Component.translatable("message.maid_restaurant.block_removed"), true);
                return InteractionResult.SUCCESS;
            }
            if (TaskWaiter.isValidServeBlock(world, pos)) {
                selection.menu.add(pos);
                player.setAttached(BlockSelection.ATTACHMENT, selection);
                sync(serverPlayer, selection);
                player.displayClientMessage(Component.translatable("message.maid_restaurant.block_selected"), true);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        });
    }

    public static void sync(ServerPlayer player, BlockSelection selection) {
        NetworkHandler.sendToPlayer(player, new SyncBlockSelectionPayload(
                EncodeUtils.encode(selection.menu),
                EncodeUtils.encode(selection.order)
        ));
    }
}
