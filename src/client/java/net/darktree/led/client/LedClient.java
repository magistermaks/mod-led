package net.darktree.led.client;

import net.darktree.led.LED;
import net.darktree.led.util.ClientDelegate;
import net.darktree.led.util.RegistryHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;

import java.util.List;

public class LedClient implements ClientModInitializer {

	private void applyDelegate(ClientDelegate delegate) {
		BlockColorRegistry.register(List.of(state -> delegate.getTint()), delegate.block);
	}

	@Override
	public void onInitializeClient() {
		List<ClientDelegate> delegates = RegistryHelper.getClientDelegates();

		delegates.forEach(this::applyDelegate);
		LED.LOG.info("[LED] Applied {} client delegates.", delegates.size());
	}

}
