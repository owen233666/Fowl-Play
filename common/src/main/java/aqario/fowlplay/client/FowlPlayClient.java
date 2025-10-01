package aqario.fowlplay.client;

import aqario.fowlplay.client.render.debug.BirdDebugRenderer;
import aqario.fowlplay.client.render.entity.*;
import aqario.fowlplay.client.render.entity.model.*;
import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.network.s2c.DebugBirdCustomPayload;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayEntityType;
import aqario.fowlplay.core.platform.PlatformHelper;
import com.google.common.base.Suppliers;
import dev.architectury.networking.NetworkManager;
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
        PlatformHelper.registerModelLayer(BlueJayEntityModel.MODEL_LAYER, BlueJayEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(CardinalEntityModel.MODEL_LAYER, CardinalEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(ChickadeeEntityModel.MODEL_LAYER, ChickadeeEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(CrowEntityModel.MODEL_LAYER, CrowEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(DuckEntityModel.MODEL_LAYER, DuckEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(GooseEntityModel.MODEL_LAYER, GooseEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(GullEntityModel.MODEL_LAYER, GullEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(HawkEntityModel.MODEL_LAYER, HawkEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(PenguinEntityModel.MODEL_LAYER, PenguinEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(BabyPenguinEntityModel.MODEL_LAYER, BabyPenguinEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(PigeonEntityModel.MODEL_LAYER, PigeonEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(RavenEntityModel.MODEL_LAYER, RavenEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(RobinEntityModel.MODEL_LAYER, RobinEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(SparrowEntityModel.MODEL_LAYER, SparrowEntityModel::getTexturedModelData);

        if(FowlPlayConfig.getInstance().customChickenModel) {
            PlatformHelper.registerModelLayer(CustomChickenEntityModel.MODEL_LAYER, CustomChickenEntityModel::getTexturedModelData);
            PlatformHelper.registerModelLayer(CustomBabyChickenEntityModel.MODEL_LAYER, CustomBabyChickenEntityModel::getTexturedModelData);
        }
    }

    public static void registerEntityRenderers() {
        PlatformHelper.registerEntityRenderer(FowlPlayEntityType.BLUE_JAY, BlueJayEntityRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityType.CARDINAL, CardinalEntityRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityType.CHICKADEE, ChickadeeEntityRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityType.CROW, CrowEntityRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityType.DUCK, DuckEntityRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityType.GOOSE, GooseEntityRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityType.GULL, GullEntityRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityType.HAWK, HawkEntityRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityType.PENGUIN, PenguinEntityRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityType.PIGEON, PigeonEntityRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityType.RAVEN, RavenEntityRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityType.ROBIN, RobinEntityRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityType.SPARROW, SparrowEntityRenderer::new);

        if(FowlPlayConfig.getInstance().customChickenModel) {
            PlatformHelper.registerEntityRenderer(Suppliers.ofInstance(EntityType.CHICKEN), CustomChickenEntityRenderer::new);
        }
    }

    // TODO: Fix cross-platform particle registration
    public static void registerParticleFactories() {
//        ParticleProviderRegistry.register(FowlPlayParticleTypes.SMALL_BUBBLE.get(), SmallBubbleParticle.Factory::new);
//        PlatformHelper.registerParticleFactory(FowlPlayParticleTypes.SMALL_BUBBLE, SmallBubbleParticle.Factory::new);
    }
}
