package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.ai.brain.TeleportTarget;
import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Unit;
import net.minecraft.util.Uuids;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public final class FowlPlayMemoryModuleType {
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create(
        FowlPlay.ID,
        RegistryKeys.MEMORY_MODULE_TYPE
    );
    public static final RegistrySupplier<MemoryModuleType<List<? extends PassiveEntity>>> NEAREST_VISIBLE_ADULTS = register("nearest_visible_adults");
    public static final RegistrySupplier<MemoryModuleType<Boolean>> SEES_FOOD = register("sees_food", Codec.BOOL);
    public static final RegistrySupplier<MemoryModuleType<Boolean>> CANNOT_PICKUP_FOOD = register("cannot_eat_food", Codec.BOOL);
    public static final RegistrySupplier<MemoryModuleType<Unit>> IS_FLYING = register("is_flying", Unit.CODEC);
    public static final RegistrySupplier<MemoryModuleType<Unit>> IS_AVOIDING = register("is_avoiding", Unit.CODEC);
    public static final RegistrySupplier<MemoryModuleType<TeleportTarget>> TELEPORT_TARGET = register("teleport_target");
    public static final RegistrySupplier<MemoryModuleType<UUID>> RECIPIENT = register("recipient", Uuids.INT_STREAM_CODEC);

    private static <U> RegistrySupplier<MemoryModuleType<U>> register(String id, Codec<U> codec) {
        return MEMORY_MODULE_TYPES.register(id, () -> new MemoryModuleType<>(Optional.of(codec)));
    }

    private static <U> RegistrySupplier<MemoryModuleType<U>> register(String id) {
        return MEMORY_MODULE_TYPES.register(id, () -> new MemoryModuleType<>(Optional.empty()));
    }

    public static void init() {
        MEMORY_MODULE_TYPES.register();
    }
}
