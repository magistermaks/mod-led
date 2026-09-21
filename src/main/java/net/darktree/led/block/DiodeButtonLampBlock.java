package net.darktree.led.block;

import net.darktree.led.util.LedVariant;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.ButtonBlock;

public class DiodeButtonLampBlock extends ButtonBlock {

    public DiodeButtonLampBlock(AbstractBlock.Settings settings) {
        super(BlockSetType.STONE, 20, LedVariant.NORMAL.applySettings(settings));
    }

}
