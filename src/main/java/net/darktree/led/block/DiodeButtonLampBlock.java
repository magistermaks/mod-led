package net.darktree.led.block;

import net.darktree.interference.api.DropsItself;
import net.darktree.led.util.DiodeVariant;
import net.minecraft.block.BlockState;
import net.minecraft.block.StoneButtonBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;

import java.util.List;

public class DiodeButtonLampBlock extends StoneButtonBlock implements DropsItself {

    public DiodeButtonLampBlock() {
        super( DiodeVariant.NORMAL.settings()
                .luminance( (state) -> state.get(POWERED) ? 3 : 0 )
                .emissiveLighting( (state, world, pos) -> state.get(POWERED) )
        );
    }

}
