package net.darktree.led.block;

import net.darktree.led.util.LootHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneLampBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.List;
import java.util.Random;

public class DiodeLamp extends Block implements LootHelper.DropsItself {

    public static final BooleanProperty LIT = BooleanProperty.of("lit");
    private final boolean shaded;

    public static boolean emissive( BlockState state, BlockView world, BlockPos pos ) {
        return state.get(LIT);
    }

    public DiodeLamp( Settings settings, int light, boolean shaded ) {
        super( settings.luminance( (state) -> state.get(LIT) ? light : 0 ) );
        this.shaded = shaded;
        setDefaultState( getDefaultState().with(LIT, false) );
    }

    @Override
    public void appendTooltip(ItemStack stack, BlockView world, List<Text> tooltip, TooltipContext options) {
        if( shaded ) {
            tooltip.add(new TranslatableText("tooltip.led.shaded").formatted(Formatting.GRAY));
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.fullCube();
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        boolean power = world.isReceivingRedstonePower(pos);

        if( power ) {
            world.setBlockState( pos, state.with(LIT, true) );
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (!world.isClient) {
            boolean lit = state.get(LIT);

            if( lit != hasPower(state, world, pos) ) {
                if( lit ) {
                    world.getBlockTickScheduler().schedule(pos, this, 4);
                }else{
                    world.setBlockState(pos, state.cycle(LIT), 2);
                }
            }
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(LIT) && !hasPower(state, world, pos)) {
            world.setBlockState(pos, state.cycle(LIT), 2);
        }
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        return LootHelper.dispatch( state, builder, this );
    }

    protected boolean hasPower(BlockState state, World world, BlockPos pos) {
        return world.isReceivingRedstonePower(pos);
    }

}
