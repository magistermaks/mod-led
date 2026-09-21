package net.darktree.led.mixin;

import net.darktree.led.util.TooltippedBlock;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

	@Shadow
	public abstract Item getItem();

	@Inject(method = "appendTooltip", at = @At("HEAD"))
	public void appendTooltip(Item.TooltipContext context, TooltipDisplayComponent displayComponent, PlayerEntity player, TooltipType type, Consumer<Text> consumer, CallbackInfo ci) {
		if (getItem() instanceof BlockItem blockItem) {
			if (blockItem.getBlock() instanceof TooltippedBlock block) {
				block.appendTooltip((ItemStack) (Object) this, context, consumer, type);
			}
		}
	}

}
