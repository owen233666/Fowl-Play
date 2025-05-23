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

public record PigeonVariant(Identifier texture) {
//    public static final DeferredRegister<PigeonVariant> PIGEON_VARIANTS = DeferredRegister.create(
//        FowlPlay.ID,
//        FowlPlayRegistryKeys.PIGEON_VARIANT
//    );
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<PigeonVariant>> PACKET_CODEC = PacketCodecs.registryEntry(FowlPlayRegistryKeys.PIGEON_VARIANT);
    public static final RegistryKey<PigeonVariant> BANDED = register("banded");
    public static final RegistryKey<PigeonVariant> CHECKERED = register("checkered");
    public static final RegistryKey<PigeonVariant> GRAY = register("gray");
    public static final RegistryKey<PigeonVariant> RUSTY = register("rusty");
    public static final RegistryKey<PigeonVariant> WHITE = register("white");

    private static RegistryKey<PigeonVariant> register(String id) {
        RegistryKey<PigeonVariant> key = RegistryKey.of(FowlPlayRegistryKeys.PIGEON_VARIANT, Identifier.of(FowlPlay.ID, id));
        Identifier texture = Identifier.of(FowlPlay.ID, "textures/entity/pigeon/" + key.getValue().getPath() + "_pigeon.png");
//        PIGEON_VARIANTS.register(id, () -> new PigeonVariant(texture));
        PlatformHelper.registerVariant(id, key, () -> new PigeonVariant(texture));
        return key;
    }

    public static void init() {
//        PIGEON_VARIANTS.register();
    }
}
