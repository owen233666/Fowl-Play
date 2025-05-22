package aqario.fowlplay.client;

import net.fabricmc.api.ClientModInitializer;

public final class FowlPlayFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FowlPlayClient.init();
    }
}
