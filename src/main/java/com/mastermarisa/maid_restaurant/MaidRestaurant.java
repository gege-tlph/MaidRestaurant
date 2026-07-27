package com.mastermarisa.maid_restaurant;

import com.mastermarisa.maid_restaurant.init.*;
import com.mastermarisa.maid_restaurant.init.registry.CommonRegistry;
import com.mastermarisa.maid_restaurant.utils.CookTasks;
import com.mastermarisa.maid_restaurant.network.NetworkHandler;
import com.mastermarisa.maid_restaurant.config.RestaurantConfig;
import com.mastermarisa.maid_restaurant.event.BlockSelector;
import com.mastermarisa.maid_restaurant.event.EnterServerEvent;
import com.mastermarisa.maid_restaurant.event.RequestDistributor;
import com.mastermarisa.maid_restaurant.event.MaidScreenOpening;
import com.mastermarisa.maid_restaurant.event.MaidTracker;
import com.mastermarisa.maid_restaurant.event.OnMaidTaskEnable;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import com.google.gson.Gson;

public class MaidRestaurant implements ModInitializer {
    public static final String MOD_ID = "maid_restaurant";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Gson GSON = new Gson();

    public static Identifier resourceLocation(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID,path);
    }

    @Override
    public void onInitialize() {
        RestaurantConfig.register();
        ModEntities.register();
        ModItems.register();
        CommonRegistry.register();
        ModDataComponents.register();
        ModTrigger.register();
        ModCompats.register();
        CookTasks.register();
        NetworkHandler.register();
        BlockSelector.register();
        EnterServerEvent.register();
        RequestDistributor.register();
        MaidScreenOpening.register();
        MaidTracker.register();
        OnMaidTaskEnable.register();
        ServerLifecycleEvents.SERVER_STARTED.register(server ->
                LOGGER.info("Maid Restaurant initialized on Fabric 1.21.11"));
    }
}
