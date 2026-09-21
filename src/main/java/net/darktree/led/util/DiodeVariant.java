package net.darktree.led.util;

import net.darktree.led.LED;
import net.minecraft.block.AbstractBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum DiodeVariant {
    NORMAL("", 15, null, false),
    REINFORCED("reinforced_", 14, "tooltip.led.reinforced", true),
    SHADED("shaded_", 0, "tooltip.led.shaded", false),
    SHADED_REINFORCED("shaded_reinforced_", 0, "tooltip.led.shaded_and_reinforced", true);

    public interface RecipeFactory {
        Recipe<?> get(Item item, DyeColor color);
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

    private String getGroup(String name) {
        return "led_" + prefix + name;
    }

    private static Item getStainedGlassPane(DyeColor color) {
        return Registries.ITEM.get(Identifier.of("minecraft:" + color.getId() + "_stained_glass_pane"));
    }

    private static Item getColoredItem(String name, DyeColor color) {
        return Registries.ITEM.get(RegistryHelper.id(name + "_" + color.getId()));
    }

    public RecipeFactory getRecipeFactory(String pattern, String component) {
        final String[] parts = pattern.split(",");
        final CraftingRecipeCategory category = LED.CATEGORY;

        return switch (this) {
            case NORMAL -> (item, color) -> {
                Map<Character, Ingredient> ingredients = new HashMap<>();

                if (pattern.contains("A")) ingredients.put('A', Ingredient.ofItem(LED.LED));
                if (pattern.contains("C")) ingredients.put('C', Ingredient.ofItem(Items.IRON_NUGGET));
                if (pattern.contains("B")) ingredients.put('B', Ingredient.ofItem(getStainedGlassPane(color)));

                return new ShapedRecipe(getGroup(component), category, RawShapedRecipe.create(ingredients, parts), new ItemStack(item));
            };

            case REINFORCED -> (item, color) -> {
                return new ShapelessRecipe(getGroup(component), category, new ItemStack(item), List.of(
                        Ingredient.ofItem(Items.IRON_BARS),
                        Ingredient.ofItem(getColoredItem(component, color))
                ));
            };

            case SHADED -> (item, color) -> {
                return new ShapelessRecipe(getGroup(component), category, new ItemStack(item), List.of(
                        Ingredient.ofItem(LED.SHADE),
                        Ingredient.ofItem(getColoredItem(component, color))
                ));
            };

            case SHADED_REINFORCED -> (item, color) -> {
                return new ShapelessRecipe(getGroup(component), category, new ItemStack(item), List.of(
                        Ingredient.ofItem(LED.SHADE),
                        Ingredient.ofItem(Items.IRON_BARS),
                        Ingredient.ofItem(getColoredItem(component, color))
                ));
            };
        };
    }

    public static RecipeFactory getButtonRecipeFactory() {
        return (item, color) -> {
            return new ShapelessRecipe("led_button", LED.CATEGORY, new ItemStack(item, 4), List.of(
                    Ingredient.ofItem(getColoredItem("clear_full", color))
            ));
        };
    }

    public static RecipeFactory getSwitchRecipeFactory() {
        return (item, color) -> {
            return new ShapelessRecipe("led_switch", LED.CATEGORY, new ItemStack(item), List.of(
                    Ingredient.ofItem(getColoredItem("button", color)),
                    Ingredient.ofItem(Items.LEVER)
            ));
        };
    }


}
