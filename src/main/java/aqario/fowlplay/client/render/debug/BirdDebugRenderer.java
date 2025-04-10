package aqario.fowlplay.client.render.debug;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class BirdDebugRenderer implements DebugRenderer.Renderer {
    public static final BirdDebugRenderer INSTANCE = new BirdDebugRenderer();
    private final MinecraftClient client;
    @Nullable
    private UUID targetedEntity;

    BirdDebugRenderer() {
        this.client = MinecraftClient.getInstance();
    }

    @Override
    public void clear() {
        this.targetedEntity = null;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {

    }

    private void updateTargetedEntity() {
        DebugRenderer.getTargetedEntity(this.client.getCameraEntity(), 8).ifPresent(entity -> this.targetedEntity = entity.getUuid());
    }
}
