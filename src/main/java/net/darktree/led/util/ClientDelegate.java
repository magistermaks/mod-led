package net.darktree.led.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.List;

public class ClientDelegate {

    public final Identifier id;
    public final Block block;
    public final Item item;
    public final DyeColor color;
    public final LedVariant.RecipeFactory factory;

    public ClientDelegate(DyeColor color, Block block, Item item, Identifier id, LedVariant.RecipeFactory factory) {
        this.color = color;
        this.block = block;
        this.item = item;
        this.id = id;
        this.factory = factory;
    }

    public record RecipeInfo (Recipe<?> recipe, RegistryKey<Recipe<?>> key) {}

    public int getTint() {
        return color.getFireworkColor();
    }

    public Identifier getItemModelPath() {
        return RegistryHelper.id("item/" + id.getPath());
    }

    public RegistryKey<Recipe<?>> getRecipeKey() {
        return RegistryKey.of(RegistryKeys.RECIPE, id);
    }

    public void addRecipes(List<RecipeInfo> recipes) {
        factory.apply((recipe, id) -> recipes.add(new RecipeInfo(recipe, RegistryKey.of(RegistryKeys.RECIPE, id))), item, color);
    }

}
