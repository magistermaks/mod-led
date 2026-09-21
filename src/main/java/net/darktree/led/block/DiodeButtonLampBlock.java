package net.darktree.led.block;

import net.darktree.led.util.LedVariant;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class DiodeButtonLampBlock extends ButtonBlock {

    public DiodeButtonLampBlock(BlockBehaviour.Properties settings) {
        super(BlockSetType.STONE, 20, LedVariant.NORMAL.applySettings(settings));
    }

}
