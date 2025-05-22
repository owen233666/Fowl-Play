package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.PenguinEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.tslat.smartbrainlib.object.MemoryTest;

/**
 * A collection of tasks that control the sliding behavior of penguins.
 */
public class SlideControlTask {
    public static <E extends PenguinEntity> SingleTickBehaviour<E> startSliding() {
        return new SingleTickBehaviour<>(
            MemoryTest.builder(1)
                .usesMemory(MemoryModuleType.WALK_TARGET),
            (bird, brain) -> {
                if (!bird.isSliding() && bird.canStartSliding()) {
                    bird.startSliding();
                    return true;
                }
                return false;
            }
        );
    }

    public static <E extends PenguinEntity> SingleTickBehaviour<E> stopSliding() {
        return new SingleTickBehaviour<>(
            MemoryTest.builder(1)
                .usesMemory(MemoryModuleType.WALK_TARGET),
            (bird, brain) -> {
                if (bird.isSliding()) {
                    bird.stopSliding();
                    return true;
                }
                return false;
            }
        );
    }

    public static <E extends PenguinEntity> SingleTickBehaviour<E> toggleSliding(int seconds) {
        return new SingleTickBehaviour<>(
            MemoryTest.builder(1)
                .usesMemory(MemoryModuleType.WALK_TARGET),
            (bird, brain) -> {
                if ((!bird.canStartSliding() && !bird.isSliding()) || bird.getLastPoseTickDelta() < (long) seconds * 20) {
                    return false;
                }
                if (bird.isSliding()) {
                    bird.stopSliding();
                }
                else {
                    bird.startSliding();
                }
                return true;
            }
        );
    }
}
