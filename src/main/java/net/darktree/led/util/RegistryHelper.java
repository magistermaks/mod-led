package net.darktree.led.util;

import net.darktree.led.LED;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class RegistryHelper {

    private static final List<ClientDelegate> DELEGATES = new ArrayList<>();

    public static final LedBlockSet FIXTURES = new LedBlockSet();
    public static final CreativeModeTab GROUP = FabricItemGroup.builder()
            .title(Component.translatable("itemGroup.led.group"))
            .icon(() -> new ItemStack(LED.BULB))
            .build();

    public static final List<Item> items = new ArrayList<>();

    public static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(LED.ID, name);
    }

    public static Item.Properties createItemSettings(Identifier id) {
        return new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id));
    }

    public static BlockBehaviour.Properties createBlockSettings(Identifier id) {
        return BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, id));
    }

    public static Item registerSimpleItem(String name) {
        Item item = new Item(createItemSettings(id(name)));
        RegistryHelper.registerItem(id(name), item);
        return item;
    }

    public static void registerFixture(LedFixture fixture, LedVariant variant, Function<BlockBehaviour.Properties, Block> supplier, LedVariant.RecipeFactory factory) {
        final String name = variant.getName(fixture.getId());

        for (DyeColor color : DyeColor.values()) {
            final Identifier id = id(name + "_" + color.getName());

            final Block block = supplier.apply(createBlockSettings(id));
            final Item item = new BlockItem(block, createItemSettings(id).useBlockDescriptionPrefix());

            addToGroup(item);
            registerItem(id, item);
            registerBlock(id, block);

            FIXTURES.setBlock(fixture, variant, color, block);
            DELEGATES.add(new ClientDelegate(color, block, item, id, factory));
        }
    }

    public static void registerFixture(LedFixture fixture, LedVariant variant, Function<BlockBehaviour.Properties, Block> supplier) {
        registerFixture(fixture, variant, supplier, variant.getRecipeFactory(fixture.getPattern(), fixture));
    }

    public static void registerItem(Identifier id, Item item) {
        Registry.register(BuiltInRegistries.ITEM, id, item);
    }

    public static void addToGroup(Item item) {
        items.add(item);
    }

    public static void registerBlock(Identifier id, Block block) {
        Registry.register(BuiltInRegistries.BLOCK, id, block);
    }

    public static List<ClientDelegate> getClientDelegates() {
        return DELEGATES;
    }

    public static void appendItemsToGroup() {
        Identifier group = id("group");

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, group, GROUP);
        ResourceKey<CreativeModeTab> key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, group);

        ItemGroupEvents.modifyEntriesEvent(key).register(listener -> {
            items.forEach(listener::accept);
        });
    }

}
