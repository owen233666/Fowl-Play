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
import net.minecraft.util.StringIdentifiable;

public record GooseVariant(Identifier texture, ModelType modelType) {
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<GooseVariant>> PACKET_CODEC = PacketCodecs.registryEntry(FowlPlayRegistryKeys.GOOSE_VARIANT);
    public static final RegistryKey<GooseVariant> GREYLAG = register("greylag", ModelType.WILD);
    public static final RegistryKey<GooseVariant> CANADA = register("canada", ModelType.WILD);
    public static final RegistryKey<GooseVariant> SWAN = register("swan", ModelType.WILD);
    public static final RegistryKey<GooseVariant> EMDEN = register("emden", ModelType.DOMESTIC);
    public static final RegistryKey<GooseVariant> CHINESE = register("chinese", ModelType.DOMESTIC);

    private static RegistryKey<GooseVariant> register(String id, ModelType modelType) {
        RegistryKey<GooseVariant> key = RegistryKey.of(FowlPlayRegistryKeys.GOOSE_VARIANT, Identifier.of(FowlPlay.ID, id));
        Identifier texture = Identifier.of(FowlPlay.ID, "textures/entity/goose/" + key.getValue().getPath() + "_goose.png");
        PlatformHelper.registerVariant(id, key, () -> new GooseVariant(texture, modelType));
        return key;
    }

    public static void init() {
    }

    public enum ModelType implements StringIdentifiable {
        WILD("wild"),
        DOMESTIC("modelType");

        private final String name;

        ModelType(final String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return this.name;
        }
    }
}
