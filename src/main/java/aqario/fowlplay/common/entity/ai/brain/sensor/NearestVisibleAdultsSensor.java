package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.entity.ai.brain.FowlPlayMemoryModuleType;
import aqario.fowlplay.common.entity.ai.brain.VisibleMobsCache;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.List;
import java.util.Set;

public class NearestVisibleAdultsSensor extends Sensor<PassiveEntity> {
    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return ImmutableSet.of(FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS);
    }

    @Override
    protected void sense(ServerWorld world, PassiveEntity entity) {
        Brain<?> brain = entity.getBrain();
        List<PassiveEntity> nearbyVisibleAdults = Lists.newArrayList();
        VisibleMobsCache visibleMobs = brain.getOptionalRegisteredMemory(FowlPlayMemoryModuleType.VISIBLE_MOBS)
            .orElse(VisibleMobsCache.getEmpty());

        visibleMobs.stream(living -> living.getType() == entity.getType() && !entity.isBaby())
            .forEach(living -> nearbyVisibleAdults.add((PassiveEntity) living));

        brain.remember(FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS, nearbyVisibleAdults);
    }
}
