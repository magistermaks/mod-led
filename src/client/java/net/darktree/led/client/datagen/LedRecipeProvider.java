package net.darktree.led.client.datagen;

import net.darktree.led.LED;
import net.darktree.led.util.LedDelegate;
import net.darktree.led.util.RegistryHelper;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LedRecipeProvider extends FabricRecipeProvider {

	public LedRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
		return new Generator(recipes, advancements);
	}

	@Override
	public String getName() {
		return "LedRecipeProvider";
	}

	public static class Generator extends RecipeProvider {

		protected Generator(BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
			super(recipes, advancements);
		}

		private void generateFixtureRecipes(List<LedDelegate.RecipeInfo> recipes) {

			Advancement.Builder advancement = output.advancement()
					.addCriterion("has_led", has(LED.LED))
					.requirements(AdvancementRequirements.Strategy.OR);

			AdvancementRewards.Builder rewards = new AdvancementRewards.Builder();

			for (LedDelegate.RecipeInfo info : recipes) {
				rewards.addRecipe(info.key());
				output.accept(info.key(), info.recipe(), null);
			}

			advancement.rewards(rewards);
			advancement.build(RegistryHelper.id("recipes/misc/lamps")).register(advancementOutput);

		}

		@Override
		public void buildRecipes() {

			List<LedDelegate.RecipeInfo> recipes = new ArrayList<>();
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
