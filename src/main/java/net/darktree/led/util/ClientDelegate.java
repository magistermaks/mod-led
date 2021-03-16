package net.darktree.led.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

public class ClientDelegate {

    private Block block = null;
    private Item item = null;
    private boolean translucentFallback = true;
    private final DyeColor color;

    public ClientDelegate( DyeColor color ) {
        this.color = color;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void useTranslucentFallback() {
        translucentFallback = true;
    }

    @Environment(EnvType.CLIENT)
    public void register(boolean jmx) {
        if( block != null ) {
            ColorProviderRegistry.BLOCK.register(
                    this::blockProvider,
                    block
            );

            // This won't work as good as it would with JMX materials,
            // but its the best we can do without some FRAPI shenanigans.
            if( translucentFallback && !jmx ) {
                BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent());
            }
        }

        if( item != null ) {
            ColorProviderRegistry.ITEM.register(
                    this::itemProvider,
                    item
            );
        }
    }

    @Environment(EnvType.CLIENT)
    private int blockProvider(BlockState state, BlockRenderView world, BlockPos pos, int tintIndex) {
        return color.getFireworkColor();
    }

    @Environment(EnvType.CLIENT)
    private int itemProvider(ItemStack stack, int tintIndex) {
        return color.getFireworkColor();
    }

}
