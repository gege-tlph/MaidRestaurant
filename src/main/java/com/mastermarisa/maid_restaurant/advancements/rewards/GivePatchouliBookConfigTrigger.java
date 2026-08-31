package com.mastermarisa.maid_restaurant.advancements.rewards;

import com.mastermarisa.maid_restaurant.config.RestaurantConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

/**
 * Fires once per login and lets the {@code maid_restaurant:grant_patchouli_book}
 * advancement hand out the guide book. Advancement progress is stored per player,
 * so the book is granted exactly once and is not re-issued after the player drops
 * it, stores it, dies, or rejoins.
 */
public class GivePatchouliBookConfigTrigger extends SimpleCriterionTrigger<GivePatchouliBookConfigTrigger.Instance> {
    public void trigger(ServerPlayer serverPlayer) {
        super.trigger(serverPlayer, instance -> RestaurantConfig.GIVE_PATCHOULI_BOOK());
    }

    @Override
    public Codec<Instance> codec() {
        return Instance.CODEC;
    }

    public record Instance(Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(Instance::player)
        ).apply(instance, Instance::new));
    }
}
