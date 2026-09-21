package net.darktree.led;

import net.darktree.led.block.DiodeButtonLampBlock;
import net.darktree.led.block.DiodeLampBlock;
import net.darktree.led.block.DiodeSwitchLampBlock;
import net.darktree.led.block.DirectionalDiodeLampBlock;
import net.darktree.led.util.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiFunction;

public class LED implements ModInitializer {

    public static final String ID = "led";
    public static final Logger LOG = LoggerFactory.getLogger("LED");
    public static final CraftingBookCategory CATEGORY = CraftingBookCategory.MISC;

    public static final Item LED = RegistryHelper.registerSimpleItem("led");
    public static final Item BULB = RegistryHelper.registerSimpleItem("bulb");
    public static final Item SHADE = RegistryHelper.registerSimpleItem("shade");

    private static void registerFixture(LedFixture fixture, BiFunction<BlockBehaviour.Properties, LedVariant, Block> supplier) {
        for (LedVariant variant : LedVariant.values()) {
            RegistryHelper.registerFixture(fixture, variant, cfg -> supplier.apply(cfg, variant));
        }
    }

    @Override
    public void onInitialize() {
        RegistryHelper.addToGroup(LED);
        RegistryHelper.addToGroup(SHADE);

        VoxelShape[] smallDiodeStance = Util.getFacings(4, 0, 4, 12, 1, 12);
        VoxelShape[] largeDiodeStance = Util.getFacings(3, 0, 3, 13, 1, 13);
        VoxelShape[] flatDiodeStance = Util.getFacings(0, 0, 0, 16, 1, 16);

        // buttons and switches
        RegistryHelper.registerFixture(LedFixture.BUTTON, LedVariant.NORMAL, DiodeButtonLampBlock::new, LedVariant.getButtonRecipeFactory());
        RegistryHelper.registerFixture(LedFixture.SWITCH, LedVariant.NORMAL, DiodeSwitchLampBlock::new, LedVariant.getSwitchRecipeFactory());

        var smallShapes = Util.combineFacings(Util.getFacings( 5, 0, 5, 11, 3, 11 ), smallDiodeStance);
        var mediumShapes = Util.combineFacings(Util.getFacings( 5, 0, 5, 11, 8, 11 ), smallDiodeStance);
        var largeShapes = Util.combineFacings(Util.getFacings( 4, 0, 4, 12, 6, 12 ), largeDiodeStance);
        var flatShapes = Util.combineFacings(Util.getFacings( 1, 1, 1, 15, 3, 15 ), flatDiodeStance);

        // light fixtures
        registerFixture(LedFixture.FULL, DiodeLampBlock::new);
        registerFixture(LedFixture.SMALL, (cfg, variant) -> new DirectionalDiodeLampBlock(cfg, variant, smallShapes));
        registerFixture(LedFixture.MEDIUM, (cfg, variant) -> new DirectionalDiodeLampBlock(cfg, variant, mediumShapes));
        registerFixture(LedFixture.LARGE, (cfg, variant) -> new DirectionalDiodeLampBlock(cfg, variant, largeShapes));
        registerFixture(LedFixture.FLAT, (cfg, variant) -> new DirectionalDiodeLampBlock(cfg, variant, flatShapes));

        RegistryHelper.appendItemsToGroup();
    }

}
