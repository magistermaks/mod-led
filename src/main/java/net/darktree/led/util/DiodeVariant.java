package net.darktree.led.util;

import com.google.gson.JsonElement;
import net.darktree.led.LED;
import net.minecraft.block.AbstractBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;

public enum DiodeVariant {
    NORMAL("", 15, null, false),
    REINFORCED("reinforced_", 14, "tooltip.led.reinforced", true),
    SHADED("shaded_", 0, "tooltip.led.shaded", false),
    SHADED_REINFORCED("shaded_reinforced_", 0, "tooltip.led.shaded_and_reinforced", true);

    public interface RecipeDelegate {
        JsonElement get(Item item, DyeColor color);
    }

    final String prefix;
    final int light;
    final String tooltip;
    final boolean reinforced;

    DiodeVariant(String prefix, int light, String tooltip, boolean reinforced) {
        this.prefix = prefix;
        this.light = light;
        this.tooltip = tooltip;
        this.reinforced = reinforced;
    }

    public AbstractBlock.Settings applySettings(AbstractBlock.Settings settings) {
        return settings.sounds(BlockSoundGroup.METAL).strength(reinforced ? 0.8f : 0.4f);
    }

    public int getLightLevel() {
        return light;
    }

    public String getTooltip() {
        return tooltip;
    }

    public String getName(String name) {
        return prefix + name;
    }

    private static String id(String name) {
        return LED.ID + ":" + name;
    }

    private String group(String name) {
        return "led_" + prefix + name;
    }

    public RecipeDelegate getRecipe(String pattern, String name) {
        return switch (this) {
            case NORMAL -> (item, color) -> {
                KeySetBuilder keys = new KeySetBuilder()
                        .addItem('A', id("led"))
                        .addItem('B', "minecraft:" + color.getName() + "_stained_glass_pane");

                if (pattern.contains("C")) {
                    keys.addItem('C', "minecraft:iron_nugget");
                }

                return RecipeHelper.createShaped(new ItemStack(item), pattern, keys.get(), group(name));
            };

            case REINFORCED -> (item, color) -> RecipeHelper.createShapeless(
                    new ItemStack(item), group(name),
                    "minecraft:iron_bars", id(name + "_" + color.getName())
            );

            case SHADED -> (item, color) -> RecipeHelper.createShapeless(
                    new ItemStack(item), group(name),
                    id("shade"), id(name + "_" + color.getName())
            );

            case SHADED_REINFORCED -> (item, color) -> RecipeHelper.createShapeless(
                    new ItemStack(item), group(name),
                    id("shade"), "minecraft:iron_bars", id(name + "_" + color.getName())
            );
        };
    }

    public static RecipeDelegate getButtonRecipe(boolean button) {
        if (button) {
            return (item, color) -> RecipeHelper.createShapeless(
                    new ItemStack(item, 4), "led_button",
                    id("clear_full_" + color.getName())
            );
        } else {
            return (item, color) -> RecipeHelper.createShapeless(
                    new ItemStack(item), "led_switch",
                    "minecraft:lever", id("button_" + color.getName())
            );
        }
    }


}
