package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.util.Birds;
import aqario.fowlplay.core.FowlPlaySensorType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.tslat.smartbrainlib.api.core.sensor.EntityFilteringSensor;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiPredicate;

public class AvoidTargetSensor<E extends BirdEntity> extends EntityFilteringSensor<LivingEntity, E> {
    @Override
    protected MemoryModuleType<LivingEntity> getMemory() {
        return MemoryModuleType.AVOID_TARGET;
    }

    @Override
    public List<MemoryModuleType<?>> memoriesUsed() {
        return List.of(this.getMemory(), MemoryModuleType.VISIBLE_MOBS);
    }

    @Override
    public SensorType<? extends ExtendedSensor<?>> type() {
        return FowlPlaySensorType.AVOID_TARGETS.get();
    }

    @Override
    protected BiPredicate<LivingEntity, E> predicate() {
        return (target, self) -> Birds.shouldAvoid(self, target);
    }

    @Override
    protected @Nullable LivingEntity findMatches(E entity, LivingTargetCache matcher) {
        return matcher.findFirst(target -> predicate().test(target, entity)).orElse(null);
    }
}