package net.darktree.led.client.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import net.minecraft.resource.ResourceManager;
import net.minecraft.text.Text;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.function.Consumer;

@Mixin(SplashTextResourceSupplier.class)
public class SplashTextMixin {

	@Shadow
	private static Text create(String text) {
		throw new UnsupportedOperationException();
	}

	@WrapMethod(method = "prepare(Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)Ljava/util/List;")
	protected List<Text> prepare(ResourceManager resourceManager, Profiler profiler, Operation<List<Text>> original) {
		List<Text> injected = new ArrayList<>(original.call(resourceManager, profiler));

		Consumer<String> inject = encoded -> {
			injected.add(create(new String(Base64.getDecoder().decode(encoded))));
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
