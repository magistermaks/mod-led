package net.darktree.led.client.datagen;

import net.darktree.led.LED;
import net.darktree.led.util.ClientDelegate;
import net.darktree.led.util.RegistryHelper;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.color.item.Constant;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import org.jspecify.annotations.NonNull;

public class LedModelProvider extends FabricModelProvider {

	public LedModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(@NonNull BlockModelGenerators generator) {

	}

	@Override
	public void generateItemModels(ItemModelGenerators generator) {
		generator.generateFlatItem(LED.BULB, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(LED.LED, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(LED.SHADE, ModelTemplates.FLAT_ITEM);

		for (ClientDelegate delegate : RegistryHelper.getClientDelegates()) {
			generator.itemModelOutput.accept(delegate.item, ItemModelUtils.tintedModel(delegate.getItemModelPath(), new Constant(delegate.getTint())));
		}
	}

	@Override
	public String getName() {
		return "LedModelProvider";
	}

}
