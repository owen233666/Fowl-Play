package aqario.fowlplay.core;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.RegistryKeys;

public class FowlPlayParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(
        FowlPlay.ID,
        RegistryKeys.PARTICLE_TYPE
    );
    public static final RegistrySupplier<SimpleParticleType> SMALL_BUBBLE = register("small_bubble", false);

    private static RegistrySupplier<SimpleParticleType> register(String name, boolean alwaysShow) {
        return PARTICLE_TYPES.register(name, () -> new SimpleParticleType(alwaysShow));
    }

    public static void init() {
        PARTICLE_TYPES.register();
    }
}
