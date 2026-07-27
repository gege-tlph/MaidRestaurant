package com.mastermarisa.maid_restaurant.client.event;

import com.mastermarisa.maid_restaurant.client.render.WorldItemRenderer;
import com.mastermarisa.maid_restaurant.init.ModItems;
import com.mastermarisa.maid_restaurant.utils.component.BlockSelection;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldExtractionContext;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public final class OnRenderLevelStage {
    private static volatile List<WorldItemRenderer.PreparedItem> preparedItems = List.of();

    private OnRenderLevelStage() {
    }

    public static void register() {
        WorldRenderEvents.END_EXTRACTION.register(OnRenderLevelStage::extract);
        WorldRenderEvents.AFTER_ENTITIES.register(OnRenderLevelStage::submit);
    }

    private static void extract(WorldExtractionContext context) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player == null || minecraft.level == null) {
            preparedItems = List.of();
            return;
        }

        BlockSelection selection = player.getAttachedOrCreate(BlockSelection.ATTACHMENT);
        Item heldItem = player.getMainHandItem().getItem();
        List<BlockPos> positions;
        Item indicator;
        if (heldItem == ModItems.ORDER_MENU.get()) {
            positions = selection.menu;
            indicator = ModItems.ORDER_MENU.get();
        } else if (heldItem == ModItems.ORDER_ITEM.get()) {
            positions = selection.order;
            indicator = ModItems.ORDER_ITEM.get();
        } else {
            preparedItems = List.of();
            return;
        }

        float partialTick = context.tickCounter().getGameTimeDeltaPartialTick(false);
        List<WorldItemRenderer.PreparedItem> extracted = new ArrayList<>(positions.size());
        for (BlockPos pos : positions) {
            Vec3 target = Vec3.atCenterOf(pos.above());
            extracted.add(WorldItemRenderer.prepare(
                    minecraft,
                    context.camera(),
                    target,
                    new ItemStack(indicator),
                    partialTick
            ));
        }
        preparedItems = List.copyOf(extracted);
    }

    private static void submit(WorldRenderContext context) {
        for (WorldItemRenderer.PreparedItem item : preparedItems) {
            WorldItemRenderer.submit(context.matrices(), context.commandQueue(), item);
        }
    }
}
