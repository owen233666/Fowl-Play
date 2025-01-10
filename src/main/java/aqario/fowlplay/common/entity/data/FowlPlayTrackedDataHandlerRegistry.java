package aqario.fowlplay.common.entity.data;

import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Uuids;

import java.util.List;
import java.util.UUID;

public final class FowlPlayTrackedDataHandlerRegistry {
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
