package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.util.Birds;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.Entity;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;

import java.util.function.Predicate;

/**
 * A collection of preconfigured group behaviours for ease of use.
 */
public class CompositeBehaviours {
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
                CustomBehaviours.setGroundWalkTarget(),
                1
            )
        );
    }
}
