package net.darktree.led.util;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;

public enum DiodeVariant {
    NORMAL("", 15),
    NORMAL_REINFORCED("reinforced_", 14),
    SHADED("shaded_", 3),
    SHADED_REINFORCED("shaded_reinforced_", 3);

    public interface RecipeDelegate {
        void register( Item item, DyeColor color );
    }

    final String prefix;
    final int light;

    DiodeVariant( String prefix, int light ) {
        this.prefix = prefix;
        this.light = light;
    }

    public FabricBlockSettings settings() {
        return RegistryHelper.settings().strength( isReinforced() ? 0.8f : 0.4f );
    }

    public int getLightLevel() {
        return light;
    }

    public boolean isShaded() {
        return this == SHADED || this == SHADED_REINFORCED;
    }

    public boolean isReinforced() {
        return this == NORMAL_REINFORCED || this == SHADED_REINFORCED;
    }

    public String getName( String name ) {
        return prefix + name;
    }

    private static String id( String name ) {
        return RegistryHelper.ID + ":" + name;
    }

    public RecipeDelegate getRecipe( String pattern, String name ) {
        if( this == NORMAL ) {
            return (item, color) -> {
                KeyBuilder keys = new KeyBuilder()
                        .addItem('A', id("led"))
                        .addItem('B', "minecraft:" + color.getName() + "_stained_glass_pane")
                        .addItem('C', "minecraft:iron_nugget");

                RecipeHelper.createShaped( new ItemStack(item), pattern, keys.get() );
            };
        }

        if( this == NORMAL_REINFORCED ) {
            return (item, color) -> RecipeHelper.createShapeless(
                    new ItemStack(item),
                    "minecraft:iron_bars", id(name + "_" + color.getName())
            );
        }

        if( this == SHADED ) {
            return (item, color) -> RecipeHelper.createShapeless(
                    new ItemStack(item),
                    id("shade"), id(name + "_" + color.getName())
            );
        }

        if( this == SHADED_REINFORCED ) {
            return (item, color) -> RecipeHelper.createShapeless(
                    new ItemStack(item),
                    id("shade"), "minecraft:iron_bars", id(name + "_" + color.getName())
            );
        }

        return null;
    }

    public static RecipeDelegate getButtonRecipe( boolean button ) {
        if( button ) {
            return (item, color) -> RecipeHelper.createShapeless(
                    new ItemStack(item, 4),
                    id("clear_full_" + color.getName())
            );
        }else{
            return (item, color) -> RecipeHelper.createShapeless(
                    new ItemStack(item),
                    "minecraft:lever", id("button_" + color.getName())
            );
        }
    }


}
