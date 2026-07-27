package com.mastermarisa.maid_restaurant.client.event;

/**
 * Client bootstrap for the Fabric port. Item-model predicates and entity
 * renderer registration are enabled once the corresponding Fabric registries
 * are migrated; keeping this entrypoint side-safe lets common code compile on
 * a dedicated server during the port.
 */
public final class ClientSetup {
    private ClientSetup() {
    }

    public static void register() {
        // Registration is intentionally staged with ModItems/ModEntities.
    }
}
