package net.darktree.led.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.DyeColor;

public class ClientDelegate {

    public final Block block;
    public final Item item;
    public final DyeColor color;

    public ClientDelegate(DyeColor color, Block block, Item item) {
        this.color = color;
        this.block = block;
        this.item = item;
    }

    public int getTint() {
        return color.getFireworkColor();
    }

}
