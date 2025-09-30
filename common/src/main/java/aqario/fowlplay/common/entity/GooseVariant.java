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

public record GooseVariant(Identifier texture) {
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<GooseVariant>> PACKET_CODEC = PacketCodecs.registryEntry(FowlPlayRegistryKeys.GOOSE_VARIANT);
    public static final RegistryKey<GooseVariant> WHITE = register("white");
    public static final RegistryKey<GooseVariant> CANADA = register("canada");

    private static RegistryKey<GooseVariant> register(String id) {
        RegistryKey<GooseVariant> key = RegistryKey.of(FowlPlayRegistryKeys.GOOSE_VARIANT, Identifier.of(FowlPlay.ID, id));
        Identifier texture = Identifier.of(FowlPlay.ID, "textures/entity/goose/" + key.getValue().getPath() + "_goose.png");
        PlatformHelper.registerVariant(id, key, () -> new GooseVariant(texture));
        return key;
    }

    public static void init() {
    }
}
