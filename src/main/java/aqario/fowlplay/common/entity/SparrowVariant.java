package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistries;
import aqario.fowlplay.core.FowlPlayRegistryKeys;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public record SparrowVariant(Identifier texture) {
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<SparrowVariant>> PACKET_CODEC = PacketCodecs.registryEntry(FowlPlayRegistryKeys.SPARROW_VARIANT);
    public static final RegistryKey<SparrowVariant> BROWN = register("brown");
    public static final RegistryKey<SparrowVariant> PALE = register("pale");

    private static RegistryKey<SparrowVariant> register(String id) {
        RegistryKey<SparrowVariant> key = RegistryKey.of(FowlPlayRegistryKeys.SPARROW_VARIANT, Identifier.of(FowlPlay.ID, id));
        Identifier texture = Identifier.of(FowlPlay.ID, "textures/entity/sparrow/" + key.getValue().getPath() + "_sparrow.png");
        Registry.register(FowlPlayRegistries.SPARROW_VARIANT, key, new SparrowVariant(texture));
        return key;
    }

    public static void init() {
    }
}
