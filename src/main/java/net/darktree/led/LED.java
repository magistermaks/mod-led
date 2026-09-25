package net.darktree.led;

import net.darktree.led.block.DiodeButtonLampBlock;
import net.darktree.led.block.DiodeLampBlock;
import net.darktree.led.block.DiodeSwitchLampBlock;
import net.darktree.led.block.DirectionalDiodeLampBlock;
import net.darktree.led.item.ShadeItem;
import net.darktree.led.util.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.BlockTransformerHelper;
import net.minecraft.core.component.BlockTransformer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedStateProvider;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiFunction;

public class LED implements ModInitializer {

    public static final TagKey<Block> LAMPS = TagKey.create(Registries.BLOCK, RegistryHelper.id("lamps"));
    public static final ResourceKey<LootTable> SCRAPE_SHADE = ResourceKey.create(Registries.LOOT_TABLE, RegistryHelper.id("scrape_shade"));

    public static final String ID = "led";
    public static final Logger LOG = LoggerFactory.getLogger("LED");
    public static final CraftingBookCategory CATEGORY = CraftingBookCategory.MISC;

    public static final Item LED = RegistryHelper.registerItem("led", Item::new);
    public static final Item BULB = RegistryHelper.registerItem("bulb", Item::new);
    public static final Item SHADE = RegistryHelper.registerItem("shade", ShadeItem::new);

    private static void registerFixture(LedFixture fixture, BiFunction<BlockBehaviour.Properties, LedType, Block> supplier) {
        for (LedVariant variant : LedVariant.values()) {
            RegistryHelper.registerFixture(fixture, variant, supplier);
        }
    }

    private static void registerBlockTransformer(RuleBasedStateProvider rules, BlockTransformer.DropStrategy strategy) {
        BlockTransformerHelper.registerAxe(BlockTransformer.BlockTransformData.builder(rules)
                .dropStrategy(strategy)
                .updateFromNeighbors(true)
                .particle(BlockTransformer.TransformParticle.SCRAPE)
                .transformType(BlockTransformer.TransformType.SINGLE_BLOCK)
                .loot(SCRAPE_SHADE)
                .sound(SoundEvents.AXE_SCRAPE)
                .build());
    }

    private static void createBlockTransformer() {
        var faceRules = RuleBasedStateProvider.builder();
        var centerRules = RuleBasedStateProvider.builder();

        for (LedDelegate delegate : RegistryHelper.getClientDelegates()) {
            delegate.appendBlockTransformerRule(faceRules, centerRules);
        }

        registerBlockTransformer(faceRules.build(), BlockTransformer.DropStrategy.CLICKED_FACE);
        registerBlockTransformer(centerRules.build(), BlockTransformer.DropStrategy.FROM_MIDDLE);
    }

    @Override
    public void onInitialize() {
        RegistryHelper.addToGroup(LED);
        RegistryHelper.addToGroup(SHADE);

        VoxelShape[] smallDiodeStance = VoxelHelper.getFacings(4, 0, 4, 12, 1, 12);
        VoxelShape[] largeDiodeStance = VoxelHelper.getFacings(3, 0, 3, 13, 1, 13);
        VoxelShape[] flatDiodeStance = VoxelHelper.getFacings(0, 0, 0, 16, 1, 16);

        // buttons and switches
        RegistryHelper.registerFixture(LedFixture.BUTTON, LedVariant.NORMAL, DiodeButtonLampBlock::new, LedVariant.getButtonRecipeFactory());
        RegistryHelper.registerFixture(LedFixture.SWITCH, LedVariant.NORMAL, DiodeSwitchLampBlock::new, LedVariant.getSwitchRecipeFactory());

        var smallShapes = VoxelHelper.combineFacings(VoxelHelper.getFacings( 5, 0, 5, 11, 3, 11 ), smallDiodeStance);
        var mediumShapes = VoxelHelper.combineFacings(VoxelHelper.getFacings( 5, 0, 5, 11, 8, 11 ), smallDiodeStance);
        var largeShapes = VoxelHelper.combineFacings(VoxelHelper.getFacings( 4, 0, 4, 12, 6, 12 ), largeDiodeStance);
        var flatShapes = VoxelHelper.combineFacings(VoxelHelper.getFacings( 1, 1, 1, 15, 3, 15 ), flatDiodeStance);

        // light fixtures
        registerFixture(LedFixture.FULL, DiodeLampBlock::new);
        registerFixture(LedFixture.SMALL, (cfg, type) -> new DirectionalDiodeLampBlock(cfg, type, smallShapes));
        registerFixture(LedFixture.MEDIUM, (cfg, type) -> new DirectionalDiodeLampBlock(cfg, type, mediumShapes));
        registerFixture(LedFixture.LARGE, (cfg, type) -> new DirectionalDiodeLampBlock(cfg, type, largeShapes));
        registerFixture(LedFixture.FLAT, (cfg, type) -> new DirectionalDiodeLampBlock(cfg, type, flatShapes));

        createBlockTransformer();
        RegistryHelper.appendItemsToGroup();
    }

}
