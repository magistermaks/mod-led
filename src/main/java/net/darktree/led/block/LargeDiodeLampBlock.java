package net.darktree.led.block;

import net.darktree.led.util.Util;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class LargeDiodeLampBlock extends DirectionalDiodeLampBlock {

    private static final VoxelShape[] SHAPES = {
            Util.box(3, 0, 3, 13, 1, 13),
            Util.box(3, 16, 3, 13, 15, 13),
            Util.box(3, 3, 0, 13, 13, 1),
            Util.box(3, 3, 16, 13, 13, 15),
            Util.box(0, 3, 3, 1, 13, 13),
            Util.box(16, 3, 3, 15, 13, 13)
    };

    public LargeDiodeLampBlock(Settings settings, int light, boolean shaded) {
        super(settings, light, shaded);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES[ state.get(FACING).getId() ];
    }
}
