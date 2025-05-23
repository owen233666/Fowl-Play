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

public record SparrowVariant(Identifier texture) {
//    public static final DeferredRegister<SparrowVariant> SPARROW_VARIANTS = DeferredRegister.create(
//        FowlPlay.ID,
//        FowlPlayRegistryKeys.SPARROW_VARIANT
//    );
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<SparrowVariant>> PACKET_CODEC = PacketCodecs.registryEntry(FowlPlayRegistryKeys.SPARROW_VARIANT);
    public static final RegistryKey<SparrowVariant> BROWN = register("brown");
    public static final RegistryKey<SparrowVariant> PALE = register("pale");

    private static RegistryKey<SparrowVariant> register(String id) {
        RegistryKey<SparrowVariant> key = RegistryKey.of(FowlPlayRegistryKeys.SPARROW_VARIANT, Identifier.of(FowlPlay.ID, id));
        Identifier texture = Identifier.of(FowlPlay.ID, "textures/entity/sparrow/" + key.getValue().getPath() + "_sparrow.png");
//        SPARROW_VARIANTS.register(id, () -> new SparrowVariant(texture));
        PlatformHelper.registerVariant(id, key, () -> new SparrowVariant(texture));
        return key;
    }

    public static void init() {
//        SPARROW_VARIANTS.register();
    }
}
