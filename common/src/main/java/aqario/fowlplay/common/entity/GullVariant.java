package aqario.fowlplay.common.entity;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistryKeys;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public record GullVariant(Identifier texture) {
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<GullVariant>> PACKET_CODEC = PacketCodecs.registryEntry(FowlPlayRegistryKeys.GULL_VARIANT);
    public static final RegistryKey<GullVariant> HERRING = register("herring");
    public static final RegistryKey<GullVariant> RING_BILLED = register("ring_billed");
    public static final RegistryKey<GullVariant> BLACK_BACKED = register("black_backed");

    private static RegistryKey<GullVariant> register(String id) {
        RegistryKey<GullVariant> key = RegistryKey.of(FowlPlayRegistryKeys.GULL_VARIANT, FowlPlay.id(id));
        Identifier texture = FowlPlay.id("textures/entity/gull/" + key.getValue().getPath() + "_gull.png");
        PlatformHelper.registerVariant(id, key, () -> new GullVariant(texture));
        return key;
    }

    public static void init() {
    }
}
