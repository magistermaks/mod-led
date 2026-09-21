package net.darktree.led.block;

import net.darktree.led.util.DiodeVariant;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

public class DirectionalDiodeLampBlock extends DiodeLampBlock {

    protected static final EnumProperty<Direction> FACING = Properties.FACING;
    private final VoxelShape[] shapes;

    public DirectionalDiodeLampBlock(AbstractBlock.Settings settings, DiodeVariant variant, VoxelShape[] shapes) {
        super(settings, variant);
        this.shapes = shapes;
        setDefaultState( getDefaultState().with(FACING, Direction.NORTH) );
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        Direction facing = state.get(FACING);
        if (facing != direction || isDirectionValid(facing, (WorldAccess) world, pos)) {
            return state;
        }

        return Blocks.AIR.getDefaultState();
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getPlacementDirections()[0];
        WorldAccess worldAccess = ctx.getWorld();

        if (isDirectionValid(direction, worldAccess, ctx.getBlockPos())) {
            return getDefaultState().with(FACING, direction);
        }

        return null;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return shapes[state.get(FACING).getIndex()];
    }

    private boolean isDirectionValid(Direction direction, WorldAccess world, BlockPos pos) {
        return world.getBlockState(pos.offset(direction)).isSideSolidFullSquare( world, pos, direction.getOpposite() );
    }

}
