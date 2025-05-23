package aqario.fowlplay.core;

import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.particle.SimpleParticleType;

import java.util.function.Supplier;

public class FowlPlayParticleTypes {
//    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(
//        FowlPlay.ID,
//        RegistryKeys.PARTICLE_TYPE
//    );
    public static final Supplier<SimpleParticleType> SMALL_BUBBLE = register("small_bubble", false);

    private static Supplier<SimpleParticleType> register(String name, boolean alwaysShow) {
//        return PARTICLE_TYPES.register(name, () -> new SimpleParticleType(alwaysShow));
        return PlatformHelper.registerParticleType(name, () -> new SimpleParticleType(alwaysShow));
    }

    public static void init() {
//        PARTICLE_TYPES.register();
    }
}
