package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.entity.ai.brain.FowlPlayMemoryModuleType;
import aqario.fowlplay.common.entity.ai.brain.VisibleMobsCache;
import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class NearbyLivingEntitiesSensor<T extends LivingEntity> extends Sensor<T> {
    @Override
    protected void sense(ServerWorld world, T entity) {
        Box box = entity.getBoundingBox().expand(this.horizontalRadius(), this.verticalRadius(), this.horizontalRadius());
        List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, box, living -> living != entity && living.isAlive());
        list.sort(Comparator.comparingDouble(entity::squaredDistanceTo));
        Brain<?> brain = entity.getBrain();
        brain.remember(MemoryModuleType.MOBS, list);
        brain.remember(FowlPlayMemoryModuleType.VISIBLE_MOBS, new VisibleMobsCache(entity, list));
    }

    protected int horizontalRadius() {
        return 32;
    }

    protected int verticalRadius() {
        return 32;
    }

    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return ImmutableSet.of(MemoryModuleType.MOBS, FowlPlayMemoryModuleType.VISIBLE_MOBS);
    }
}
