package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.TrustingBirdEntity;
import aqario.fowlplay.common.util.Birds;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.Set;

public class AttackedSensor extends Sensor<BirdEntity> {
    public AttackedSensor() {
        super(10);
    }

    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return ImmutableSet.of(
            MemoryModuleType.HURT_BY,
            MemoryModuleType.HURT_BY_ENTITY,
            MemoryModuleType.AVOID_TARGET,
            FowlPlayMemoryModuleType.SEES_FOOD,
            FowlPlayMemoryModuleType.CANNOT_PICKUP_FOOD
        );
    }

    @Override
    protected void sense(ServerWorld world, BirdEntity bird) {
        Brain<?> brain = bird.getBrain();
        DamageSource damageSource = bird.getRecentDamageSource();
        if (damageSource != null) {
            brain.remember(MemoryModuleType.HURT_BY, bird.getRecentDamageSource());
            Entity attacker = damageSource.getAttacker();
            if (attacker instanceof LivingEntity livingEntity) {
                brain.remember(MemoryModuleType.HURT_BY_ENTITY, livingEntity);
                onAttacked(bird, livingEntity);
            }
        }
        else {
            brain.forget(MemoryModuleType.HURT_BY);
        }

        brain.getOptionalRegisteredMemory(MemoryModuleType.HURT_BY_ENTITY).ifPresent(livingEntity -> {
            if (!livingEntity.isAlive() || livingEntity.getWorld() != world) {
                brain.forget(MemoryModuleType.HURT_BY_ENTITY);
            }
        });
    }

    public static <T extends BirdEntity> void onAttacked(T bird, LivingEntity attacker) {
        Brain<?> brain = bird.getBrain();
        brain.forget(FowlPlayMemoryModuleType.SEES_FOOD);
        if (attacker instanceof PlayerEntity player) {
            brain.remember(FowlPlayMemoryModuleType.CANNOT_PICKUP_FOOD, true, Birds.CANNOT_PICKUP_FOOD_TICKS);
            if (bird instanceof TrustingBirdEntity trustingBird && trustingBird.trusts(player)) {
                trustingBird.stopTrusting(player);
            }
        }
        if (attacker.getType() != bird.getType() && !bird.canAttack(attacker)) {
            brain.remember(MemoryModuleType.AVOID_TARGET, attacker, Birds.AVOID_TICKS);
            Birds.alertOthers(bird, attacker);
        }
    }
}
