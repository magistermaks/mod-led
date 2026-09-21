package net.darktree.led.util;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.DyeColor;

import java.util.IdentityHashMap;
import java.util.Map;

public class LedBlockSet {

	private final Map<LedFixture, Map<LedVariant, Map<DyeColor, Block>>> blocks = new IdentityHashMap<>();

	private Map<LedVariant, Map<DyeColor, Block>> getFixture(LedFixture fixture) {
		return blocks.computeIfAbsent(fixture, key -> new IdentityHashMap<>());
	}

	private Map<DyeColor, Block> getFixtureVariant(LedFixture fixture, LedVariant variant) {
		return getFixture(fixture).computeIfAbsent(variant, key -> new IdentityHashMap<>());
	}

	public Block getBlock(LedFixture fixture, LedVariant variant, DyeColor color) {
		return getFixtureVariant(fixture, variant).getOrDefault(color, Blocks.AIR);
	}

	public void setBlock(LedFixture fixture, LedVariant variant, DyeColor color, Block block) {
		getFixtureVariant(fixture, variant).put(color, block);
	}

}
