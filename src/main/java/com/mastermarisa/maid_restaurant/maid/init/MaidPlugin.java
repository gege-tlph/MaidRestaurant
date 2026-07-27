package com.mastermarisa.maid_restaurant.maid.init;

import com.github.tartaricacid.touhoulittlemaid.api.ILittleMaid;
import com.github.tartaricacid.touhoulittlemaid.api.LittleMaidExtension;
import com.github.tartaricacid.touhoulittlemaid.entity.ai.brain.ExtraMaidBrainManager;
import com.github.tartaricacid.touhoulittlemaid.entity.task.TaskManager;
import com.github.tartaricacid.touhoulittlemaid.entity.data.TaskDataRegister;
import com.mastermarisa.maid_restaurant.maid.TaskCook;
import com.mastermarisa.maid_restaurant.maid.TaskWaiter;
import com.mastermarisa.maid_restaurant.request.CookRequestHandler;
import com.mastermarisa.maid_restaurant.request.ServeRequestHandler;
import com.mastermarisa.maid_restaurant.request.world.WorldCookRequestHandler;
import com.mastermarisa.maid_restaurant.request.world.WorldServeRequestHandler;
import com.mastermarisa.maid_restaurant.utils.component.BlockSelection;

@LittleMaidExtension
public class MaidPlugin implements ILittleMaid {
    @Override
    public void addMaidTask(TaskManager manager){
        manager.add(new TaskCook());
        manager.add(new TaskWaiter());
    }

    @Override
    public void addExtraMaidBrain(ExtraMaidBrainManager manager) {
        manager.addExtraMaidBrain(new RestaurantMaidBrain());
    }

    @Override
    public void registerTaskData(TaskDataRegister register) {
        register.register(CookRequestHandler.TYPE);
        register.register(ServeRequestHandler.TYPE);
        register.register(WorldCookRequestHandler.TYPE);
        register.register(WorldServeRequestHandler.TYPE);
        register.register(BlockSelection.TYPE);
    }
}
