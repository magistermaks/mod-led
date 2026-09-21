package net.darktree.led.client.datagen;

import net.darktree.led.LED;
import net.darktree.led.util.RegistryHelper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import org.jspecify.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class LedBlockTagProvider extends FabricTagProvider.BlockTagProvider {

	public LedBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void addTags(HolderLookup.Provider lookup) {

		var lamps = builder(LED.LAMPS);

		RegistryHelper.getClientDelegates().forEach(delegate -> {
			lamps.add(delegate.getBlockKey());
		});

		lamps.setReplace(true);

		builder(BlockTags.MINEABLE_WITH_PICKAXE)
				.addOptionalTag(LED.LAMPS)
				.setReplace(false);

	}

}
