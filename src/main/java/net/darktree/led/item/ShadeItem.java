package net.darktree.led.item;

import net.darktree.led.block.DiodeLampBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;

public class ShadeItem extends Item {

	public ShadeItem(Properties properties) {
		super(properties);
	}

	private void spawnBlockParticle(Level level, BlockPos pos, SimpleParticleType type) {
		RandomSource random = level.getRandom();

		for (Direction direction : Direction.values()) {
			BlockPos relative = pos.relative(direction);
			if (!level.getBlockState(relative).isSolidRender()) {
				Direction.Axis axis = direction.getAxis();
				double dx = axis == Direction.Axis.X ? 0.5 + 0.5625 * direction.getStepX() : random.nextFloat();
				double dy = axis == Direction.Axis.Y ? 0.5 + 0.5625 * direction.getStepY() : random.nextFloat();
				double dz = axis == Direction.Axis.Z ? 0.5 + 0.5625 * direction.getStepZ() : random.nextFloat();
				level.addParticle(type, pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz, 0.0, 0.0, 0.0);
			}
		}
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {

		final Level level = context.getLevel();
		final BlockPos pos = context.getClickedPos();
		final BlockState state = level.getBlockState(pos);

		if (state.getBlock() instanceof DiodeLampBlock diode && !diode.type.variant.isShaded()) {
			BlockState transformed = diode.applyState(state, diode.type.withShaded(true).getBlock().defaultBlockState());
			level.setBlock(pos, transformed, Block.UPDATE_ALL);

			spawnBlockParticle(level, pos, ParticleTypes.WAX_ON);

			float pitch = Mth.randomBetween(level.getRandom(), 1.1F, 2.0F);
			level.playSound(null, pos, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1.0f, pitch);

			context.getItemInHand().consume(1, context.getPlayer());
			return InteractionResult.SUCCESS;
		}

		return super.useOn(context);
	}

}
