package net.darktree.led.util;

import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class Util {

    public static VoxelShape box(int x1, int y1, int z1, int x2, int y2, int z2 ) {
        return VoxelShapes.cuboid( x1 / 16d, y1 / 16d, z1 / 16d, x2 / 16d, y2 / 16d, z2 / 16d );
    }

    public static VoxelShape[] getRotatedVariantArray( int a, int b, int c, int d, int e, int f ) {
        return new VoxelShape[] {
                box( a, b, c, d, e, f ),
                box( a, 16 - b, c, d, 16 - e, f ),
                box( a, c, b, d, f, e ),
                box( a, c, 16 - b, d, f, 16 - e ),
                box( b, a, c, e, d, f ),
                box( 16 - b, a, c, 16 - e, d, f )
        };
    }

}
