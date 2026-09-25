package net.darktree.led.client.datagen;

import net.darktree.led.util.LedDelegate;
import net.darktree.led.util.RegistryHelper;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.concurrent.CompletableFuture;

public class LedBlockLootProvider extends FabricBlockLootSubProvider {

	public LedBlockLootProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
		super(output, lookup);
	}

	@Override
	public void generate() {
		for (LedDelegate delegate : RegistryHelper.getClientDelegates()) {

			if (!delegate.variant.isReinforced()) {
				dropSelf(delegate.block);
				continue;
			}

			Item unpacked = delegate.variant.withReinforced(false).getItem(delegate.fixture, delegate.color);

			add(delegate.block, LootTable.lootTable()
					.withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
							.add(LootItem.lootTableItem(delegate.block))
							.when(hasSilkTouch())
							.when(ExplosionCondition.survivesExplosion()))
					.withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
							.add(LootItem.lootTableItem(unpacked))
							.when(doesNotHaveSilkTouch())
							.when(ExplosionCondition.survivesExplosion()))
					.withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
							.add(LootItem.lootTableItem(Items.IRON_BARS))
							.when(doesNotHaveSilkTouch())
							.when(ExplosionCondition.survivesExplosion()))
			);

		}
	}

}
