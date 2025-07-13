package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.ai.brain.TeleportTarget;
import aqario.fowlplay.core.platform.PlatformHelper;
import com.mojang.serialization.Codec;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.util.Unit;
import net.minecraft.util.Uuids;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public final class FowlPlayMemoryModuleType {
    public static final Supplier<MemoryModuleType<List<? extends PassiveEntity>>> NEAREST_VISIBLE_ADULTS = register("nearest_visible_adults");
    public static final Supplier<MemoryModuleType<Unit>> SEES_FOOD = register("sees_food", Unit.CODEC);
    public static final Supplier<MemoryModuleType<Boolean>> CANNOT_PICKUP_FOOD = register("cannot_eat_food", Codec.BOOL);
    public static final Supplier<MemoryModuleType<Unit>> IS_FLYING = register("is_flying", Unit.CODEC);
    public static final Supplier<MemoryModuleType<Unit>> IS_AVOIDING = register("is_avoiding", Unit.CODEC);
    public static final Supplier<MemoryModuleType<TeleportTarget>> TELEPORT_TARGET = register("teleport_target");
    public static final Supplier<MemoryModuleType<UUID>> RECIPIENT = register("recipient", Uuids.INT_STREAM_CODEC);

    private static <U> Supplier<MemoryModuleType<U>> register(String id, Codec<U> codec) {
        return PlatformHelper.registerMemoryModuleType(id, () -> new MemoryModuleType<>(Optional.of(codec)));
    }

    private static <U> Supplier<MemoryModuleType<U>> register(String id) {
        return PlatformHelper.registerMemoryModuleType(id, () -> new MemoryModuleType<>(Optional.empty()));
    }

    public static void init() {
    }
}
