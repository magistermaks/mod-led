package net.darktree.led;

import net.darktree.led.block.DiodeButtonLampBlock;
import net.darktree.led.block.DiodeLampBlock;
import net.darktree.led.block.DiodeSwitchLampBlock;
import net.darktree.led.block.DirectionalDiodeLampBlock;
import net.darktree.led.util.DiodeVariant;
import net.darktree.led.util.RegistryHelper;
import net.darktree.led.util.Util;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.shape.VoxelShape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LED implements ModInitializer {

    public static final String ID = "led";
    public static final Logger LOG = LoggerFactory.getLogger("LED");
    public static final CraftingRecipeCategory CATEGORY = CraftingRecipeCategory.MISC;

    public static final Item LED = RegistryHelper.registerSimpleItem("led");
    public static final Item BULB = RegistryHelper.registerSimpleItem("bulb");
    public static final Item SHADE = RegistryHelper.registerSimpleItem("shade");

    @Override
    public void onInitialize() {
        RegistryHelper.addToGroup(LED);
        RegistryHelper.addToGroup(SHADE);

        VoxelShape[] smallDiodeStance = Util.getVariants(4, 0, 4, 12, 1, 12);
        VoxelShape[] largeDiodeStance = Util.getVariants(3, 0, 3, 13, 1, 13);
        VoxelShape[] flatDiodeStance = Util.getVariants(0, 0, 0, 16, 1, 16);

        // buttons and switches
        RegistryHelper.registerForColors("button", DiodeButtonLampBlock::new, DiodeVariant.getButtonRecipeFactory());
        RegistryHelper.registerForColors("switch", DiodeSwitchLampBlock::new, DiodeVariant.getSwitchRecipeFactory());

        for (DiodeVariant variant : DiodeVariant.values()) {

            // full indicator lamp
            RegistryHelper.registerForColors(variant.getName("clear_full"), cfg -> new DiodeLampBlock(
                    cfg, variant),
                    variant.getRecipeFactory("BBB,BAB,BBB", "clear_full")
            );

            // small indicator lamp
            RegistryHelper.registerForColors(variant.getName("small_fixture"), cfg -> new DirectionalDiodeLampBlock(
                    cfg, variant, Util.combineVariants(Util.getVariants( 5, 0, 5, 11, 3, 11 ), smallDiodeStance)),
                    variant.getRecipeFactory(" B , A ,CCC", "small_fixture")
            );

            // medium indicator lamp
            RegistryHelper.registerForColors(variant.getName("medium_fixture"), cfg -> new DirectionalDiodeLampBlock(
                    cfg, variant, Util.combineVariants(Util.getVariants( 5, 0, 5, 11, 8, 11 ), smallDiodeStance)),
                    variant.getRecipeFactory(" B ,BAB,CCC", "medium_fixture")
            );

            // large indicator lamp
            RegistryHelper.registerForColors(variant.getName("large_fixture"), cfg -> new DirectionalDiodeLampBlock(
                    cfg, variant, Util.combineVariants(Util.getVariants( 4, 0, 4, 12, 6, 12 ), largeDiodeStance)),
                    variant.getRecipeFactory("BBB,BAB,CCC", "large_fixture")
            );

            // flat indicator lamp
            RegistryHelper.registerForColors(variant.getName("flat_fixture"), cfg -> new DirectionalDiodeLampBlock(
                    cfg, variant, Util.combineVariants(Util.getVariants( 1, 1, 1, 15, 3, 15 ), flatDiodeStance)),
                    variant.getRecipeFactory("BBB, A ,CCC", "flat_fixture")
            );

        }

        RegistryHelper.appendItemsToGroup();
    }

}
