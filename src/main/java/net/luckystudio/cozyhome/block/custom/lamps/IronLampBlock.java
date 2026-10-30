package net.luckystudio.cozyhome.block.custom.lamps;

import net.luckystudio.cozyhome.util.ModColorHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class IronLampBlock extends AbstractLampBlock {
    public static final VoxelShape SINGLE_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(5, 0, 5, 11, 14, 11),
            Block.createCuboidShape(3, 2, 3, 13, 12, 13));
    public static final VoxelShape MIDDLE_SHAPE = Block.createCuboidShape(6, 0, 6, 10, 16, 10);
    public static final VoxelShape BOTTOM_SHAPE = VoxelShapes.union(MIDDLE_SHAPE, Block.createCuboidShape(4, 0, 4, 12, 4, 12));
    public IronLampBlock(Settings settings) {
        super(settings);
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(CONNECTION)) {
            case MIDDLE -> MIDDLE_SHAPE;
            case TAIL -> BOTTOM_SHAPE;
            default -> SINGLE_SHAPE;
        };
    }

    // Gives the Iron Lamp that default looking color we all know
    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (world.getBlockEntity(pos) instanceof LampBlockEntity lampBlockEntity) {
            final int color = ModColorHandler.getItemColor(itemStack, -1005508);
            lampBlockEntity.setDyeColor(color);
            lampBlockEntity.markDirty();
            world.updateListeners(pos, state, state, 0);
        }
        super.onPlaced(world, pos, state, placer, itemStack);
    }
}
