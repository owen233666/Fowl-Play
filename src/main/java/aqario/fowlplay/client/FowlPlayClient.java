package aqario.fowlplay.client;

import aqario.fowlplay.client.model.*;
import aqario.fowlplay.client.render.*;
import aqario.fowlplay.common.entity.FowlPlayEntityType;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class FowlPlayClient implements ClientModInitializer {
    @Override
    public void onInitializeClient(ModContainer mod) {
        EntityModelLayerRegistry.registerModelLayer(FowlPlayEntityModelLayers.BLUE_JAY, BlueJayEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.BLUE_JAY, BlueJayEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(FowlPlayEntityModelLayers.CARDINAL, CardinalEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.CARDINAL, CardinalEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(FowlPlayEntityModelLayers.PENGUIN, PenguinEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.PENGUIN, PenguinEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(FowlPlayEntityModelLayers.PIGEON, PigeonEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.PIGEON, PigeonEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(FowlPlayEntityModelLayers.ROBIN, RobinEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.ROBIN, RobinEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(FowlPlayEntityModelLayers.SEAGULL, SeagullEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.SEAGULL, SeagullEntityRenderer::new);
    }
}
