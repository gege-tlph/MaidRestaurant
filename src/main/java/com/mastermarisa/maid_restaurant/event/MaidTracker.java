package com.mastermarisa.maid_restaurant.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.maid_restaurant.maid.TaskWaiter;
import com.mastermarisa.maid_restaurant.request.ServeRequestHandler;
import com.mastermarisa.maid_restaurant.utils.BlockUsageManager;
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
                if (maid.getTask() instanceof TaskWaiter) {
                    maid.getOrCreateData(ServeRequestHandler.TYPE, new ServeRequestHandler())
                            .toList().forEach(request ->
                                    request.targets.forEach(pos -> BlockUsageManager.addUser(pos, maid.getUUID())));
                }
            }
        });
        ServerEntityEvents.ENTITY_UNLOAD.register((entity, world) -> {
            if (entity instanceof EntityMaid maid) {
                maids.remove(maid);
                if (maid.getTask() instanceof TaskWaiter) {
                    maid.getOrCreateData(ServeRequestHandler.TYPE, new ServeRequestHandler())
                            .toList().forEach(request ->
                                    request.targets.forEach(pos -> BlockUsageManager.removeUser(pos, maid.getUUID())));
                }
            }
        });
    }
}
