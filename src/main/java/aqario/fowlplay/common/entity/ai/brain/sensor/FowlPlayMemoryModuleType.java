package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.PenguinEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Optional;

public class FowlPlayMemoryModuleType {
    public static final MemoryModuleType<LivingEntity> NEAREST_VISIBLE_PENGUIN = register("nearest_visible_penguin");

    private static <U> MemoryModuleType<U> register(String id) {
        return Registry.register(Registry.MEMORY_MODULE_TYPE, new Identifier(FowlPlay.ID, id), new MemoryModuleType<>(Optional.empty()));
    }
}
