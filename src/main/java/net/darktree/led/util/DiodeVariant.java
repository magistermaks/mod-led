package net.darktree.led.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;

public enum DiodeVariant {
    NORMAL("", 15),
    SHADED("shaded_", 3);

    public interface RecipeDelegate {
        void register( Item item, DyeColor color );
    }

    final String prefix;
    final int light;

    DiodeVariant( String prefix, int light ) {
        this.prefix = prefix;
        this.light = light;
    }

    protected String getPrefix() {
        return prefix;
    }

    public int getLightLevel() {
        return light;
    }

    public String getName( String name ) {
        return getPrefix() + name;
    }

    private static String id( String name ) {
        return RegistryHelper.ID + ":" + name;
    }

    public RecipeDelegate getClearFullRecipe() {
        if( this == NORMAL ) {
            return (item, color) -> {
                KeyBuilder keys = KeyBuilder.make()
                        .addItem('A', id("led"))
                        .addItem('B', "minecraft:" + color.getName() + "_stained_glass_pane");

                RecipeHelper.createShaped(
                    new ItemStack(item),
                    "BBB,BAB,BBB", keys.get()
                );
            };
        }else{
            return (item, color) -> RecipeHelper.createShapeless(
                    new ItemStack(item),
                    id("shade"), id("clear_full_" + color.getName())
            );
        }
    }

    public RecipeDelegate getClearSmallRecipe() {
        if( this == NORMAL ) {
            return (item, color) -> {
                KeyBuilder keys = KeyBuilder.make()
                        .addItem('A', id("led"))
                        .addItem('B', "minecraft:" + color.getName() + "_stained_glass_pane")
                        .addItem('C', "minecraft:iron_nugget");

                RecipeHelper.createShaped(
                        new ItemStack(item),
                        " B , A ,CCC", keys.get()
                );
            };
        }else{
            return (item, color) -> RecipeHelper.createShapeless(
                    new ItemStack(item),
                    id("shade"), id("small_fixture_" + color.getName())
            );
        }
    }

    public RecipeDelegate getClearMediumRecipe() {
        if( this == NORMAL ) {
            return (item, color) -> {
                KeyBuilder keys = KeyBuilder.make()
                        .addItem('A', id("led"))
                        .addItem('B', "minecraft:" + color.getName() + "_stained_glass_pane")
                        .addItem('C', "minecraft:iron_nugget");

                RecipeHelper.createShaped(
                        new ItemStack(item),
                        " B ,BAB,CCC", keys.get()
                );
            };
        }else{
            return (item, color) -> RecipeHelper.createShapeless(
                    new ItemStack(item),
                    id("shade"), id("medium_fixture_" + color.getName())
            );
        }
    }

    public RecipeDelegate getClearLargeRecipe() {
        if( this == NORMAL ) {
            return (item, color) -> {
                KeyBuilder keys = KeyBuilder.make()
                        .addItem('A', id("led"))
                        .addItem('B', "minecraft:" + color.getName() + "_stained_glass_pane")
                        .addItem('C', "minecraft:iron_nugget");

                RecipeHelper.createShaped(
                        new ItemStack(item),
                        "BBB,BAB,CCC", keys.get()
                );
            };
        }else{
            return (item, color) -> RecipeHelper.createShapeless(
                    new ItemStack(item),
                    id("shade"), id("large_fixture_" + color.getName())
            );
        }
    }

}
