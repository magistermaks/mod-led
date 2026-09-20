package net.darktree.led.client.datagen;

import net.darktree.led.LED;
import net.darktree.led.util.ClientDelegate;
import net.darktree.led.util.RegistryHelper;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.ItemModels;
import net.minecraft.client.data.Models;
import net.minecraft.client.render.item.tint.ConstantTintSource;
import net.minecraft.util.Identifier;

public class LedModelProvider extends FabricModelProvider {

	public LedModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator generator) {

	}

	@Override
	public void generateItemModels(ItemModelGenerator generator) {
		generator.register(LED.BULB, Models.GENERATED);
		generator.register(LED.LED, Models.GENERATED);
		generator.register(LED.SHADE, Models.GENERATED);

		for (ClientDelegate delegate : RegistryHelper.getClientDelegates()) {
			generator.output.accept(delegate.item, ItemModels.tinted(delegate.getItemModelPath(), new ConstantTintSource(delegate.getTint())));
		}
	}

	@Override
	public String getName() {
		return "LedModelProvider";
	}

}
