package com.mastermarisa.maid_restaurant.client.event;

import com.mastermarisa.maid_restaurant.client.render.EmptyRenderer;
import com.mastermarisa.maid_restaurant.entity.SitEntity;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public final class ClientSetup {
    private ClientSetup() {
    }

    public static void register() {
        EntityRendererRegistry.register(SitEntity.TYPE, EmptyRenderer::new);
        OnRenderLevelStage.register();
    }
}
