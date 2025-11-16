package aqario.fowlplay.common.entity;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistryKeys;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;

public record SparrowVariant(ResourceLocation texture) {
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<SparrowVariant>> PACKET_CODEC = ByteBufCodecs.holderRegistry(FowlPlayRegistryKeys.SPARROW_VARIANT);
    public static final ResourceKey<SparrowVariant> BROWN = register("brown");
    public static final ResourceKey<SparrowVariant> PALE = register("pale");

    private static ResourceKey<SparrowVariant> register(String id) {
        ResourceKey<SparrowVariant> key = ResourceKey.create(FowlPlayRegistryKeys.SPARROW_VARIANT, FowlPlay.id(id));
        ResourceLocation texture = FowlPlay.id("textures/entity/sparrow/" + key.location().getPath() + "_sparrow.png");
        PlatformHelper.registerVariant(id, key, () -> new SparrowVariant(texture));
        return key;
    }

    public static void init() {
    }
}
