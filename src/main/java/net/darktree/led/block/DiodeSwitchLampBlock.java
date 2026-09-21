package net.darktree.led.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class DiodeSwitchLampBlock extends DiodeButtonLampBlock {

    public DiodeSwitchLampBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        // do nothing
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        boolean power = state.getValue(POWERED);
        world.setBlockAndUpdate( pos, state.cycle(POWERED) );
        playSound(player, world, pos, !power);
        world.updateNeighborsAt(pos, this, null);
        world.updateNeighborsAt(pos.relative(getConnectedDirection(state).getOpposite()), this, null);

        return InteractionResult.SUCCESS;
    }

}
