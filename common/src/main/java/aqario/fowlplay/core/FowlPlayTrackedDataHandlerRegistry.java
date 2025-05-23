package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.*;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Uuids;

import java.util.List;
import java.util.UUID;

public final class FowlPlayTrackedDataHandlerRegistry {
    public static final TrackedDataHandler<RegistryEntry<ChickenVariant>> CHICKEN_VARIANT = register(
        "chicken_variant",
        TrackedDataHandler.create(ChickenVariant.PACKET_CODEC)
    );
    public static final TrackedDataHandler<RegistryEntry<DuckVariant>> DUCK_VARIANT = register(
        "duck_variant",
        TrackedDataHandler.create(DuckVariant.PACKET_CODEC)
    );
    public static final TrackedDataHandler<RegistryEntry<GullVariant>> GULL_VARIANT = register(
        "gull_variant",
        TrackedDataHandler.create(GullVariant.PACKET_CODEC)
    );
    public static final TrackedDataHandler<RegistryEntry<PigeonVariant>> PIGEON_VARIANT = register(
        "pigeon_variant",
        TrackedDataHandler.create(PigeonVariant.PACKET_CODEC)
    );
    public static final TrackedDataHandler<RegistryEntry<SparrowVariant>> SPARROW_VARIANT = register(
        "sparrow_variant",
        TrackedDataHandler.create(SparrowVariant.PACKET_CODEC)
    );
    public static final TrackedDataHandler<List<UUID>> UUID_LIST = register(
        "uuid_list",
        TrackedDataHandler.create(Uuids.PACKET_CODEC.collect(PacketCodecs.toList()))
    );

    private static <T> TrackedDataHandler<T> register(String id, TrackedDataHandler<T> handler) {
        PlatformHelper.registerTrackedDataHandler(id, handler);
        return handler;
    }

    public static void init() {
    }
}
