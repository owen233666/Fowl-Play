package aqario.fowlplay.client;

import aqario.fowlplay.client.particle.SmallBubbleParticle;
import aqario.fowlplay.client.render.debug.BirdDebugRenderer;
import aqario.fowlplay.client.render.entity.*;
import aqario.fowlplay.client.render.entity.model.*;
import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.network.s2c.DebugBirdCustomPayload;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayEntityType;
import aqario.fowlplay.core.FowlPlayParticleTypes;
import com.google.common.base.Suppliers;
import dev.architectury.networking.NetworkManager;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import io.github.flemmli97.debugutils.api.RegisterDebugRenderers;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;

@SuppressWarnings("unused")
public class FowlPlayClient {
    public static boolean DEBUG_BIRD = false;

    public static void init() {
        if(FowlPlay.isDebugUtilsLoaded()) {
            Identifier debugBirdId = Identifier.of(FowlPlay.ID, "debug/bird");
            RegisterDebugRenderers.registerCustomDebugRenderer(debugBirdId, BirdDebugRenderer.INSTANCE);
            RegisterDebugRenderers.registerServerToggle(debugBirdId);
            RegisterDebugRenderers.registerClientHandler(debugBirdId, b -> FowlPlayClient.DEBUG_BIRD = b);

//            NetworkManager.registerS2CPayloadType(DebugBirdCustomPayload.ID, DebugBirdCustomPayload.CODEC);
            NetworkManager.registerReceiver(
                NetworkManager.Side.S2C,
                DebugBirdCustomPayload.ID,
                DebugBirdCustomPayload.CODEC,
                (payload, context) ->
                    DebugBirdCustomPayload.onReceive(payload)
            );
        }
    }

    public static void registerModelLayers() {
        EntityModelLayerRegistry.register(BlueJayEntityModel.MODEL_LAYER, BlueJayEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.register(CardinalEntityModel.MODEL_LAYER, CardinalEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.register(ChickadeeEntityModel.MODEL_LAYER, ChickadeeEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.register(CrowEntityModel.MODEL_LAYER, CrowEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.register(DuckEntityModel.MODEL_LAYER, DuckEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.register(GullEntityModel.MODEL_LAYER, GullEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.register(HawkEntityModel.MODEL_LAYER, HawkEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.register(PenguinEntityModel.MODEL_LAYER, PenguinEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.register(BabyPenguinEntityModel.MODEL_LAYER, BabyPenguinEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.register(PigeonEntityModel.MODEL_LAYER, PigeonEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.register(RavenEntityModel.MODEL_LAYER, RavenEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.register(RobinEntityModel.MODEL_LAYER, RobinEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.register(SparrowEntityModel.MODEL_LAYER, SparrowEntityModel::getTexturedModelData);

        if(FowlPlayConfig.getInstance().customChickenModel) {
            EntityModelLayerRegistry.register(CustomChickenEntityModel.MODEL_LAYER, CustomChickenEntityModel::getTexturedModelData);
            EntityModelLayerRegistry.register(CustomBabyChickenEntityModel.MODEL_LAYER, CustomBabyChickenEntityModel::getTexturedModelData);
        }
    }

    public static void registerEntityRenderers() {
        EntityRendererRegistry.register(FowlPlayEntityType.BLUE_JAY, BlueJayEntityRenderer::new);
        EntityRendererRegistry.register(FowlPlayEntityType.CARDINAL, CardinalEntityRenderer::new);
        EntityRendererRegistry.register(FowlPlayEntityType.CHICKADEE, ChickadeeEntityRenderer::new);
        EntityRendererRegistry.register(FowlPlayEntityType.CROW, CrowEntityRenderer::new);
        EntityRendererRegistry.register(FowlPlayEntityType.DUCK, DuckEntityRenderer::new);
        EntityRendererRegistry.register(FowlPlayEntityType.GULL, GullEntityRenderer::new);
        EntityRendererRegistry.register(FowlPlayEntityType.HAWK, HawkEntityRenderer::new);
        EntityRendererRegistry.register(FowlPlayEntityType.PENGUIN, PenguinEntityRenderer::new);
        EntityRendererRegistry.register(FowlPlayEntityType.PIGEON, PigeonEntityRenderer::new);
        EntityRendererRegistry.register(FowlPlayEntityType.RAVEN, RavenEntityRenderer::new);
        EntityRendererRegistry.register(FowlPlayEntityType.ROBIN, RobinEntityRenderer::new);
        EntityRendererRegistry.register(FowlPlayEntityType.SPARROW, SparrowEntityRenderer::new);

        if(FowlPlayConfig.getInstance().customChickenModel) {
            EntityRendererRegistry.register(Suppliers.ofInstance(EntityType.CHICKEN), CustomChickenEntityRenderer::new);
        }
    }

    public static void registerParticleFactories() {
        ParticleProviderRegistry.register(FowlPlayParticleTypes.SMALL_BUBBLE.get(), SmallBubbleParticle.Factory::new);
    }
}
