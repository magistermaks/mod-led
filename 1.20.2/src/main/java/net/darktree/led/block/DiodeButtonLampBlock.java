package net.darktree.led.block;

import net.darktree.interference.api.DropsItself;
import net.darktree.led.util.DiodeVariant;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.ButtonBlock;

public class DiodeButtonLampBlock extends ButtonBlock implements DropsItself {

    public DiodeButtonLampBlock() {
        super(DiodeVariant.NORMAL.settings()
                .luminance((state) -> state.get(POWERED) ? 3 : 0)
                .emissiveLighting((state, world, pos) -> state.get(POWERED)),
                BlockSetType.STONE, 20, false
        );
    }

}
