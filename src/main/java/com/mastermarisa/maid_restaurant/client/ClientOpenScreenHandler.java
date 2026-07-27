package com.mastermarisa.maid_restaurant.client;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.maid_restaurant.client.gui.screen.cook_request.CookRequestScreen;
import com.mastermarisa.maid_restaurant.client.gui.screen.serve_request.ServeRequestScreen;
import com.mastermarisa.maid_restaurant.network.OpenScreenPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

public final class ClientOpenScreenHandler {
    private ClientOpenScreenHandler() {}

    public static void handle(OpenScreenPayload payload) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) return;
        Entity entity = minecraft.level.getEntity(payload.id());
        if (!(entity instanceof EntityMaid maid)) return;
        if (payload.actionCode() == 0) {
            minecraft.setScreen(new CookRequestScreen(maid));
        } else if (payload.actionCode() == 1) {
            minecraft.setScreen(new ServeRequestScreen(maid));
        }
    }
}
