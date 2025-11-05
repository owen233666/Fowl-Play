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

public record DuckVariant(Identifier texture) {
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<DuckVariant>> PACKET_CODEC = PacketCodecs.registryEntry(FowlPlayRegistryKeys.DUCK_VARIANT);
    public static final RegistryKey<DuckVariant> GREEN_HEADED = register("green_headed");
    public static final RegistryKey<DuckVariant> BROWN = register("brown");

    private static RegistryKey<DuckVariant> register(String id) {
        RegistryKey<DuckVariant> key = RegistryKey.of(FowlPlayRegistryKeys.DUCK_VARIANT, FowlPlay.id(id));
        Identifier texture = FowlPlay.id("textures/entity/duck/" + key.getValue().getPath() + "_duck.png");
        PlatformHelper.registerVariant(id, key, () -> new DuckVariant(texture));
        return key;
    }

    public static void init() {
    }
}
