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

public record GooseVariant(Identifier texture, boolean domestic) {
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<GooseVariant>> PACKET_CODEC = PacketCodecs.registryEntry(FowlPlayRegistryKeys.GOOSE_VARIANT);
    public static final RegistryKey<GooseVariant> GREYLAG = register("greylag", false);
    public static final RegistryKey<GooseVariant> CANADA = register("canada", false);
    public static final RegistryKey<GooseVariant> EMDEN = register("emden", true);
    public static final RegistryKey<GooseVariant> CHINESE = register("chinese", true);

    private static RegistryKey<GooseVariant> register(String id, boolean domestic) {
        RegistryKey<GooseVariant> key = RegistryKey.of(FowlPlayRegistryKeys.GOOSE_VARIANT, Identifier.of(FowlPlay.ID, id));
        Identifier texture = Identifier.of(FowlPlay.ID, "textures/entity/goose/" + key.getValue().getPath() + "_goose.png");
        PlatformHelper.registerVariant(id, key, () -> new GooseVariant(texture, domestic));
        return key;
    }

    public static void init() {
    }
}
