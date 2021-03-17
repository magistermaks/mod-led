package net.darktree.led.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;

import java.util.Collections;
import java.util.List;

public class LootHelper {

    public static List<ItemStack> dispatch(BlockState state, LootContext.Builder builder, Block block) {
        LootContext lootContext = builder.parameter(LootContextParameters.BLOCK_STATE, state).build(LootContextTypes.BLOCK);
        ServerWorld serverWorld = lootContext.getWorld();
        LootTable lootTable = serverWorld.getServer().getLootManager().getTable( block.getLootTableId() );

        if( lootTable == LootTable.EMPTY ) {
            if( block instanceof DropsItself ) {
                return dropItself(block);
            }
        }

        return lootTable.generateLoot(lootContext);
    }

    private static List<ItemStack> dropItself(Block block) {
        return Collections.singletonList( new ItemStack( block.asItem() ) );
    }

    public interface DropsItself {
        // used to mark blocks that drop themselves
    }

}
