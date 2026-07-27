package com.mastermarisa.maid_restaurant.compat.bakeries;

import net.fabricmc.loader.api.FabricLoader;

public class BakeriesCompat {
    public static final boolean LOADED = FabricLoader.getInstance().isModLoaded("bakeries");

    public static void register() {
        if (LOADED) {
            OvenCookTask.register();
            BlenderCookTask.register();
            ToasterCookTask.register();
            GlassDrinkCupCookTask.register();
        }
    }
}
