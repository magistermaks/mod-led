package net.darktree.led.util;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.*;

public class LootHelper {

	public static List<ItemStack> dropSelf(Block block, List<ItemStack> stacks) {
		return stacks.isEmpty() ? Collections.singletonList(new ItemStack(block.asItem())) : stacks;
	}

}