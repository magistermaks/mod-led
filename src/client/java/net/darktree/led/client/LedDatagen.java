package net.darktree.led.client;

import net.darktree.led.client.datagen.LedLootProvider;
import net.darktree.led.client.datagen.LedModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class LedDatagen implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();

		pack.addProvider(LedModelProvider::new);
		pack.addProvider(LedLootProvider::new);
	}

}
