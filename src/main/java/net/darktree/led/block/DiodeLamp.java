package net.darktree.led.block;

import net.darktree.led.util.LootHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.List;

public class DiodeLamp extends Block implements LootHelper.DropsItself {

    public static final BooleanProperty LIT = BooleanProperty.of("lit");
    private final VoxelShape shape;
    private final boolean shaded;

    public static boolean emissive( BlockState state, BlockView world, BlockPos pos ) {
        return state.get(LIT);
    }

    public DiodeLamp( Settings settings, int light, VoxelShape shape, boolean shaded ) {
        super( settings.luminance( (state) -> state.get(LIT) ? light : 0 ) );
        this.shape = shape;
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
        return shape;
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        boolean power = world.isReceivingRedstonePower(pos);

        if( power != state.get(LIT) ) {
            world.setBlockState( pos, state.with(LIT, power) );
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess worldAccess, BlockPos pos, BlockPos posFrom) {
        if( worldAccess instanceof World ) {
            World world = (World) worldAccess;

            return state.with(LIT, world.isReceivingRedstonePower(pos));
        }

        return state;
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        return LootHelper.dispatch( state, builder, this );
    }

}
