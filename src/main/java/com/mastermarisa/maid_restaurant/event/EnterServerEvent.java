package com.mastermarisa.maid_restaurant.event;

import com.mastermarisa.maid_restaurant.init.ModTrigger;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public final class EnterServerEvent {
    private EnterServerEvent() {
    }

    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) ->
                ModTrigger.GIVE_PATCHOULI_BOOK_CONFIG.get().trigger(handler.getPlayer()));
    }
}
