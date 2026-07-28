package com.mastermarisa.maid_restaurant.client;

import com.mastermarisa.maid_restaurant.client.gui.screen.ordering.OrderingScreen;
import com.mastermarisa.maid_restaurant.network.OpenOrderingScreenPayload;
import com.mastermarisa.maid_restaurant.utils.CookTasks;
import com.mastermarisa.maid_restaurant.utils.EncodeUtils;
import com.mastermarisa.maid_restaurant.utils.component.RecipeData;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;

public final class ClientOrderingScreenHandler {
    private ClientOrderingScreenHandler() {
    }

    public static void handle(OpenOrderingScreenPayload payload) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) return;
        var recipes = new ArrayList<RecipeData>();
        for (var summary : payload.recipes()) {
            var type = CookTasks.getType(summary.type());
            if (type == null) continue;
            var task = CookTasks.getTask(type);
            if (task == null) continue;
            recipes.add(new RecipeData(
                    Identifier.parse(summary.id()),
                    type,
                    task.getIcon(),
                    summary.result().copy()
            ));
        }
        minecraft.setScreen(new OrderingScreen(
                minecraft.player,
                EncodeUtils.decode(payload.targets()),
                recipes
        ));
    }
}
