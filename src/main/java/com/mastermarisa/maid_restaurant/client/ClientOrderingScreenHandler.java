package com.mastermarisa.maid_restaurant.client;

import com.mastermarisa.maid_restaurant.client.gui.screen.ordering.OrderingScreen;
import com.mastermarisa.maid_restaurant.network.OpenOrderingScreenPayload;
import com.mastermarisa.maid_restaurant.utils.EncodeUtils;
import net.minecraft.client.Minecraft;

public final class ClientOrderingScreenHandler {
    private ClientOrderingScreenHandler() {
    }

    public static void handle(OpenOrderingScreenPayload payload) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) return;
        minecraft.setScreen(new OrderingScreen(minecraft.player, EncodeUtils.decode(payload.targets())));
    }
}
