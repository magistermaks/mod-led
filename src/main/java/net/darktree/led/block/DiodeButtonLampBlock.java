package net.darktree.led.block;

import net.darktree.led.util.DiodeVariant;
import net.darktree.led.util.LootHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.StoneButtonBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;

import java.util.List;

public class DiodeButtonLampBlock extends StoneButtonBlock implements LootHelper.DropsItself {

    public DiodeButtonLampBlock(DiodeVariant variant) {
        super( variant.settings()
                .luminance( (state) -> state.get(POWERED) ? 3 : 0 )
                .emissiveLighting( (state, world, pos) -> state.get(POWERED) )
        );
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        return LootHelper.dispatch( state, builder, this );
    }

}
