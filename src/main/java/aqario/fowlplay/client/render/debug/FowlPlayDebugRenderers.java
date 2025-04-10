package aqario.fowlplay.client.render.debug;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.util.math.MatrixStack;

import java.util.LinkedList;
import java.util.List;

public class FowlPlayDebugRenderers {
    private static final List<DebugRenderer.Renderer> RENDERERS = new LinkedList<>();

    public static void add(DebugRenderer.Renderer renderer) {
        RENDERERS.add(renderer);
    }

    public static void render(MatrixStack poseStack, VertexConsumerProvider bufferSource, double camX, double camY, double camZ) {
        RENDERERS.forEach((r) -> r.render(poseStack, bufferSource, camX, camY, camZ));
    }

    public static void reset() {
        RENDERERS.forEach(DebugRenderer.Renderer::clear);
    }

    public static void init() {
        add(BirdDebugRenderer.INSTANCE);
    }
}
