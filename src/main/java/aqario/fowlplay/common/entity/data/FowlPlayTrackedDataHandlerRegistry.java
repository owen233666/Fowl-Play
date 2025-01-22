package aqario.fowlplay.common.entity.data;

import aqario.fowlplay.common.entity.DuckVariant;
import aqario.fowlplay.common.entity.SparrowVariant;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Uuids;

import java.util.List;
import java.util.UUID;

public final class FowlPlayTrackedDataHandlerRegistry {
    public static final TrackedDataHandler<RegistryEntry<DuckVariant>> DUCK_VARIANT = register(
        TrackedDataHandler.create(DuckVariant.PACKET_CODEC)
    );
    public static final TrackedDataHandler<RegistryEntry<SparrowVariant>> SPARROW_VARIANT = register(
        TrackedDataHandler.create(SparrowVariant.PACKET_CODEC)
    );
    public static final TrackedDataHandler<List<UUID>> UUID_LIST = register(
        TrackedDataHandler.create(Uuids.PACKET_CODEC.collect(PacketCodecs.toList()))
    );

    private static <T> TrackedDataHandler<T> register(TrackedDataHandler<T> handler) {
        TrackedDataHandlerRegistry.register(handler);
        return handler;
    }

    public static void init() {
    }
}
