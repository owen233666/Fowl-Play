package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.util.Birds;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.InvalidateMemory;

import java.util.function.Predicate;

/**
 * A collection of preconfigured tasks and task lists for ease of use.
 */
public class CompositeTasks {
    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> setWaterWalkTarget() {
        return new SetWaterWalkTargetTask<E>()
            .setRadius(32, 24);
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> setGroundWalkTarget() {
        return new SetNonAirWalkTargetTask<E>()
            .setRadius(32, 16);
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> setWaterRestTarget() {
        return new SetWaterWalkTargetTask<E>()
            .setRadius(64, 32)
            .startCondition(Predicate.not(Entity::isInsideWaterOrBubbleColumn));
    }

    public static <E extends BirdEntity> ExtendedBehaviour<E> setNearestFoodWalkTarget() {
        return new SetItemWalkTargetTask<E>()
            .radius(Birds.ITEM_PICK_UP_RANGE)
            .speedModifier(Birds.FAST_SPEED);
    }

    public static <E extends BirdEntity> ExtendedBehaviour<E> setAvoidEntityWalkTarget() {
        return new SetWalkTargetAwayFromTask<E, LivingEntity>(MemoryModuleType.AVOID_TARGET, Entity::getPos)
            .speedModifier(Birds.FAST_SPEED);
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> idleIfNotFlying() {
        return new Idle<E>()
            .noTimeout()
            .startCondition(entity -> !entity.isFlying() && !Birds.isPerched(entity))
            .stopIf(entity -> entity.isFlying() || Birds.isPerched(entity));
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> idleIfPerched() {
        return new Idle<E>()
            .noTimeout()
            .startCondition(Birds::isPerched)
            .stopIf(Predicate.not(Birds::isPerched));
    }

    public static <E extends BirdEntity> ExtendedBehaviour<E> idleIfInWater() {
        return new Idle<E>()
            .noTimeout()
            .startCondition(Entity::isInsideWaterOrBubbleColumn)
            .stopIf(Predicate.not(Entity::isInsideWaterOrBubbleColumn));
    }

    public static <E extends BirdEntity> ExtendedBehaviour<E> forgetUnderwaterAttackTarget() {
        return new InvalidateMemory<E, LivingEntity>(MemoryModuleType.ATTACK_TARGET)
            .invalidateIf(((entity, target) ->
                entity.isInsideWaterOrBubbleColumn() && target.isSubmergedInWater() && target.getPos().y < entity.getPos().y
            ));
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> tryPerch() {
        return new OneRandomBehaviour<>(
            Pair.of(
                new OneRandomBehaviour<E>(
                    new LookAroundTask<>(),
                    new Idle<>()
                        .noTimeout()
                )
                    .runForBetween(30, 100)
                    .startCondition(Birds::isPerched)
                    .stopIf(Predicate.not(Birds::isPerched)),
                8
            ),
            Pair.of(
                new SetPerchWalkTargetTask<>(),
                1
            )
        );
    }

    public static <E extends FlyingBirdEntity> ExtendedBehaviour<E> tryForage() {
        return new OneRandomBehaviour<>(
            Pair.of(
                new OneRandomBehaviour<>(
                    new LookAroundTask<>(),
                    new Idle<>()
                        .noTimeout()
                )
                    .runForBetween(30, 100)
                    .startCondition(Entity::isOnGround)
                    .stopIf(Predicate.not(Entity::isOnGround)),
                2
            ),
            Pair.of(
                setGroundWalkTarget(),
                1
            )
        );
    }
}
