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

public record GullVariant(Identifier texture) {
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<GullVariant>> PACKET_CODEC = PacketCodecs.registryEntry(FowlPlayRegistryKeys.GULL_VARIANT);
    public static final RegistryKey<GullVariant> HERRING = register("herring");
    public static final RegistryKey<GullVariant> RING_BILLED = register("ring_billed");

    private static RegistryKey<GullVariant> register(String id) {
        RegistryKey<GullVariant> key = RegistryKey.of(FowlPlayRegistryKeys.GULL_VARIANT, Identifier.of(FowlPlay.ID, id));
        Identifier texture = Identifier.of(FowlPlay.ID, "textures/entity/gull/" + key.getValue().getPath() + "_gull.png");
        Registry.register(FowlPlayRegistries.GULL_VARIANT, key, new GullVariant(texture));
        return key;
    }

    public static void init() {
    }
}
