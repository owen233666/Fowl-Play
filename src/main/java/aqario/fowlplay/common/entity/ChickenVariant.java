package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.registry.FowlPlayRegistries;
import aqario.fowlplay.common.registry.FowlPlayRegistryKeys;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public record ChickenVariant(String id) {
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<ChickenVariant>> PACKET_CODEC = PacketCodecs.registryEntry(FowlPlayRegistryKeys.CHICKEN_VARIANT);
    public static final RegistryKey<ChickenVariant> WHITE = register("white");
    public static final RegistryKey<ChickenVariant> RED_JUNGLEFOWL = register("red_junglefowl");

    private static RegistryKey<ChickenVariant> register(String id) {
        RegistryKey<ChickenVariant> key = RegistryKey.of(FowlPlayRegistryKeys.CHICKEN_VARIANT, Identifier.of(FowlPlay.ID, id));
        Registry.register(FowlPlayRegistries.CHICKEN_VARIANT, key, new ChickenVariant(id));
        return key;
    }

    public static void init() {
    }
}
