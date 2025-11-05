package aqario.fowlplay.common.entity;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistryKeys;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;

public record ChickenVariant(String id) {
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<ChickenVariant>> PACKET_CODEC = PacketCodecs.registryEntry(FowlPlayRegistryKeys.CHICKEN_VARIANT);
    public static final RegistryKey<ChickenVariant> WHITE = register("white");
    public static final RegistryKey<ChickenVariant> RED_JUNGLEFOWL = register("red_junglefowl");

    private static RegistryKey<ChickenVariant> register(String id) {
        RegistryKey<ChickenVariant> key = RegistryKey.of(FowlPlayRegistryKeys.CHICKEN_VARIANT, FowlPlay.id(id));
        PlatformHelper.registerVariant(id, key, () -> new ChickenVariant(id));
        return key;
    }

    public static void init() {
    }
}
