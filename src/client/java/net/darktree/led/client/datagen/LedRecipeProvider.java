package net.darktree.led.client.datagen;

import net.darktree.led.LED;
import net.darktree.led.util.ClientDelegate;
import net.darktree.led.util.RegistryHelper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Items;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

		private void generateFixtureRecipes(List<ClientDelegate.RecipeInfo> recipes) {

			Advancement.Builder advancement = exporter.getAdvancementBuilder()
					.criterion("has_led", conditionsFromItem(LED.LED))
					.criteriaMerger(AdvancementRequirements.CriterionMerger.OR);

			AdvancementRewards.Builder rewards = new AdvancementRewards.Builder();
			Iterator<ClientDelegate.RecipeInfo> it = recipes.iterator();

			while (it.hasNext()) {
				ClientDelegate.RecipeInfo info = it.next();

				rewards.addRecipe(info.key());
				AdvancementEntry entry = null;

				// force the combined advancement down the games throat with the last recipe
				if (!it.hasNext()) {
					advancement.rewards(rewards);
					entry = advancement.build(RegistryHelper.id("recipes/misc/lamps"));
				}

				exporter.accept(info.key(), info.recipe(), entry);
			}

		}

		@Override
		public void generate() {

			List<ClientDelegate.RecipeInfo> recipes = new ArrayList<>();
			RegistryHelper.getClientDelegates().forEach(delegate -> delegate.addRecipes(recipes));
			generateFixtureRecipes(recipes);

			createShaped(RecipeCategory.MISC, LED.LED, 2)
					.pattern(" 0 ")
					.pattern("323")
					.pattern("1 1")
					.input('0', Items.GLOWSTONE_DUST)
					.input('1', Items.IRON_NUGGET)
					.input('2', Items.QUARTZ)
					.input('3', Items.REDSTONE)
					.criterion("has_quartz", conditionsFromItem(Items.QUARTZ))
					.offerTo(exporter);

			createShapeless(RecipeCategory.MISC, LED.SHADE, 4)
					.input(Items.SOUL_SAND)
					.input(Items.INK_SAC)
					.criterion(hasItem(Items.SOUL_SAND), this.conditionsFromItem(Items.SOUL_SAND))
					.offerTo(exporter);

		}

	}

}
