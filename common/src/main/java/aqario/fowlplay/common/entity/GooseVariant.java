package aqario.fowlplay.common.entity;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistries;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;

public record GooseVariant(ResourceLocation texture, ModelType modelType) {
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<GooseVariant>> PACKET_CODEC = ByteBufCodecs.holderRegistry(FowlPlayRegistries.GOOSE_VARIANT);
    public static final ResourceKey<GooseVariant> GREYLAG = register("greylag", ModelType.WILD);
    public static final ResourceKey<GooseVariant> CANADA = register("canada", ModelType.WILD);
    public static final ResourceKey<GooseVariant> SWAN = register("swan", ModelType.WILD);
    public static final ResourceKey<GooseVariant> EMDEN = register("emden", ModelType.DOMESTIC);
    public static final ResourceKey<GooseVariant> CHINESE = register("chinese", ModelType.DOMESTIC);

    private static ResourceKey<GooseVariant> register(String id, ModelType modelType) {
        ResourceKey<GooseVariant> key = ResourceKey.create(FowlPlayRegistries.GOOSE_VARIANT, FowlPlay.id(id));
        ResourceLocation texture = FowlPlay.id("textures/entity/goose/" + key.location().getPath() + "_goose.png");
        PlatformHelper.registerVariant(id, key, () -> new GooseVariant(texture, modelType));
        return key;
    }

    public static void init() {
    }

    public enum ModelType implements StringRepresentable {
        WILD("wild"),
        DOMESTIC("modelType");

        private final String name;

        ModelType(final String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
