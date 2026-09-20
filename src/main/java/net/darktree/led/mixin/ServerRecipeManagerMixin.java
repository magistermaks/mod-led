package net.darktree.led.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.JsonOps;
import net.darktree.led.util.RecipeInjector;
import net.minecraft.recipe.PreparedRecipes;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.SortedMap;

@Mixin(ServerRecipeManager.class)
abstract public class ServerRecipeManagerMixin {

	@Shadow
	@Final
	private RegistryWrapper.WrapperLookup registries;

	@Inject(
			method = "prepare(Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)Lnet/minecraft/recipe/PreparedRecipes;",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/resource/JsonDataLoader;load(Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/resource/ResourceFinder;Lcom/mojang/serialization/DynamicOps;Lcom/mojang/serialization/Codec;Ljava/util/Map;)V",
					shift = At.Shift.AFTER
			)
	)
	public void interceptApply(ResourceManager resourceManager, Profiler profiler, CallbackInfoReturnable<PreparedRecipes> cir, @Local SortedMap<Identifier, Recipe<?>> recipes) {
		RecipeInjector.consume((id, element) -> {
			recipes.putIfAbsent(id, Recipe.CODEC.parse(this.registries.getOps(JsonOps.INSTANCE), element).getOrThrow());
		});
	}

}