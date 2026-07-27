package com.mastermarisa.maid_restaurant.client.render;

import net.fabricmc.loader.api.FabricLoader;

public final class ShaderState {
    private ShaderState() {
    }

    public static boolean shaderEnabled() {
        return FabricLoader.getInstance().isModLoaded("iris");
    }
}
