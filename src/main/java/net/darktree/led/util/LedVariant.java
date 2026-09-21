package net.darktree.led.util;

import net.darktree.led.LED;
import net.minecraft.block.AbstractBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public enum LedVariant {
    NORMAL("", 15, null, false),
    REINFORCED("reinforced_", 14, "tooltip.led.reinforced", true),
    SHADED("shaded_", 0, "tooltip.led.shaded", false),
    SHADED_REINFORCED("shaded_reinforced_", 0, "tooltip.led.shaded_and_reinforced", true);

    public interface RecipeFactory {
        void apply(BiConsumer<Recipe<?>, Identifier> consumer, Item item, DyeColor color);
    }

    final String prefix;
    final int light;
    final String tooltip;
    final boolean reinforced;

    LedVariant(String prefix, int light, String tooltip, boolean reinforced) {
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

    private String getRecipeGroup(LedFixture fixture) {
        return "led_" + prefix + fixture.getId();
    }

    private static Item getStainedGlassPane(DyeColor color) {
        return Registries.ITEM.get(Identifier.of("minecraft:" + color.getId() + "_stained_glass_pane"));
    }

    private Item getItem(LedFixture fixture, DyeColor color) {
        return RegistryHelper.FIXTURES.getBlock(fixture, this, color).asItem();
    }

    public RecipeFactory getRecipeFactory(String pattern, LedFixture fixture) {
        final String[] parts = pattern.split(",");
        final CraftingRecipeCategory category = LED.CATEGORY;
        final String group = getRecipeGroup(fixture);

        return switch (this) {
            case NORMAL -> (consumer, item, color) -> {
                Map<Character, Ingredient> ingredients = new HashMap<>();

                if (pattern.contains("A")) ingredients.put('A', Ingredient.ofItem(LED.LED));
                if (pattern.contains("C")) ingredients.put('C', Ingredient.ofItem(Items.IRON_NUGGET));
                if (pattern.contains("B")) ingredients.put('B', Ingredient.ofItem(getStainedGlassPane(color)));

                consumer.accept(new ShapedRecipe(group, category, RawShapedRecipe.create(ingredients, parts), new ItemStack(item)), Registries.ITEM.getId(item));
            };

            case REINFORCED -> (consumer, item, color) -> {
                consumer.accept(new ShapelessRecipe(group, category, new ItemStack(item), List.of(
                        Ingredient.ofItem(Items.IRON_BARS),
                        Ingredient.ofItem(NORMAL.getItem(fixture, color))
                )), Registries.ITEM.getId(item));
            };

            case SHADED -> (consumer, item, color) -> {
                consumer.accept(new ShapelessRecipe(group, category, new ItemStack(item), List.of(
                        Ingredient.ofItem(LED.SHADE),
                        Ingredient.ofItem(NORMAL.getItem(fixture, color))
                )), Registries.ITEM.getId(item));
            };

            case SHADED_REINFORCED -> (consumer, item, color) -> {
                consumer.accept(new ShapelessRecipe(group, category, new ItemStack(item), List.of(
                        Ingredient.ofItem(LED.SHADE),
                        Ingredient.ofItem(Items.IRON_BARS),
                        Ingredient.ofItem(NORMAL.getItem(fixture, color))
                )), Registries.ITEM.getId(item));

                consumer.accept(new ShapelessRecipe(group, category, new ItemStack(item), List.of(
                        Ingredient.ofItem(LED.SHADE),
                        Ingredient.ofItem(REINFORCED.getItem(fixture, color))
                )), Registries.ITEM.getId(item).withSuffixedPath("_from_reinforced"));

                consumer.accept(new ShapelessRecipe(group, category, new ItemStack(item), List.of(
                        Ingredient.ofItem(Items.IRON_BARS),
                        Ingredient.ofItem(SHADED.getItem(fixture, color))
                )), Registries.ITEM.getId(item).withSuffixedPath("_from_shaded"));
            };
        };
    }

    public static RecipeFactory getButtonRecipeFactory() {
        return (consumer, item, color) -> {
            consumer.accept(new ShapelessRecipe("led_button", LED.CATEGORY, new ItemStack(item, 4), List.of(
                    Ingredient.ofItem(RegistryHelper.FIXTURES.getBlock(LedFixture.FULL, LedVariant.NORMAL, color).asItem())
            )), Registries.ITEM.getId(item));
        };
    }

    public static RecipeFactory getSwitchRecipeFactory() {
        return (consumer, item, color) -> {
            consumer.accept(new ShapelessRecipe("led_switch", LED.CATEGORY, new ItemStack(item), List.of(
                    Ingredient.ofItem(RegistryHelper.FIXTURES.getBlock(LedFixture.BUTTON, LedVariant.NORMAL, color).asItem()),
                    Ingredient.ofItem(Items.LEVER)
            )), Registries.ITEM.getId(item));
        };
    }


}
