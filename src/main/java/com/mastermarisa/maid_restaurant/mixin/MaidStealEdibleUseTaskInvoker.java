package com.mastermarisa.maid_restaurant.mixin;

import com.github.tartaricacid.touhoulittlemaid.entity.ai.brain.task.MaidStealEdibleUseTask;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * TouhouLittleMaid 0.8.8 gates edible-block stealing behind a package-private
 * {@code canSteal} helper. {@link MaidStealEdibleMoveBlockTaskMixin} overwrites
 * the move task's start() and has to apply the same gate, so expose the original
 * instead of copying its condition, which would silently drift the next time
 * upstream changes what counts as stealable.
 */
@Mixin(MaidStealEdibleUseTask.class)
public interface MaidStealEdibleUseTaskInvoker {
    @Invoker("canSteal")
    static boolean invokeCanSteal(EntityMaid maid) {
        throw new AssertionError();
    }
}
