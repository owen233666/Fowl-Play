package aqario.fowlplay.mixin;

import aqario.fowlplay.client.render.debug.FowlPlayDebugRenderers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DebugRenderer.class)
public class DebugRendererMixin {
    @Inject(method = "render", at = @At("RETURN"))
    private void fowlplay$addDebugRenderers(MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers, double cameraX, double cameraY, double cameraZ, CallbackInfo ci) {
        FowlPlayDebugRenderers.render(matrices, vertexConsumers, cameraX, cameraY, cameraZ);
    }

    @Inject(method = "reset", at = @At("RETURN"))
    private void fowlplay$resetDebugRenderers(CallbackInfo ci) {
        FowlPlayDebugRenderers.reset();
    }
}
