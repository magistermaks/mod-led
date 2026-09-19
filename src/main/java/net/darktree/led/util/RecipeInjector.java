package net.darktree.led.util;

import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class RecipeInjector {

	private static final List<Pair<Identifier, JsonElement>> RECIPES = new ArrayList<>();

	public static void register(Identifier id, JsonElement json) {
		RECIPES.add(Pair.of(id, json));
	}

	public static void consume(BiConsumer<Identifier, JsonElement> consumer) {
		RECIPES.forEach(pair -> consumer.accept(pair.getFirst(), pair.getSecond()));
	}

}