package net.darktree.led;

import net.darktree.led.block.DiodeLampBlock;
import net.darktree.led.block.LargeDiodeLampBlock;
import net.darktree.led.block.SmallDiodeLampBlock;
import net.darktree.led.util.DiodeVariant;
import net.darktree.led.util.RegistryHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LED implements ModInitializer, ClientModInitializer, DedicatedServerModInitializer {

    public static final Logger LOG = LogManager.getLogger("LED");

    public static final Item LED = new Item( RegistryHelper.ITEM_SETTINGS );
    public static final Item BULB = new Item( RegistryHelper.ITEM_SETTINGS );
    public static final Item TUBE = new Item( RegistryHelper.ITEM_SETTINGS );
    public static final Item SHADE = new Item( RegistryHelper.ITEM_SETTINGS );

    @Override
    public void onInitialize() {
        RegistryHelper.registerItem("led", LED);
        RegistryHelper.registerItem("bulb", BULB);
        RegistryHelper.registerItem("tube", TUBE);
        RegistryHelper.registerItem("shade", SHADE);

        for( DiodeVariant variant : DiodeVariant.values() ) {

            // full block lamps
            RegistryHelper.registerForColors( variant.getName("clear_full"), () -> new DiodeLampBlock(
                    RegistryHelper.settings().emissiveLighting( DiodeLampBlock::emissive ),
                    variant.getLightLevel(), variant == DiodeVariant.SHADED
            ), variant.getClearFullRecipe());

            // small indicator lamp
            RegistryHelper.registerForColors( variant.getName("small_fixture"), () -> new SmallDiodeLampBlock(
                    RegistryHelper.settings().emissiveLighting( DiodeLampBlock::emissive ),
                    variant.getLightLevel(), variant == DiodeVariant.SHADED
            ), variant.getClearSmallRecipe());

            // medium indicator lamp
            RegistryHelper.registerForColors( variant.getName("medium_fixture"), () -> new SmallDiodeLampBlock(
                    RegistryHelper.settings().emissiveLighting( DiodeLampBlock::emissive ),
                    variant.getLightLevel(), variant == DiodeVariant.SHADED
            ), variant.getClearMediumRecipe());

            // large indicator lamp
            RegistryHelper.registerForColors( variant.getName("large_fixture"), () -> new LargeDiodeLampBlock(
                    RegistryHelper.settings().emissiveLighting( DiodeLampBlock::emissive ),
                    variant.getLightLevel(), variant == DiodeVariant.SHADED
            ), variant.getClearLargeRecipe());

        }
    }

    @Override
    public void onInitializeClient() {
        RegistryHelper.applyDelegates();
    }

    @Override
    public void onInitializeServer() {
        RegistryHelper.discardDelegates();
    }

}
