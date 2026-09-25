package net.darktree.led.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;

import java.util.List;
import java.util.Map;

public class RecipeHelper {

	public static ShapedRecipe shaped(String group, CraftingBookCategory category, Item item, int count, Map<Character, Ingredient> ingredients, String... pattern) {
		Recipe.CommonInfo commonInfo = new Recipe.CommonInfo(true);
		CraftingRecipe.CraftingBookInfo bookInfo = new CraftingRecipe.CraftingBookInfo(category, group);
		ItemStackTemplate output = new ItemStackTemplate(item, count);

		return new ShapedRecipe(commonInfo, bookInfo, ShapedRecipePattern.of(ingredients, pattern), output);
	}

	public static ShapelessRecipe shapeless(String group, CraftingBookCategory category, Item item, int count, List<Ingredient> ingredients) {
		Recipe.CommonInfo commonInfo = new Recipe.CommonInfo(true);
		CraftingRecipe.CraftingBookInfo bookInfo = new CraftingRecipe.CraftingBookInfo(category, group);
		ItemStackTemplate output = new ItemStackTemplate(item, count);

		return new ShapelessRecipe(commonInfo, bookInfo, output, ingredients);
	}

}
