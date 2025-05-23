package aqario.fowlplay.common.entity;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistryKeys;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public record DuckVariant(Identifier texture) {
    public static final DeferredRegister<DuckVariant> DUCK_VARIANTS = DeferredRegister.create(
        FowlPlay.ID,
        FowlPlayRegistryKeys.DUCK_VARIANT
    );
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<DuckVariant>> PACKET_CODEC = PacketCodecs.registryEntry(FowlPlayRegistryKeys.DUCK_VARIANT);
    public static final RegistryKey<DuckVariant> GREEN_HEADED = register("green_headed");
    public static final RegistryKey<DuckVariant> BROWN = register("brown");

    private static RegistryKey<DuckVariant> register(String id) {
        RegistryKey<DuckVariant> key = RegistryKey.of(FowlPlayRegistryKeys.DUCK_VARIANT, Identifier.of(FowlPlay.ID, id));
        Identifier texture = Identifier.of(FowlPlay.ID, "textures/entity/duck/" + key.getValue().getPath() + "_duck.png");
        DUCK_VARIANTS.register(id, () -> new DuckVariant(texture));
        return key;
    }

    public static void init() {
        DUCK_VARIANTS.register();
    }
}
