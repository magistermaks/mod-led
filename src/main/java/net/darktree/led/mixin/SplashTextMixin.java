package net.darktree.led.mixin;

import net.minecraft.client.resource.SplashTextResourceSupplier;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Base64;
import java.util.List;
import java.util.function.Consumer;

@Mixin(SplashTextResourceSupplier.class)
public class SplashTextMixin {

	@Shadow
	@Final
	private List<String> splashTexts;

	@Inject(
			method = "apply(Ljava/util/List;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V",
			at = @At("TAIL")
	)
	protected void apply(List<String> list, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci) {
		Consumer<String> inject = encoded -> {
			splashTexts.add(new String(Base64.getDecoder().decode(encoded)));
		};

		// nothing to see here
		inject.accept("Q2hlY2sgb3V0IHRoZSBBbWF6aW5nIERpZ2l0YWwgQ2lyY3Vz");
		inject.accept("QXMgc2VlbiBpbiBNQVUgTUFLQU4gQVBBIQ==");
		inject.accept("QWxzbyB0cnkgUmFpbiBXb3JsZCE=");
		inject.accept("VHJ5IHdpdGggUmVkIEJpdHMh");
		inject.accept("QWxzbyB0cnkgRmFjdG9yaW8h");
		inject.accept("QWxzbyB0cnkgTGl0dGxlQmlnUGxhbmV0IQ==");
	}

}
