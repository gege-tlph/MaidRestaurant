package com.mastermarisa.maid_restaurant.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.maid_restaurant.maid.TaskWaiter;
import com.mastermarisa.maid_restaurant.request.ServeRequestHandler;
import com.mastermarisa.maid_restaurant.utils.BlockUsageManager;
import com.mastermarisa.maid_restaurant.request.CookRequestHandler;
import com.mastermarisa.maid_restaurant.utils.TaskDataKeys;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;

import java.util.ArrayList;
import java.util.List;

public final class MaidTracker {
    public static final List<EntityMaid> maids = new ArrayList<>();

    private MaidTracker() {
    }

    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof EntityMaid maid) {
                maids.add(maid);
                TaskDataKeys.getOrCreate(maid, CookRequestHandler.TYPE);
                ServeRequestHandler serveHandler = TaskDataKeys.getOrCreate(maid, ServeRequestHandler.TYPE);
                if (maid.getTask() instanceof TaskWaiter) {
                    serveHandler.toList().forEach(request ->
                            request.targets.forEach(pos -> BlockUsageManager.addUser(pos, maid.getUUID())));
                }
            }
        });
        ServerEntityEvents.ENTITY_UNLOAD.register((entity, world) -> {
            if (entity instanceof EntityMaid maid) {
                maids.remove(maid);
                if (maid.getTask() instanceof TaskWaiter) {
                    TaskDataKeys.getOrCreate(maid, ServeRequestHandler.TYPE)
                            .toList().forEach(request ->
                                    request.targets.forEach(pos -> BlockUsageManager.removeUser(pos, maid.getUUID())));
                }
            }
        });
    }
}
