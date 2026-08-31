package com.mastermarisa.maid_restaurant.init;

import com.mastermarisa.maid_restaurant.MaidRestaurant;
import com.mastermarisa.maid_restaurant.advancements.rewards.GivePatchouliBookConfigTrigger;
import com.mastermarisa.maid_restaurant.utils.RegistryRef;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public final class ModTrigger {
    public static final RegistryRef<GivePatchouliBookConfigTrigger> GIVE_PATCHOULI_BOOK_CONFIG =
            new RegistryRef<>(Registry.register(
                    BuiltInRegistries.TRIGGER_TYPES,
                    MaidRestaurant.resourceLocation("give_patchouli_book_config"),
                    new GivePatchouliBookConfigTrigger()));

    private ModTrigger() {
    }

    public static void register() {
        // Calling this method initializes the static Fabric registry entry.
    }
}
