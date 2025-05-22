package aqario.fowlplay.core.platform.neoforge;

import net.neoforged.fml.ModList;

public class PlatformHelperImpl {
    public static boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }
}
