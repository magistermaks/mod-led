package net.darktree.led.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

public class ClientDelegate {

    private Block block = null;
    private Item item = null;
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

    @Environment(EnvType.CLIENT)
    public void register() {
        if( block != null ) {
            ColorProviderRegistry.BLOCK.register(
                    this::blockProvider,
                    block
            );
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
