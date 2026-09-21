package net.darktree.led.client.datagen;

import net.darktree.led.util.ClientDelegate;
import net.darktree.led.util.RegistryHelper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;
import java.util.concurrent.CompletableFuture;

public class LedLootProvider extends FabricBlockLootTableProvider {

	public LedLootProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
		super(output, lookup);
	}

	@Override
	public void generate() {
		for (ClientDelegate delegate : RegistryHelper.getClientDelegates()) {
			dropSelf(delegate.block);
		}
	}

}
