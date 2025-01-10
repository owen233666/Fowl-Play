package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.TrustingBirdEntity;
import aqario.fowlplay.common.entity.ai.brain.FowlPlayMemoryModuleType;
import aqario.fowlplay.common.entity.ai.brain.VisibleMobsCache;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;

import java.util.Optional;
import java.util.Set;

public class AvoidTargetSensor extends Sensor<BirdEntity> {
    @Override
    protected void sense(ServerWorld world, BirdEntity bird) {
        Brain<?> brain = bird.getBrain();
        Optional<VisibleMobsCache> visibleMobs = brain.getOptionalRegisteredMemory(FowlPlayMemoryModuleType.VISIBLE_MOBS);
        if (visibleMobs.isEmpty()) {
            brain.forget(MemoryModuleType.AVOID_TARGET);
            return;
        }
        Optional<LivingEntity> avoidTarget = visibleMobs.get().stream(entity -> shouldAvoid(bird, entity)).findFirst();

        if (avoidTarget.isEmpty()) {
            brain.forget(MemoryModuleType.AVOID_TARGET);
            return;
        }
        brain.remember(MemoryModuleType.AVOID_TARGET, avoidTarget.get());
    }

    private static boolean shouldAvoid(BirdEntity bird, LivingEntity target) {
        if (!bird.shouldAvoid(target)) {
            return false;
        }
        if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(target)) {
            return false;
        }
        if (target instanceof PlayerEntity player) {
            return !(bird instanceof TrustingBirdEntity trusting) || !trusting.trusts(player);
        }
        Optional<LivingEntity> attackTarget = bird.getBrain().getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET);
        if (attackTarget.isPresent() && attackTarget.get().equals(target)) {
            return false;
        }
        return true;
    }

    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return Set.of(MemoryModuleType.AVOID_TARGET);
    }
}