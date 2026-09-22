package net.darktree.led.util;

import net.darktree.led.LED;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public enum LedVariant {
    NORMAL("", 15, null, false, false),
    REINFORCED("reinforced_", 14, "tooltip.led.reinforced", true, false),
    SHADED("shaded_", 0, "tooltip.led.shaded", false, true),
    SHADED_REINFORCED("shaded_reinforced_", 0, "tooltip.led.shaded_and_reinforced", true, true);

    public interface RecipeFactory {
        void apply(BiConsumer<Recipe<?>, Identifier> consumer, Item item, DyeColor color);
    }

    final String prefix;
    final int light;
    final String tooltip;
    final boolean reinforced;
    final boolean shaded;

    LedVariant(String prefix, int light, String tooltip, boolean reinforced, boolean shaded) {
        this.prefix = prefix;
        this.light = light;
        this.tooltip = tooltip;
        this.reinforced = reinforced;
        this.shaded = shaded;
    }

    public static LedVariant byTrait(boolean reinforced, boolean shaded) {
        return values()[(reinforced ? 1 : 0) + (shaded ? 2 : 0)];
    }

    public boolean isReinforced() {
        return reinforced;
    }

    public boolean isShaded() {
        return shaded;
    }

    public LedVariant withShaded(boolean shaded) {
        return byTrait(this.reinforced, shaded);
    }

    public LedVariant withReinforced(boolean reinforced) {
        return byTrait(reinforced, this.shaded);
    }

    public BlockBehaviour.Properties applySettings(BlockBehaviour.Properties settings) {
        return settings.sound(SoundType.METAL).strength(reinforced ? 0.8f : 0.3f);
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
        return BuiltInRegistries.ITEM.getValue(Identifier.parse("minecraft:" + color.getName() + "_stained_glass_pane"));
    }

    public Item getItem(LedFixture fixture, DyeColor color) {
        return RegistryHelper.FIXTURES.getBlock(fixture, this, color).asItem();
    }

    public RecipeFactory getRecipeFactory(String pattern, LedFixture fixture) {
        final String[] parts = pattern.split(",");
        final CraftingBookCategory category = LED.CATEGORY;
        final String group = getRecipeGroup(fixture);

        return switch (this) {
            case NORMAL -> (consumer, item, color) -> {
                Map<Character, Ingredient> ingredients = new HashMap<>();

                if (pattern.contains("A")) ingredients.put('A', Ingredient.of(LED.LED));
                if (pattern.contains("C")) ingredients.put('C', Ingredient.of(Items.IRON_NUGGET));
                if (pattern.contains("B")) ingredients.put('B', Ingredient.of(getStainedGlassPane(color)));

                consumer.accept(RecipeHelper.shaped(group, category, item, 1, ingredients, parts), BuiltInRegistries.ITEM.getKey(item));
            };

            case REINFORCED -> (consumer, item, color) -> {
                consumer.accept(RecipeHelper.shapeless(group, category, item, 1, List.of(
                        Ingredient.of(Items.IRON_BARS),
                        Ingredient.of(NORMAL.getItem(fixture, color))
                )), BuiltInRegistries.ITEM.getKey(item));
            };

            case SHADED -> (consumer, item, color) -> {
                consumer.accept(RecipeHelper.shapeless(group, category, item, 1, List.of(
                        Ingredient.of(LED.SHADE),
                        Ingredient.of(NORMAL.getItem(fixture, color))
                )), BuiltInRegistries.ITEM.getKey(item));
            };

            case SHADED_REINFORCED -> (consumer, item, color) -> {
                consumer.accept(RecipeHelper.shapeless(group, category, item, 1, List.of(
                        Ingredient.of(LED.SHADE),
                        Ingredient.of(Items.IRON_BARS),
                        Ingredient.of(NORMAL.getItem(fixture, color))
                )), BuiltInRegistries.ITEM.getKey(item));

                consumer.accept(RecipeHelper.shapeless(group, category, item, 1, List.of(
                        Ingredient.of(LED.SHADE),
                        Ingredient.of(REINFORCED.getItem(fixture, color))
                )), BuiltInRegistries.ITEM.getKey(item).withSuffix("_from_reinforced"));

                consumer.accept(RecipeHelper.shapeless(group, category, item, 1, List.of(
                        Ingredient.of(Items.IRON_BARS),
                        Ingredient.of(SHADED.getItem(fixture, color))
                )), BuiltInRegistries.ITEM.getKey(item).withSuffix("_from_shaded"));
            };
        };
    }

    public static RecipeFactory getButtonRecipeFactory() {
        return (consumer, item, color) -> {
            consumer.accept(RecipeHelper.shapeless("led_button", LED.CATEGORY, item, 4, List.of(
                    Ingredient.of(RegistryHelper.FIXTURES.getBlock(LedFixture.FULL, LedVariant.NORMAL, color).asItem())
            )), BuiltInRegistries.ITEM.getKey(item));
        };
    }

    public static RecipeFactory getSwitchRecipeFactory() {
        return (consumer, item, color) -> {
            consumer.accept(RecipeHelper.shapeless("led_switch", LED.CATEGORY, item, 1, List.of(
                    Ingredient.of(RegistryHelper.FIXTURES.getBlock(LedFixture.BUTTON, LedVariant.NORMAL, color).asItem()),
                    Ingredient.of(Items.LEVER)
            )), BuiltInRegistries.ITEM.getKey(item));
        };
    }


}
