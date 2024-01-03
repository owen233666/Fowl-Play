package aqario.fowlplay.client;

import aqario.fowlplay.client.render.PenguinRenderer;
import aqario.fowlplay.client.render.PigeonRenderer;
import aqario.fowlplay.client.render.RobinRenderer;
import aqario.fowlplay.client.render.SeagullRenderer;
import aqario.fowlplay.common.entity.FowlPlayEntityType;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class FowlPlayClient implements ClientModInitializer {
    @Override
    public void onInitializeClient(ModContainer mod) {
        EntityRendererRegistry.register(FowlPlayEntityType.PENGUIN, PenguinRenderer::new);
        EntityRendererRegistry.register(FowlPlayEntityType.PIGEON, PigeonRenderer::new);
        EntityRendererRegistry.register(FowlPlayEntityType.ROBIN, RobinRenderer::new);
        EntityRendererRegistry.register(FowlPlayEntityType.SEAGULL, SeagullRenderer::new);
    }
}
