package net.darktree.led.client.datagen;

import com.mojang.serialization.JsonOps;
import net.darktree.led.util.ClientDelegate;
import net.darktree.led.util.RegistryHelper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class LedRecipeProvider extends FabricRecipeProvider {

	public LedRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
		return new Generator(registries, exporter);
	}

	@Override
	public String getName() {
		return "LedRecipeProvider";
	}

	public static class Generator extends RecipeGenerator {

		protected Generator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
			super(registries, exporter);
		}

		@Override
		public void generate() {

			for (ClientDelegate delegate : RegistryHelper.getClientDelegates()) {
				RegistryKey<Recipe<?>> key = RegistryKey.of(RegistryKeys.RECIPE, delegate.id);

				try {
					Recipe<?> recipe = Recipe.CODEC.parse(registries.getOps(JsonOps.INSTANCE), delegate.recipe).getOrThrow();
					List<Item> preconditions = List.of();

					Advancement.Builder builder = exporter.getAdvancementBuilder()
							.criterion("has_the_recipe", RecipeUnlockedCriterion.create(key))
							.rewards(AdvancementRewards.Builder.recipe(key))
							.criteriaMerger(AdvancementRequirements.CriterionMerger.OR);

					for (Item item : preconditions) {
						builder.criterion(hasItem(item), conditionsFromItem(item));
					}

					String category = Registries.RECIPE_BOOK_CATEGORY.getEntry(recipe.getRecipeBookCategory()).getKey().orElseThrow().getValue().getPath();
					exporter.accept(key, recipe, builder.build(key.getValue().withPrefixedPath("recipes/" + category + "/")));
				} catch (Exception e) {
					throw e;
				}
			}



		}

	}

}
