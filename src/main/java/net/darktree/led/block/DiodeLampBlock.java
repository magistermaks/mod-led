package net.darktree.led.block;

import net.darktree.led.util.LedVariant;
import net.darktree.led.util.TooltippedBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class DiodeLampBlock extends Block implements TooltippedBlock {

    public static final BooleanProperty LIT = BooleanProperty.create("lit");
    private final LedVariant variant;

    public DiodeLampBlock(BlockBehaviour.Properties settings, LedVariant variant) {
        super(variant.applySettings(settings).lightLevel(state -> state.getValue(LIT) ? variant.getLightLevel() : 0));

        this.variant = variant;
        registerDefaultState(defaultBlockState().setValue(LIT, false));
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag options) {
        final String text = variant.getTooltip();

        if (text != null) {
            consumer.accept(Component.translatable(text).withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.block();
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
        boolean power = hasPower(world, pos);

        if (power) {
            world.setBlockAndUpdate(pos, state.setValue(LIT, true));
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    protected void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, @Nullable Orientation wireOrientation, boolean notify) {
        if (!world.isClientSide()) {
            boolean lit = state.getValue(LIT);

            if (lit != hasPower(world, pos)) {
                if (lit) {
                    world.scheduleTick(pos, this, 4);
                } else {
                    world.setBlock(pos, state.cycle(LIT), 2);
                }
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT) && !hasPower(world, pos)) {
            world.setBlock(pos, state.cycle(LIT), 2);
        }
    }

    private boolean hasPower(Level world, BlockPos pos) {
        return world.hasNeighborSignal(pos);
    }

}
