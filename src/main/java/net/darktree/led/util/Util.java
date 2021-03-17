package net.darktree.led.util;

import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class Util {

    public static VoxelShape box(int x1, int y1, int z1, int x2, int y2, int z2 ) {
        return VoxelShapes.cuboid( x1 / 16d, y1 / 16d, z1 / 16d, x2 / 16d, y2 / 16d, z2 / 16d );
    }

}
