package net.darktree.led.util;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.function.Consumer;

public interface TooltippedBlock {

	/**
	 * If a block implements this interface this tooltip returned by this method
	 * will be appended to its block items.
	 */
	void appendTooltip(ItemStack stack, Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag options);

}
