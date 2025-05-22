package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import aqario.fowlplay.core.FowlPlaySensorType;
import com.google.common.collect.Lists;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.passive.PassiveEntity;
import net.tslat.smartbrainlib.api.core.sensor.EntityFilteringSensor;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;

import java.util.List;
import java.util.function.BiPredicate;

public class NearbyAdultsSensor<E extends PassiveEntity> extends EntityFilteringSensor<List<? extends PassiveEntity>, E> {
    @Override
    public SensorType<? extends ExtendedSensor<?>> type() {
        return FowlPlaySensorType.NEARBY_ADULTS;
    }

    @Override
    protected MemoryModuleType<List<? extends PassiveEntity>> getMemory() {
        return FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS;
    }

    @Override
    protected BiPredicate<LivingEntity, E> predicate() {
        return (target, self) -> target.getType() == self.getType() && !self.isBaby();
    }

    @Override
    protected List<? extends PassiveEntity> findMatches(E self, LivingTargetCache matcher) {
        List<PassiveEntity> nearbyVisibleAdults = Lists.newArrayList();
        matcher.stream(target -> this.predicate().test(target, self))
            .forEach(target -> nearbyVisibleAdults.add((PassiveEntity) target));
        return nearbyVisibleAdults;
    }
}
