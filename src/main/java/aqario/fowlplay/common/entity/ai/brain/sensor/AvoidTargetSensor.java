package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.TrustingBirdEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.VisibleLivingEntitiesCache;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class AvoidTargetSensor extends Sensor<BirdEntity> {
    @Override
    protected void sense(ServerWorld world, BirdEntity bird) {
        Brain<?> brain = bird.getBrain();
        Optional<VisibleLivingEntitiesCache> visibleMobs = brain.getOptionalMemory(MemoryModuleType.VISIBLE_MOBS);
        if (visibleMobs.isEmpty()) {
            brain.forget(MemoryModuleType.AVOID_TARGET);
            return;
        }
        List<LivingEntity> avoidTarget = new ArrayList<>();

        visibleMobs.get().stream(bird::shouldAvoid)
            .filter(entity -> shouldAvoid(bird, entity))
            .forEach(entity -> {
                if (avoidTarget.isEmpty()) {
                    avoidTarget.add(entity);
                    return;
                }
                if (bird.squaredDistanceTo(entity) < bird.squaredDistanceTo(avoidTarget.getFirst())) {
                    avoidTarget.set(0, entity);
                }
            });

        if (avoidTarget.isEmpty()) {
            brain.forget(MemoryModuleType.AVOID_TARGET);
            return;
        }
        brain.remember(MemoryModuleType.AVOID_TARGET, avoidTarget.getFirst());
    }

    private static boolean shouldAvoid(BirdEntity entity, LivingEntity target) {
        if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(target)) {
            return false;
        }
        if (target instanceof PlayerEntity player) {
            return !(entity instanceof TrustingBirdEntity trusting) || !trusting.trusts(player);
        }
        return true;
    }

    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return Set.of(MemoryModuleType.AVOID_TARGET);
    }
}