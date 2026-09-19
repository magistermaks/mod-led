package net.darktree.led.block;

import net.darktree.led.util.DiodeVariant;
import net.darktree.led.util.LootHelper;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ButtonBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;

import java.util.List;

public class DiodeButtonLampBlock extends ButtonBlock {

    public DiodeButtonLampBlock() {
        super(BlockSetType.STONE, 20, DiodeVariant.NORMAL.settings()
                .luminance((state) -> state.get(POWERED) ? 3 : 0)
                .emissiveLighting((state, world, pos) -> state.get(POWERED))
        );
    }

    @Override
    protected List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        return LootHelper.dropSelf(this, super.getDroppedStacks(state, builder));
    }

}
