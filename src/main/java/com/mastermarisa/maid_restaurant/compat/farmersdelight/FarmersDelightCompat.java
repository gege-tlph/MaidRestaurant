package com.mastermarisa.maid_restaurant.compat.farmersdelight;

import net.fabricmc.loader.api.FabricLoader;

public class FarmersDelightCompat {
    public static final boolean LOADED = FabricLoader.getInstance().isModLoaded("farmersdelight");

    public static void register() {
        if (LOADED) {
            CookingPotCookTask.register();
        }
    }
}
