package aqario.fowlplay.core.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class PlatformHelper {
    @ExpectPlatform
    public static boolean isModLoaded(String modId) {
        throw new AssertionError();
    }
}
