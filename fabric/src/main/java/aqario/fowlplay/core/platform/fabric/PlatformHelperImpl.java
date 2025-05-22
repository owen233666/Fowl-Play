package aqario.fowlplay.core.platform.fabric;

import net.fabricmc.loader.api.FabricLoader;

public class PlatformHelperImpl {
    public static boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }
}
