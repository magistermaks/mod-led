package net.darktree.led.util;

import net.darktree.led.LED;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class RegistryHelper {

    private static final List<ClientDelegate> DELEGATES = new ArrayList<>();

    public static final LedBlockSet FIXTURES = new LedBlockSet();
    public static final ItemGroup GROUP = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup.led.group"))
            .icon(() -> new ItemStack(LED.BULB))
            .build();

    public static final List<Item> items = new ArrayList<>();

    public static Identifier id(String name) {
        return Identifier.of(LED.ID, name);
    }

    public static Item.Settings createItemSettings(Identifier id) {
        return new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, id));
    }

    public static AbstractBlock.Settings createBlockSettings(Identifier id) {
        return AbstractBlock.Settings.create().registryKey(RegistryKey.of(RegistryKeys.BLOCK, id));
    }

    public static Item registerSimpleItem(String name) {
        Item item = new Item(createItemSettings(id(name)));
        RegistryHelper.registerItem(id(name), item);
        return item;
    }

    public static void registerFixture(LedFixture fixture, LedVariant variant, Function<AbstractBlock.Settings, Block> supplier, LedVariant.RecipeFactory factory) {
        final String name = variant.getName(fixture.getId());

        for (DyeColor color : DyeColor.values()) {
            final Identifier id = id(name + "_" + color.getId());

            final Block block = supplier.apply(createBlockSettings(id));
            final Item item = new BlockItem(block, createItemSettings(id).useBlockPrefixedTranslationKey());

            addToGroup(item);
            registerItem(id, item);
            registerBlock(id, block);

            FIXTURES.setBlock(fixture, variant, color, block);
            DELEGATES.add(new ClientDelegate(color, block, item, id, factory));
        }
    }

    public static void registerFixture(LedFixture fixture, LedVariant variant, Function<AbstractBlock.Settings, Block> supplier) {
        registerFixture(fixture, variant, supplier, variant.getRecipeFactory(fixture.getPattern(), fixture));
    }

    public static void registerItem(Identifier id, Item item) {
        Registry.register(Registries.ITEM, id, item);
    }

    public static void addToGroup(Item item) {
        items.add(item);
    }

    public static void registerBlock(Identifier id, Block block) {
        Registry.register(Registries.BLOCK, id, block);
    }

    public static List<ClientDelegate> getClientDelegates() {
        return DELEGATES;
    }

    public static void appendItemsToGroup() {
        Identifier group = id("group");

        Registry.register(Registries.ITEM_GROUP, group, GROUP);
        RegistryKey<ItemGroup> key = RegistryKey.of(RegistryKeys.ITEM_GROUP, group);

        ItemGroupEvents.modifyEntriesEvent(key).register(listener -> {
            items.forEach(listener::add);
        });
    }

}
