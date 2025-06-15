package aqario.fowlplay.client.fabric;

import aqario.fowlplay.client.FowlPlayClient;
import aqario.fowlplay.client.particle.SmallBubbleParticle;
import aqario.fowlplay.core.FowlPlayParticleTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public final class FowlPlayFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FowlPlayClient.init();
        FowlPlayClient.registerModelLayers();
        FowlPlayClient.registerEntityRenderers();
//        FowlPlayClient.registerParticleFactories();
        ParticleFactoryRegistry.getInstance().register(FowlPlayParticleTypes.SMALL_BUBBLE.get(), SmallBubbleParticle.Factory::new);
    }
}
