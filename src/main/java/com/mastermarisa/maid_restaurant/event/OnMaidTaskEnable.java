package com.mastermarisa.maid_restaurant.event;

import com.github.tartaricacid.touhoulittlemaid.api.event.MaidTaskEnableEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.maid_restaurant.maid.TaskCook;
import com.mastermarisa.maid_restaurant.maid.TaskWaiter;
import com.mastermarisa.maid_restaurant.request.CookRequest;
import com.mastermarisa.maid_restaurant.request.ServeRequest;
import com.mastermarisa.maid_restaurant.request.ServeRequestHandler;
import com.mastermarisa.maid_restaurant.utils.BehaviorUtils;
import com.mastermarisa.maid_restaurant.utils.BlockUsageManager;
import com.mastermarisa.maid_restaurant.utils.EncodeUtils;
import com.mastermarisa.maid_restaurant.utils.RequestManager;

import java.util.Arrays;

public class OnMaidTaskEnable {
    public static void onMaidTaskEnable(MaidTaskEnableEvent event) {
        EntityMaid maid = event.getEntityMaid();

        BehaviorUtils.eraseTargetPos(maid);

        if (maid.getTask() instanceof TaskWaiter) {
            maid.getData(ServeRequestHandler.TYPE).toList().forEach(serveRequest -> {
                serveRequest.targets.forEach(p -> BlockUsageManager.removeUser(p,maid.getUUID()));
            });
        }

        if (event.getTargetTask() instanceof TaskWaiter) {
            maid.getData(ServeRequestHandler.TYPE).toList().forEach(serveRequest -> {
                serveRequest.targets.forEach(p -> BlockUsageManager.addUser(p,maid.getUUID()));
            });
        }
    }
}
