package com.mastermarisa.maid_restaurant.item;

import com.mastermarisa.maid_restaurant.maid.TaskWaiter;
import com.mastermarisa.maid_restaurant.utils.component.BlockSelection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.ArrayList;

public class OrderMenuItem extends Item {
    public OrderMenuItem(Identifier id) {
        super(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)).stacksTo(1));
    }

    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();

        if (player != null && TaskWaiter.isValidServeBlock(level,pos))
            return InteractionResult.SUCCESS;

        return super.useOn(context);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        BlockSelection selection = player.getAttachedOrCreate(BlockSelection.ATTACHMENT);
        if (!player.isSecondaryUseActive() && !selection.menu.isEmpty()) {
            selection.menu.clear();
            player.setAttached(BlockSelection.ATTACHMENT, selection);
        }

        return super.use(level, player, usedHand);
    }
}
