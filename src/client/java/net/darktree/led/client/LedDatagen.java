package net.darktree.led.client;

import net.darktree.led.client.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;

public class LedDatagen implements DataGeneratorEntrypoint {

	@Override
	public void buildRegistry(RegistrySetBuilder builder) {
		LedRegistryBuilders.setupRegistryBuilders(builder);
	}

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();

		pack.addProvider(LedModelProvider::new);
		pack.addProvider(LedBlockLootProvider::new);
		pack.addProvider(LedRecipeProvider::new);
		pack.addProvider(LedBlockTagProvider::new);
		pack.addProvider(LedSimpleLootProvider::new);
		pack.addProvider(LedDynamicProvider::new);
	}

}
