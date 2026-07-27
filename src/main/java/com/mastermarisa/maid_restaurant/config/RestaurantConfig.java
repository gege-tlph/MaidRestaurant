package com.mastermarisa.maid_restaurant.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mastermarisa.maid_restaurant.MaidRestaurant;
import net.fabricmc.loader.api.FabricLoader;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Small, stable Fabric-side configuration backend. Keeping this file local to
 * Maid Restaurant avoids making gameplay depend on an optional config screen
 * mod while still persisting the two migrated server-side options.
 */
public final class RestaurantConfig {
    private static final String FILE_NAME = "maid_restaurant.json";
    private static boolean sitWhileCooking = true;
    private static boolean givePatchouliBook = true;

    private RestaurantConfig() {
    }

    public static boolean SIT_WHILE_COOKING() {
        return sitWhileCooking;
    }

    public static boolean GIVE_PATCHOULI_BOOK() {
        return givePatchouliBook;
    }

    public static synchronized void register() {
        Path path = FabricLoader.getInstance().getConfigDir().resolve(FILE_NAME);
        try {
            Files.createDirectories(path.getParent());
            if (Files.exists(path)) {
                try (Reader reader = Files.newBufferedReader(path)) {
                    JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                    if (json.has("sit_while_cooking")) {
                        sitWhileCooking = json.get("sit_while_cooking").getAsBoolean();
                    }
                    if (json.has("give_patchouli_book")) {
                        givePatchouliBook = json.get("give_patchouli_book").getAsBoolean();
                    }
                }
            }
            save(path);
        } catch (Exception exception) {
            MaidRestaurant.LOGGER.warn("Could not load Maid Restaurant config; using defaults", exception);
        }
    }

    private static void save(Path path) throws Exception {
        JsonObject json = new JsonObject();
        json.addProperty("sit_while_cooking", sitWhileCooking);
        json.addProperty("give_patchouli_book", givePatchouliBook);
        try (Writer writer = Files.newBufferedWriter(path)) {
            MaidRestaurant.GSON.toJson(json, writer);
        }
    }
}
