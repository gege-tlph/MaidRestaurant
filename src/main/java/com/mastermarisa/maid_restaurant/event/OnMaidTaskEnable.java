package com.mastermarisa.maid_restaurant.event;

import com.github.tartaricacid.touhoulittlemaid.api.event.MaidTaskEnableEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.maid_restaurant.maid.TaskWaiter;
import com.mastermarisa.maid_restaurant.request.CookRequestHandler;
import com.mastermarisa.maid_restaurant.request.ServeRequestHandler;
import com.mastermarisa.maid_restaurant.utils.BehaviorUtils;
import com.mastermarisa.maid_restaurant.utils.BlockUsageManager;
import com.mastermarisa.maid_restaurant.utils.TaskDataKeys;

public class OnMaidTaskEnable {
    public static void register() {
        MaidTaskEnableEvent.CALLBACK.register(OnMaidTaskEnable::onMaidTaskEnable);
    }

    public static void onMaidTaskEnable(MaidTaskEnableEvent event) {
        EntityMaid maid = event.getEntityMaid();

        BehaviorUtils.eraseTargetPos(maid);
        TaskDataKeys.getOrCreate(maid, CookRequestHandler.TYPE);
        ServeRequestHandler serveHandler = TaskDataKeys.getOrCreate(maid, ServeRequestHandler.TYPE);

        if (maid.getTask() instanceof TaskWaiter) {
            serveHandler.toList().forEach(serveRequest -> {
                serveRequest.targets.forEach(p -> BlockUsageManager.removeUser(p,maid.getUUID()));
            });
        }

        if (event.getTargetTask() instanceof TaskWaiter) {
            serveHandler.toList().forEach(serveRequest -> {
                serveRequest.targets.forEach(p -> BlockUsageManager.addUser(p,maid.getUUID()));
            });
        }
    }
}
