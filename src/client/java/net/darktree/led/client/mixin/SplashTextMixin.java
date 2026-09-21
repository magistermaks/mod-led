package net.darktree.led.client.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.resources.SplashManager;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

@Mixin(SplashManager.class)
public class SplashTextMixin {

	@Shadow
	private static Component literalSplash(String text) {
		throw new UnsupportedOperationException();
	}

	@WrapMethod(method = "prepare(Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)Ljava/util/List;")
	protected List<Component> prepare(ResourceManager resourceManager, ProfilerFiller profiler, Operation<List<Component>> original) {
		List<Component> injected = new ArrayList<>(original.call(resourceManager, profiler));

		Consumer<String> inject = encoded -> {
			injected.add(literalSplash(new String(Base64.getDecoder().decode(encoded))));
		};

		// nothing to see here
		inject.accept("Q2hlY2sgb3V0IHRoZSBBbWF6aW5nIERpZ2l0YWwgQ2lyY3Vz");
		inject.accept("QXMgc2VlbiBpbiBNQVUgTUFLQU4gQVBBIQ==");
		inject.accept("QWxzbyB0cnkgUmFpbiBXb3JsZCE=");
		inject.accept("VHJ5IHdpdGggUmVkIEJpdHMh");
		inject.accept("QWxzbyB0cnkgRmFjdG9yaW8h");
		inject.accept("QWxzbyB0cnkgTGl0dGxlQmlnUGxhbmV0IQ==");

		return List.copyOf(injected);
	}

}
