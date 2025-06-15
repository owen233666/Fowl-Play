package aqario.fowlplay.client.neoforge;

import aqario.fowlplay.client.FowlPlayClient;
import aqario.fowlplay.client.particle.SmallBubbleParticle;
import aqario.fowlplay.core.FowlPlayParticleTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

public class FowlPlayNeoForgeClient {
    public static void init(IEventBus modBus) {

        modBus.addListener(FowlPlayNeoForgeClient::onClientSetup);
        modBus.addListener(FowlPlayNeoForgeClient::onRegisterParticles);
        modBus.addListener(FowlPlayNeoForgeClient::onRegisterEntityRenderers);
        modBus.addListener(FowlPlayNeoForgeClient::onRegisterEntityLayers);
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        FowlPlayClient.init();
    }

    public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
//        FowlPlayClient.registerParticleFactories();
        event.registerSpriteSet(FowlPlayParticleTypes.SMALL_BUBBLE.get(), SmallBubbleParticle.Factory::new);
    }

    public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        FowlPlayClient.registerEntityRenderers();
    }

    public static void onRegisterEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        FowlPlayClient.registerModelLayers();
    }
}
