package com.mastermarisa.maid_restaurant.advancements.rewards;

import com.mastermarisa.maid_restaurant.MaidRestaurant;
import com.mastermarisa.maid_restaurant.config.RestaurantConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class GivePatchouliBookConfigTrigger {
    private static final Identifier GUIDE_BOOK = Identifier.fromNamespaceAndPath("patchouli", "guide_book");
    private static final Identifier BOOK_COMPONENT = Identifier.fromNamespaceAndPath("patchouli", "book");
    private static final Identifier BOOK_ID = MaidRestaurant.resourceLocation("your_maid_serves");

    public void trigger(ServerPlayer player) {
        if (!RestaurantConfig.GIVE_PATCHOULI_BOOK()
                || !FabricLoader.getInstance().isModLoaded("patchouli")) {
            return;
        }

        Item guideBook = BuiltInRegistries.ITEM.getOptional(GUIDE_BOOK).orElse(Items.AIR);
        DataComponentType<?> component = BuiltInRegistries.DATA_COMPONENT_TYPE
                .getOptional(BOOK_COMPONENT)
                .orElse(null);
        if (guideBook == Items.AIR || component == null) {
            MaidRestaurant.LOGGER.warn("Patchouli is loaded but its guide book component is unavailable");
            return;
        }

        if (player.getInventory().contains(stack -> stack.is(guideBook)
                && BOOK_ID.equals(getBookComponent(stack, component)))) {
            return;
        }

        ItemStack book = new ItemStack(guideBook);
        setBookComponent(book, component);
        if (!player.getInventory().add(book)) {
            player.drop(book, false);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void setBookComponent(ItemStack stack, DataComponentType<?> component) {
        stack.set((DataComponentType) component, BOOK_ID);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Object getBookComponent(ItemStack stack, DataComponentType<?> component) {
        return stack.get((DataComponentType) component);
    }
}
