package net.darktree.led.client.datagen;

import net.darktree.led.LED;
import net.darktree.led.util.LedDelegate;
import net.darktree.led.util.RegistryHelper;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.color.item.Constant;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;

public class LedModelProvider extends FabricModelProvider {

	public LedModelProvider(FabricPackOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators generator) {

	}

	@Override
	public void generateItemModels(ItemModelGenerators generator) {
		generator.generateFlatItem(LED.BULB, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(LED.LED, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(LED.SHADE, ModelTemplates.FLAT_ITEM);

		for (LedDelegate delegate : RegistryHelper.getClientDelegates()) {
			generator.itemModelOutput.accept(delegate.item, ItemModelUtils.tintedModel(delegate.getItemModelPath(), new Constant(delegate.getTint())));
		}
	}

	@Override
	public String getName() {
		return "LedModelProvider";
	}

}
