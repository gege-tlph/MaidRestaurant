package com.mastermarisa.maid_restaurant.data;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public final class DataGenerators implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ItemModelProvider::new);
        pack.addProvider(TagBlock::new);
        pack.addProvider(TagItem::new);
    }

    private static final class ItemModelProvider implements DataProvider {
        private final Path outputRoot;

        private ItemModelProvider(FabricDataOutput output) {
            this.outputRoot = output.getOutputFolder();
        }

        @Override
        public CompletableFuture<?> run(CachedOutput cache) {
            Path itemRoot = outputRoot.resolve("assets/maid_restaurant/items");
            JsonObject orderMenu = new JsonObject();
            JsonObject orderMenuModel = new JsonObject();
            orderMenuModel.addProperty("type", "minecraft:model");
            orderMenuModel.addProperty("model", "maid_restaurant:item/order_menu");
            orderMenu.add("model", orderMenuModel);

            JsonObject orderItem = new JsonObject();
            JsonObject orderItemModel = new JsonObject();
            orderItemModel.addProperty("type", "minecraft:model");
            orderItemModel.addProperty("model", "maid_restaurant:item/order_item_no_requests");
            orderItem.add("model", orderItemModel);

            CompletableFuture<?> menu = DataProvider.saveStable(
                    cache, orderMenu, itemRoot.resolve("order_menu.json"));
            CompletableFuture<?> item = DataProvider.saveStable(
                    cache, orderItem, itemRoot.resolve("order_item.json"));
            return CompletableFuture.allOf(menu, item);
        }

        @Override
        public String getName() {
            return "Maid Restaurant item models";
        }
    }
}
