package net.darktree.led.block;

import net.darktree.led.util.Util;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class SmallIndicatorBlock extends DirectionalDiodeLamp {

    private static final VoxelShape[] SHAPES = {
            Util.box(4, 0, 4, 12, 1, 12),
            Util.box(4, 16, 4, 12, 15, 12),
            Util.box(4, 4, 0, 12, 12, 1),
            Util.box(4, 4, 16, 12, 12, 15),
            Util.box(0, 4, 4, 1, 12, 12),
            Util.box(16, 4, 4, 15, 12, 12)
    };

    public SmallIndicatorBlock(Settings settings, int light, boolean shaded) {
        super(settings, light, shaded);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES[ state.get(FACING).getId() ];
    }
}
