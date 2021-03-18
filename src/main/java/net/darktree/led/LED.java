package net.darktree.led;

import net.darktree.led.block.DiodeLampBlock;
import net.darktree.led.block.DirectionalDiodeLampBlock;
import net.darktree.led.util.DiodeVariant;
import net.darktree.led.util.RegistryHelper;
import net.darktree.led.util.Util;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.util.shape.VoxelShape;
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

        VoxelShape[] diode_small_stance = Util.getRotatedVariantArray(4, 0, 4, 12, 1, 12);
        VoxelShape[] diode_large_stance = Util.getRotatedVariantArray(3, 0, 3, 13, 1, 13);

        for( DiodeVariant variant : DiodeVariant.values() ) {

            // full block lamps
            RegistryHelper.registerForColors( variant.getName("clear_full"), () -> new DiodeLampBlock(
                    RegistryHelper.settings(), variant.getLightLevel(), variant.isShaded()
            ), variant.getClearFullRecipe());

            // small indicator lamp
            RegistryHelper.registerForColors( variant.getName("small_fixture"), () -> new DirectionalDiodeLampBlock(
                    RegistryHelper.settings(), variant.getLightLevel(), variant.isShaded(),
                    Util.combineVariantArray( Util.getRotatedVariantArray( 5, 0, 5, 11, 3, 11 ), diode_small_stance )
            ), variant.getClearSmallRecipe());

            // medium indicator lamp
            RegistryHelper.registerForColors( variant.getName("medium_fixture"), () -> new DirectionalDiodeLampBlock(
                    RegistryHelper.settings(), variant.getLightLevel(), variant.isShaded(),
                    Util.combineVariantArray( Util.getRotatedVariantArray( 5, 0, 5, 11, 8, 11 ), diode_small_stance )
            ), variant.getClearMediumRecipe());

            // large indicator lamp
            RegistryHelper.registerForColors( variant.getName("large_fixture"), () -> new DirectionalDiodeLampBlock(
                    RegistryHelper.settings(), variant.getLightLevel(), variant.isShaded(),
                    Util.combineVariantArray( Util.getRotatedVariantArray( 4, 0, 4, 12, 6, 12 ), diode_large_stance )
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
