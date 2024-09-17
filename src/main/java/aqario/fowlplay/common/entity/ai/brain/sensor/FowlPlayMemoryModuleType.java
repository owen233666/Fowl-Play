package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.FowlPlay;
import com.mojang.serialization.Codec;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class FowlPlayMemoryModuleType {
    public static final MemoryModuleType<LivingEntity> NEAREST_VISIBLE_PENGUIN = register("nearest_visible_penguin");
    public static final MemoryModuleType<Boolean> SEES_FOOD = register("sees_food", Codec.BOOL);
    public static final MemoryModuleType<Boolean> CANNOT_EAT_FOOD = register("cannot_eat_food", Codec.BOOL);

    private static <U> MemoryModuleType<U> register(String id, Codec<U> codec) {
        return Registry.register(Registries.MEMORY_MODULE_TYPE, Identifier.ofDefault(id), new MemoryModuleType<>(Optional.of(codec)));
    }

    private static <U> MemoryModuleType<U> register(String id) {
        return Registry.register(Registries.MEMORY_MODULE_TYPE, Identifier.of(FowlPlay.ID, id), new MemoryModuleType<>(Optional.empty()));
    }

    public static void init() {
    }
}
