package net.darktree.led.util;

import net.darktree.led.LED;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.function.Supplier;

public class RegistryHelper {

    private static final ArrayList<ClientDelegate> delegates = new ArrayList<>();
    public static final String ID = "led";

    public static final ItemGroup GROUP = FabricItemGroupBuilder.create( id("group") )
            .icon( () -> new ItemStack(LED.BULB) )
            .build();

    public static final FabricItemSettings ITEM_SETTINGS = new FabricItemSettings()
            .group( GROUP );

    public static FabricBlockSettings settings() {
        return FabricBlockSettings.of(Material.METAL).breakByHand(true).strength(0.4f);
    }

    public static Identifier id( String name ) {
        return new Identifier( ID, name );
    }

    public static void registerForColors(String name, Supplier<Block> supplier, DiodeVariant.RecipeDelegate recipe) {
        for( DyeColor color : DyeColor.values() ) {
            Block block = supplier.get();
            Item item = new BlockItem(block, ITEM_SETTINGS);
            String suffix = "_" + color.getName();

            ClientDelegate delegate = new ClientDelegate(color, block, item);
            registerItem( name + suffix, item );
            registerBlock( name + suffix, block );

            recipe.register( item, color );

            delegates.add(delegate);
        }
    }

    public static void registerItem( String name, Item item ) {
        Registry.register( Registry.ITEM, id(name), item );
    }

    public static void registerBlock( String name, Block block ) {
        Registry.register( Registry.BLOCK, id(name), block );
    }

    @Environment(EnvType.CLIENT)
    public static void applyDelegates() {

        for( ClientDelegate delegate : delegates ) {
            delegate.register();
        }

        LED.LOG.info("[LED] Applied " + delegates.size() + " client delegates.");
        delegates.clear();
    }

    @Environment(EnvType.SERVER)
    public static void discardDelegates() {
        LED.LOG.info("[LED] Discarded " + delegates.size() + " client delegates.");
        delegates.clear();
    }

}
