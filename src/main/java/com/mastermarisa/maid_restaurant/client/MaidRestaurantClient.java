package com.mastermarisa.maid_restaurant.client;

import com.mastermarisa.maid_restaurant.network.OpenScreenPayload;
import com.mastermarisa.maid_restaurant.network.OpenOrderingScreenPayload;
import com.mastermarisa.maid_restaurant.network.SyncBlockSelectionPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

/**
 * Fabric client entrypoint. Client-only registrations are kept here so the
 * common entrypoint can load safely on a dedicated server.
 */
public final class MaidRestaurantClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(OpenScreenPayload.TYPE,
                (payload, context) -> context.client().execute(() -> ClientOpenScreenHandler.handle(payload)));
        ClientPlayNetworking.registerGlobalReceiver(SyncBlockSelectionPayload.TYPE,
                (payload, context) -> context.client().execute(() -> ClientBlockSelectionHandler.handle(payload)));
        ClientPlayNetworking.registerGlobalReceiver(OpenOrderingScreenPayload.TYPE,
                (payload, context) -> context.client().execute(() -> ClientOrderingScreenHandler.handle(payload)));
        com.mastermarisa.maid_restaurant.client.event.ClientSetup.register();
    }
}
