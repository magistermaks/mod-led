package net.darktree.led.block;

import net.darktree.led.util.LedType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class DirectionalDiodeLampBlock extends DiodeLampBlock {

    protected static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
    private final VoxelShape[] shapes;

    public DirectionalDiodeLampBlock(BlockBehaviour.Properties settings, LedType type, VoxelShape[] shapes) {
        super(settings, type);
        this.shapes = shapes;
        registerDefaultState( defaultBlockState().setValue(FACING, Direction.NORTH) );
    }

    public BlockState applyState(BlockState from, BlockState to) {
        return super.applyState(from, to).setValue(FACING, from.getValue(FACING));
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        Direction facing = state.getValue(FACING);
        if (facing != direction || isDirectionValid(facing, (LevelAccessor) world, pos)) {
            return state;
        }

        return Blocks.AIR.defaultBlockState();
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        for (Direction direction : context.getNearestLookingDirections()) {
            if (isDirectionValid(direction, context.getLevel(), context.getClickedPos())) {
                return defaultBlockState().setValue(FACING, direction);
            }
        }

        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return shapes[state.getValue(FACING).get3DDataValue()];
    }

    private boolean isDirectionValid(Direction direction, LevelAccessor world, BlockPos pos) {
        return world.getBlockState(pos.relative(direction)).isFaceSturdy( world, pos, direction.getOpposite() );
    }

}
