package net.darktree.led.client.datagen;

import net.darktree.led.LED;
import net.darktree.led.util.ClientDelegate;
import net.darktree.led.util.RegistryHelper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LedRecipeProvider extends FabricRecipeProvider {

	public LedRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput exporter) {
		return new Generator(registries, exporter);
	}

	@Override
	public String getName() {
		return "LedRecipeProvider";
	}

	public static class Generator extends RecipeProvider {

		protected Generator(HolderLookup.Provider registries, RecipeOutput exporter) {
			super(registries, exporter);
		}

		private void generateFixtureRecipes(List<ClientDelegate.RecipeInfo> recipes) {

			Advancement.Builder advancement = output.advancement()
					.addCriterion("has_led", has(LED.LED))
					.requirements(AdvancementRequirements.Strategy.OR);

			AdvancementRewards.Builder rewards = new AdvancementRewards.Builder();
			Iterator<ClientDelegate.RecipeInfo> it = recipes.iterator();

			while (it.hasNext()) {
				ClientDelegate.RecipeInfo info = it.next();

				rewards.addRecipe(info.key());
				AdvancementHolder entry = null;

				// force the combined advancement down the games throat with the last recipe
				if (!it.hasNext()) {
					advancement.rewards(rewards);
					entry = advancement.build(RegistryHelper.id("recipes/misc/lamps"));
				}

				output.accept(info.key(), info.recipe(), entry);
			}

		}

		@Override
		public void buildRecipes() {

			List<ClientDelegate.RecipeInfo> recipes = new ArrayList<>();
			RegistryHelper.getClientDelegates().forEach(delegate -> delegate.addRecipes(recipes));
			generateFixtureRecipes(recipes);

			shaped(RecipeCategory.MISC, LED.LED, 2)
					.pattern(" 0 ")
					.pattern("323")
					.pattern("1 1")
					.define('0', Items.GLOWSTONE_DUST)
					.define('1', Items.IRON_NUGGET)
					.define('2', Items.QUARTZ)
					.define('3', Items.REDSTONE)
					.unlockedBy("has_quartz", has(Items.QUARTZ))
					.save(output);

			shapeless(RecipeCategory.MISC, LED.SHADE, 4)
					.requires(Items.SOUL_SAND)
					.requires(Items.INK_SAC)
					.unlockedBy(getHasName(Items.SOUL_SAND), this.has(Items.SOUL_SAND))
					.save(output);

		}

	}

}
