package aqario.fowlplay.common.entity;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistryKeys;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;

public record ChickenVariant(String id) {
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<ChickenVariant>> PACKET_CODEC = ByteBufCodecs.holderRegistry(FowlPlayRegistryKeys.CHICKEN_VARIANT);
    public static final ResourceKey<ChickenVariant> WHITE = register("white");
    public static final ResourceKey<ChickenVariant> RED_JUNGLEFOWL = register("red_junglefowl");

    private static ResourceKey<ChickenVariant> register(String id) {
        ResourceKey<ChickenVariant> key = ResourceKey.create(FowlPlayRegistryKeys.CHICKEN_VARIANT, FowlPlay.id(id));
        PlatformHelper.registerVariant(id, key, () -> new ChickenVariant(id));
        return key;
    }

    public static void init() {
    }
}
