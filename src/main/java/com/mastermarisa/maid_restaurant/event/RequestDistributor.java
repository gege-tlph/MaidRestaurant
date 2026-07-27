package com.mastermarisa.maid_restaurant.event;

import com.mastermarisa.maid_restaurant.utils.RequestManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

public final class RequestDistributor {
    private RequestDistributor() {
    }

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(RequestDistributor::tick);
    }

    private static void tick(MinecraftServer server) {
        if (server.getTickCount() % 10 != 0) return;
        server.getAllLevels().forEach(RequestManager::tryDistributeRequests);
    }
}
