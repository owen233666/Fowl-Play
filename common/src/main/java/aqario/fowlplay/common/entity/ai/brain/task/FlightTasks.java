package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.util.MemoryList;
import net.minecraft.entity.ai.brain.MemoryModuleType;

/**
 * A collection of tasks that control the flying behavior of birds.
 */
public class FlightTasks {
    public static <E extends FlyingBirdEntity> OneShotTask<E> startFlying() {
        return new OneShotTask<>(
            (bird, brain) -> {
                if(bird.canStartFlying()) {
                    bird.startFlying();
                    return true;
                }
                return false;
            }
        );
    }

    public static <E extends FlyingBirdEntity> OneShotTask<E> stopFlying() {
        return new OneShotTask<>(
            MemoryList.create(1)
                .registered(MemoryModuleType.WALK_TARGET),
            (bird, brain) -> {
                bird.stopFlying();
                return true;
            }
        );
    }

    public static <E extends FlyingBirdEntity> OneShotTask<E> stopFalling() {
        return new OneShotTask<>(
            (bird, brain) -> {
                if(bird.fallDistance > 1 && bird.canStartFlying()) {
                    bird.startFlying();
                    return true;
                }
                return false;
            }
        );
    }
}
