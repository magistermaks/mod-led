package net.darktree.led;

import net.darktree.led.block.DiodeLamp;
import net.darktree.led.util.DiodeVariant;
import net.darktree.led.util.RegistryHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.util.shape.VoxelShapes;
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
            RegistryHelper.registerForColors( variant.getName("clear_full"), () -> new DiodeLamp(
                    RegistryHelper.settings().emissiveLighting( DiodeLamp::emissive ),
                    variant.getLightLevel(), VoxelShapes.fullCube(), variant == DiodeVariant.SHADED
            ), variant.getClearFullRecipe());
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
