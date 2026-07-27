package com.mastermarisa.maid_restaurant.advancements.rewards;

import net.minecraft.server.level.ServerPlayer;

/**
 * Patchouli's optional book reward is handled by the Fabric advancement
 * integration in the final pass. The trigger object remains as a stable
 * internal hook for login events while the NeoForge trigger registry is gone.
 */
public final class GivePatchouliBookConfigTrigger {
    public void trigger(ServerPlayer player) {
        // Optional Patchouli integration; no action when Patchouli is absent.
    }
}
