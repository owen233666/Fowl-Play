package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.entity.PenguinEntity;
import aqario.fowlplay.common.entity.ai.brain.FowlPlayMemoryModuleType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.NearestVisibleLivingEntitySensor;

public class NearestVisiblePenguinSensor extends NearestVisibleLivingEntitySensor {
    @Override
    protected boolean matches(LivingEntity entity, LivingEntity target) {
        return entity instanceof PenguinEntity && target instanceof PenguinEntity && target.distanceTo(entity) < 32;
    }

    @Override
    protected MemoryModuleType<LivingEntity> getOutputMemoryModule() {
        return FowlPlayMemoryModuleType.NEAREST_VISIBLE_PENGUIN;
    }
}
