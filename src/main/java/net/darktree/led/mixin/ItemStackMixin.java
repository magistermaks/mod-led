package net.darktree.led.mixin;

import net.darktree.led.util.TooltippedBlock;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
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

	@Inject(method = "addDetailsToTooltip", at = @At("HEAD"))
	public void appendTooltip(Item.TooltipContext context, TooltipDisplay displayComponent, Player player, TooltipFlag type, Consumer<Component> consumer, CallbackInfo ci) {
		if (getItem() instanceof BlockItem blockItem) {
			if (blockItem.getBlock() instanceof TooltippedBlock block) {
				block.appendTooltip((ItemStack) (Object) this, context, consumer, type);
			}
		}
	}

}
