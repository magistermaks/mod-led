package net.darktree.led.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public interface TooltippedBlock {

	/**
	 * If a block implements this interface this tooltip returned by this method
	 * will be appended to its block items.
	 */
	void appendTooltip(ItemStack stack, Item.TooltipContext context, Consumer<Text> consumer, TooltipType options);

}
