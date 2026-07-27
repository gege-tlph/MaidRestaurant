package com.mastermarisa.maid_restaurant.utils;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

/**
 * Small compatibility layer for the 1.21.11 recipe API.  RecipeManager is no
 * longer exposed from Level; on the server it is obtained from MinecraftServer.
 */
public final class RecipeAccess {
    private RecipeAccess() {}

    public static RecipeManager manager(Level level) {
        MinecraftServer server = level.getServer();
        if (server == null) {
            throw new IllegalStateException("RecipeManager is only available on the server in 1.21.11");
        }
        return server.getRecipeManager();
    }

    @SuppressWarnings("unchecked")
    public static <T extends Recipe<?>> Optional<RecipeHolder<T>> byId(Level level, Identifier id) {
        ResourceKey<Recipe<?>> key = ResourceKey.create(Registries.RECIPE, id);
        return manager(level).byKey(key).map(holder -> (RecipeHolder<T>) holder);
    }

    public static <T extends Recipe<?>> RecipeHolder<T> require(Level level, Identifier id) {
        return (RecipeHolder<T>) byId(level, id).orElseThrow(() -> new IllegalArgumentException("Unknown recipe: " + id));
    }

    @SuppressWarnings("unchecked")
    public static <T extends Recipe<?>> List<RecipeHolder<T>> allOf(Level level, RecipeType<T> type) {
        return manager(level).getRecipes().stream()
                .filter(holder -> holder.value().getType() == type)
                .map(holder -> (RecipeHolder<T>) holder)
                .toList();
    }
}
