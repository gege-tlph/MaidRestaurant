package com.mastermarisa.maid_restaurant.client;

import com.mastermarisa.maid_restaurant.network.SyncBlockSelectionPayload;
import com.mastermarisa.maid_restaurant.utils.EncodeUtils;
import com.mastermarisa.maid_restaurant.utils.component.BlockSelection;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;

public final class ClientBlockSelectionHandler {
    private ClientBlockSelectionHandler() {
    }

    public static void handle(SyncBlockSelectionPayload payload) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) return;

        BlockSelection selection = minecraft.player.getAttachedOrCreate(BlockSelection.ATTACHMENT);
        selection.menu = new ArrayList<>(EncodeUtils.decode(payload.menu()));
        selection.order = new ArrayList<>(EncodeUtils.decode(payload.order()));
        minecraft.player.setAttached(BlockSelection.ATTACHMENT, selection);
    }
}
