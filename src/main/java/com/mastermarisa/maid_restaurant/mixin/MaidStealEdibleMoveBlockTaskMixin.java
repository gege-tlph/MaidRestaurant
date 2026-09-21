package com.mastermarisa.maid_restaurant.mixin;

import com.github.tartaricacid.touhoulittlemaid.entity.ai.brain.task.MaidMoveToBlockTask;
import com.github.tartaricacid.touhoulittlemaid.entity.ai.brain.task.MaidStealEdibleMoveBlockTask;
import com.github.tartaricacid.touhoulittlemaid.entity.ai.edible.MaidEdibleBlockAction;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.init.InitEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({MaidStealEdibleMoveBlockTask.class})
public class MaidStealEdibleMoveBlockTaskMixin extends MaidMoveToBlockTask {
    @Shadow
    private final MemoryModuleType<MaidEdibleBlockAction> action;

    public MaidStealEdibleMoveBlockTaskMixin(float movementSpeed) {
        super(movementSpeed, 2);
        this.setMaxCheckRate(900);
        this.action = InitEntities.MAID_EDIBLE_BLOCK_ACTION;
    }

    /**
     * @author MasterMarisa
     * @reason DisableEdibleBlockPlacingOnTable
     */
    @Overwrite
    protected void start(ServerLevel worldIn, EntityMaid maid, long gameTimeIn) {
        // Restaurant tables have to stay clear, so upstream's TRY_PLACE branch is
        // dropped and the action always falls back to TRY_STEAL. Everything after
        // that mirrors TouhouLittleMaid 0.8.8's start(): the steal attempt is
        // gated on canSteal, and a search that found a target arms the hold so the
        // maid stops re-picking a destination on every check.
        maid.getBrain().setMemory(this.action, MaidEdibleBlockAction.TRY_STEAL);

        if (MaidStealEdibleUseTaskInvoker.invokeCanSteal(maid)) {
            this.searchForDestination(worldIn, maid);
            armTargetHold(worldIn, maid);
        }
    }

    @Shadow
    private static void armTargetHold(ServerLevel worldIn, EntityMaid maid) {
        throw new AssertionError();
    }

    @Shadow
    protected boolean shouldMoveTo(ServerLevel worldIn, EntityMaid maid, BlockPos pos) {
        return false;
    }
}
