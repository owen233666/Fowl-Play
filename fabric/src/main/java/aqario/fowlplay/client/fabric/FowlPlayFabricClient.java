package aqario.fowlplay.client.fabric;

import aqario.fowlplay.client.FowlPlayClient;
import net.fabricmc.api.ClientModInitializer;

public final class FowlPlayFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FowlPlayClient.init();
        FowlPlayClient.registerModelLayers();
        FowlPlayClient.registerEntityRenderers();
        FowlPlayClient.registerParticleFactories();
    }
}
