package aqario.fowlplay.common.entity;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistryKeys;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public record ChickenVariant(String id) {
    public static final DeferredRegister<ChickenVariant> CHICKEN_VARIANTS = DeferredRegister.create(
        FowlPlay.ID,
        FowlPlayRegistryKeys.CHICKEN_VARIANT
    );
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<ChickenVariant>> PACKET_CODEC = PacketCodecs.registryEntry(FowlPlayRegistryKeys.CHICKEN_VARIANT);
    public static final RegistryKey<ChickenVariant> WHITE = register("white");
    public static final RegistryKey<ChickenVariant> RED_JUNGLEFOWL = register("red_junglefowl");

    private static RegistryKey<ChickenVariant> register(String id) {
        CHICKEN_VARIANTS.register(id, () -> new ChickenVariant(id));
        return RegistryKey.of(FowlPlayRegistryKeys.CHICKEN_VARIANT, Identifier.of(FowlPlay.ID, id));
    }

    public static void init() {
        CHICKEN_VARIANTS.register();
    }
}
