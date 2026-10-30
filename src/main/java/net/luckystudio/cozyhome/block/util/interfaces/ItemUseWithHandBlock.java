package net.luckystudio.cozyhome.block.util.interfaces;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Bridges 1.21's item-aware block interaction to Fabric 1.20.1's use-block event. */
public interface ItemUseWithHandBlock {
    ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos,
                               PlayerEntity player, Hand hand, BlockHitResult hit);
}