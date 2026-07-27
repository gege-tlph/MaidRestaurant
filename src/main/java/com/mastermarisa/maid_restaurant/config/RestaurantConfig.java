package com.mastermarisa.maid_restaurant.config;

/**
 * Fabric-side configuration defaults. A Config API integration can replace
 * these values without changing gameplay call sites.
 */
public final class RestaurantConfig {
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

    public static void register() {
    }
}
