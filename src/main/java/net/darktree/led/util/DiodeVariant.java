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
            return (item, color) -> RecipeHelper.createShaped(
                    new ItemStack(item, 8), "AAA,ABA,AAA",
                    KeyBuilder.make().addItem('B', id("led")).addStainedGlass('A', color).get()
            );
        }else{
            return (item, color) -> RecipeHelper.createShapeless(
                    new ItemStack(item),
                    id("shade"), id("clear_full_" + color.getName())
            );
        }
    }

}
