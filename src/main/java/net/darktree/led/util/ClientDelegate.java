package net.darktree.led.util;

import com.google.gson.JsonElement;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class ClientDelegate {

    public final Identifier id;
    public final Block block;
    public final Item item;
    public final DyeColor color;
    public final JsonElement recipe;

    public ClientDelegate(DyeColor color, Block block, Item item, Identifier id, JsonElement recipe) {
        this.color = color;
        this.block = block;
        this.item = item;
        this.id = id;
        this.recipe = recipe;
    }

    public int getTint() {
        return color.getFireworkColor();
    }

    public Identifier getItemModelPath() {
        return RegistryHelper.id("item/" + id.getPath());
    }

}
