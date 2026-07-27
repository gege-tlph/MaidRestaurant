package com.mastermarisa.maid_restaurant.init;

import com.mastermarisa.maid_restaurant.advancements.rewards.GivePatchouliBookConfigTrigger;
import com.mastermarisa.maid_restaurant.utils.RegistryRef;

public final class ModTrigger {
    public static final RegistryRef<GivePatchouliBookConfigTrigger> GIVE_PATCHOULI_BOOK_CONFIG =
            new RegistryRef<>(new GivePatchouliBookConfigTrigger());

    public static void register() {
        // Calling this method initializes the login-reward trigger holder.
    }
}
