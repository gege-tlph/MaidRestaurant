package com.mastermarisa.maid_restaurant.utils;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.maid_restaurant.api.request.IRequest;
import com.mastermarisa.maid_restaurant.event.MaidTracker;
import com.mastermarisa.maid_restaurant.maid.TaskCook;
import com.mastermarisa.maid_restaurant.maid.TaskWaiter;
import com.mastermarisa.maid_restaurant.request.CookRequest;
import com.mastermarisa.maid_restaurant.request.CookRequestHandler;
import com.mastermarisa.maid_restaurant.request.ServeRequest;
import com.mastermarisa.maid_restaurant.request.ServeRequestHandler;
import com.mastermarisa.maid_restaurant.request.world.WorldCookRequestHandler;
import com.mastermarisa.maid_restaurant.request.world.WorldServeRequestHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RequestManager {
    private static final double MAX_RANGE = 50.0D;

    public static @Nullable IRequest peek(EntityMaid maid, int type) {
        switch (type) {
            case CookRequest.TYPE -> {
                return TaskDataKeys.getOrCreate(maid, CookRequestHandler.TYPE).getFirst();
            }
            case ServeRequest.TYPE -> {
                ServeRequestHandler handler = TaskDataKeys.getOrCreate(maid, ServeRequestHandler.TYPE);
                ServeRequest request = handler.getFirst();
                if (request != null && request.toServe.getCount() <= 0) {
                    handler.removeFirst();
                    maid.setAndSyncData(ServeRequestHandler.TYPE, handler);
                    return peek(maid, ServeRequest.TYPE);
                }
                return request;
            }
        }
        return null;
    }

    public static @Nullable IRequest pop(EntityMaid maid, int type) {
        switch (type) {
            case CookRequest.TYPE -> {
                CookRequestHandler handler = TaskDataKeys.getOrCreate(maid, CookRequestHandler.TYPE);
                CookRequest request = handler.removeFirst();
                maid.setAndSyncData(CookRequestHandler.TYPE, handler);
                return request;
            }
            case ServeRequest.TYPE -> {
                ServeRequestHandler handler = TaskDataKeys.getOrCreate(maid, ServeRequestHandler.TYPE);
                ServeRequest request = handler.removeFirst();
                maid.setAndSyncData(ServeRequestHandler.TYPE, handler);
                return request;
            }
        }
        return null;
    }

    public static void post(ServerLevel level, IRequest request, int type) {
        switch (type) {
            case CookRequest.TYPE -> level.getAttachedOrCreate(WorldCookRequestHandler.ATTACHMENT).add((CookRequest) request);
            case ServeRequest.TYPE -> level.getAttachedOrCreate(WorldServeRequestHandler.ATTACHMENT).add((ServeRequest) request);
        }
    }

    public static void tryDistributeRequests(ServerLevel level) {
        tryDistributeCookRequest(level);
        tryDistributeServeRequest(level);
    }

    private static void tryDistributeCookRequest(ServerLevel level) {
        List<CookRequest> toRemove = new ArrayList<>();
        WorldCookRequestHandler handler = level.getAttachedOrCreate(WorldCookRequestHandler.ATTACHMENT);
        for (var request : handler.toList()) {
            BlockPos pos = EncodeUtils.decode(request.targets[0]);
            List<EntityMaid> cookers = MaidTracker.maids.stream().filter(maid -> maid.getTask() instanceof TaskCook).filter(m-> {
                CookRequestHandler requests = TaskDataKeys.getOrCreate(m, CookRequestHandler.TYPE);
                return requests.accept && requests.size() < 7 && pos.distSqr(m.blockPosition()) <= Math.pow(MAX_RANGE, 2.0D);
            }).toList();
            if (!cookers.isEmpty()) {
                cookers = new ArrayList<>(cookers);
                Collections.shuffle(cookers);
                EntityMaid target = cookers.getFirst();
                CookRequestHandler requests = TaskDataKeys.getOrCreate(target, CookRequestHandler.TYPE);
                requests.add(request);
                target.setAndSyncData(CookRequestHandler.TYPE, requests);
                toRemove.add(request);
            }
        }

        for (var request : toRemove) {
            handler.remove(request);
        }
    }

    public static void tryDistributeServeRequest(ServerLevel level) {
        List<ServeRequest> toRemove = new ArrayList<>();
        WorldServeRequestHandler handler = level.getAttachedOrCreate(WorldServeRequestHandler.ATTACHMENT);
        for (var request : handler.toList()) {
            BlockPos pos = request.targets.getFirst();
            List<EntityMaid> waiters = MaidTracker.maids.stream().filter(maid -> maid.getTask() instanceof TaskWaiter).filter(m-> {
                ServeRequestHandler requests = TaskDataKeys.getOrCreate(m, ServeRequestHandler.TYPE);
                return requests.size() < 5 && pos.distSqr(m.blockPosition()) <= Math.pow(MAX_RANGE,2.0D);
            }).toList();
            if (!waiters.isEmpty()) {
                waiters = new ArrayList<>(waiters);
                Collections.shuffle(waiters);
                EntityMaid waiter = waiters.getFirst();
                ServeRequestHandler serveRequestHandler = TaskDataKeys.getOrCreate(waiter, ServeRequestHandler.TYPE);
                serveRequestHandler.add(request);
                serveRequestHandler.toList().forEach(serveRequest -> {
                        serveRequest.targets.forEach(p -> BlockUsageManager.addUser(p,waiter.getUUID()));
                });
                waiter.setAndSyncData(ServeRequestHandler.TYPE, serveRequestHandler);
                toRemove.add(request);
            }
        }

        for (var request : toRemove) {
            handler.remove(request);
        }
    }
}
