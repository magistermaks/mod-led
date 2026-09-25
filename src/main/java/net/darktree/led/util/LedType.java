package net.darktree.led.util;

import net.minecraft.util.ARGB;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class LedType {

	public final LedFixture fixture;
	public final LedVariant variant;
	public final DyeColor color;

	public LedType(LedFixture fixture, LedVariant variant, DyeColor color) {
		this.fixture = fixture;
		this.variant = variant;
		this.color = color;
	}

	public LedType withShaded(boolean shaded) {
		return new LedType(fixture, variant.withShaded(shaded), color);
	}

	public Block getBlock() {
		return RegistryHelper.FIXTURES.getBlock(fixture, variant, color);
	}

	public Item getItem() {
		return getBlock().asItem();
	}

	public int getTint() {
		return ARGB.opaque(color.getFireworkColor());
	}

}
