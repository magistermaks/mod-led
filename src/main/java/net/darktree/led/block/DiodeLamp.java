package net.darktree.led.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class DiodeLamp extends Block {

    public static final BooleanProperty LIT = BooleanProperty.of("lit");
    private final VoxelShape shape;

    public static boolean emissive( BlockState state, BlockView world, BlockPos pos ) {
        return state.get(LIT);
    }

    public DiodeLamp( Settings settings, int light, VoxelShape shape ) {
        super( settings.luminance( (state) -> state.get(LIT) ? light : 0 ) );
        this.shape = shape;
        setDefaultState( getDefaultState().with(LIT, false) );
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return shape;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess worldAccess, BlockPos pos, BlockPos posFrom) {
        if( worldAccess instanceof World ) {
            World world = (World) worldAccess;

            return state.with(LIT, world.isReceivingRedstonePower(pos));
        }

        return state;
    }

}
