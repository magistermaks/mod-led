package net.darktree.led;

import net.darktree.interference.MessageInjector;
import net.darktree.led.block.DiodeButtonLampBlock;
import net.darktree.led.block.DiodeLampBlock;
import net.darktree.led.block.DiodeSwitchLampBlock;
import net.darktree.led.block.DirectionalDiodeLampBlock;
import net.darktree.led.util.DiodeVariant;
import net.darktree.led.util.RegistryHelper;
import net.darktree.led.util.Util;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.util.shape.VoxelShape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LED implements ModInitializer, ClientModInitializer, DedicatedServerModInitializer {

    public static final Logger LOG = LoggerFactory.getLogger("LED");

    public static final Item LED = new Item(RegistryHelper.ITEM_SETTINGS);
    public static final Item BULB = new Item(RegistryHelper.ITEM_SETTINGS);
    public static final Item SHADE = new Item(RegistryHelper.ITEM_SETTINGS);

    @Override
    public void onInitialize() {
        RegistryHelper.registerItem("led", LED, true);
        RegistryHelper.registerItem("bulb", BULB, false);
        RegistryHelper.registerItem("shade", SHADE, true);

        VoxelShape[] smallDiodeStance = Util.getVariants(4, 0, 4, 12, 1, 12);
        VoxelShape[] largeDiodeStance = Util.getVariants(3, 0, 3, 13, 1, 13);
        VoxelShape[] flatDiodeStance = Util.getVariants(0, 0, 0, 16, 1, 16);

        // buttons and switches
        RegistryHelper.registerForColors("button", DiodeButtonLampBlock::new, DiodeVariant.getButtonRecipe(true));
        RegistryHelper.registerForColors("switch", DiodeSwitchLampBlock::new, DiodeVariant.getButtonRecipe(false));

        for (DiodeVariant variant : DiodeVariant.values()) {

            // full indicator lamp
            RegistryHelper.registerForColors(variant.getName("clear_full"), () -> new DiodeLampBlock(
                    variant),
                    variant.getRecipe("BBB,BAB,BCB", "clear_full")
            );

            // small indicator lamp
            RegistryHelper.registerForColors(variant.getName("small_fixture"), () -> new DirectionalDiodeLampBlock(
                    variant, Util.combineVariants(Util.getVariants( 5, 0, 5, 11, 3, 11 ), smallDiodeStance) ),
                    variant.getRecipe(" B , A ,CCC", "small_fixture")
            );

            // medium indicator lamp
            RegistryHelper.registerForColors(variant.getName("medium_fixture"), () -> new DirectionalDiodeLampBlock(
                    variant, Util.combineVariants(Util.getVariants( 5, 0, 5, 11, 8, 11 ), smallDiodeStance) ),
                    variant.getRecipe(" B ,BAB,CCC", "medium_fixture")
            );

            // large indicator lamp
            RegistryHelper.registerForColors(variant.getName("large_fixture"), () -> new DirectionalDiodeLampBlock(
                    variant, Util.combineVariants(Util.getVariants( 4, 0, 4, 12, 6, 12 ), largeDiodeStance) ),
                    variant.getRecipe("BBB,BAB,CCC", "large_fixture")
            );

            // flat indicator lamp
            RegistryHelper.registerForColors(variant.getName("flat_fixture"), () -> new DirectionalDiodeLampBlock(
                    variant, Util.combineVariants(Util.getVariants( 1, 1, 1, 15, 3, 15 ), flatDiodeStance) ),
                    variant.getRecipe("BBB, A ,CCC", "flat_fixture")
            );

        }

        // add everything to one group
        RegistryHelper.appendItemsToGroup();

        MessageInjector.inject("Q2hlY2sgb3V0IE11cmRlciBEcm9uZXMh");
        MessageInjector.inject("Q2hlY2sgb3V0IFJlZCBCaXRzIQ==");
        MessageInjector.inject("VGVuIGJpbGxpb24gcGVyY2VudCE=");
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
