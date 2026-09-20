package net.darktree.led.block;

import net.darktree.led.util.DiodeVariant;
import net.darktree.led.util.LootHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ButtonBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootWorldContext;

import java.util.List;

public class DiodeButtonLampBlock extends ButtonBlock {

    public DiodeButtonLampBlock(AbstractBlock.Settings settings) {
        super(BlockSetType.STONE, 20, DiodeVariant.NORMAL.applySettings(settings)
                .luminance(state -> 0)
                .emissiveLighting((state, world, pos) -> state.get(POWERED))
        );
    }

    @Override
    protected List<ItemStack> getDroppedStacks(BlockState state, LootWorldContext.Builder builder) {
        return LootHelper.dropSelf(this, super.getDroppedStacks(state, builder));
    }

}
