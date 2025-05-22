package aqario.fowlplay.common.util;

import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public final class RegistryBuilder<T> {
    private final RegistryKey<Registry<T>> registryKey;
    private boolean sync = false;

    private RegistryBuilder(RegistryKey<Registry<T>> registryKey) {
        this.registryKey = registryKey;
    }

    public static <T> RegistryBuilder<T> create(RegistryKey<Registry<T>> registryKey) {
        return new RegistryBuilder<>(registryKey);
    }

    public RegistryBuilder<T> sync() {
        this.sync = true;
        return this;
    }

    public Registry<T> buildAndRegister() {
        return PlatformHelper.registerRegistry(this.registryKey, this.sync);
    }
}
