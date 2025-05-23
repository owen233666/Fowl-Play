package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.util.Unit;
import net.tslat.smartbrainlib.object.MemoryTest;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;
import java.util.function.Predicate;

/**
 * A collection of tasks that control the flying behavior of birds.
 */
public class FlightControlTask {
    public static <E extends FlyingBirdEntity> SingleTickBehaviour<E> startFlying() {
        return startFlying(bird -> true);
    }

    public static <E extends FlyingBirdEntity> SingleTickBehaviour<E> startFlying(Predicate<E> shouldRun) {
        return new SingleTickBehaviour<>(
            MemoryTest.builder(1)
                .noMemory(FowlPlayMemoryModuleType.IS_FLYING.get()),
            (bird, brain) -> {
                if (bird.canStartFlying() && shouldRun.test(bird)) {
                    bird.startFlying();
                    BrainUtils.setMemory(brain, FowlPlayMemoryModuleType.IS_FLYING.get(), Unit.INSTANCE);
                    return true;
                }
                return false;
            }
        );
    }

    public static <E extends FlyingBirdEntity> SingleTickBehaviour<E> stopFlying() {
        return new SingleTickBehaviour<>(
            MemoryTest.builder(2)
                .hasMemory(FowlPlayMemoryModuleType.IS_FLYING.get())
                .usesMemory(MemoryModuleType.WALK_TARGET),
            (bird, brain) -> {
                bird.stopFlying();
                return true;
            }
        );
    }

    public static <E extends FlyingBirdEntity> SingleTickBehaviour<E> stopFalling() {
        return new SingleTickBehaviour<>(
            List.of(
                Pair.of(FowlPlayMemoryModuleType.IS_FLYING.get(), MemoryModuleState.VALUE_ABSENT)
            ),
            (bird, brain) -> {
                if (bird.fallDistance > 1 && bird.canStartFlying()) {
                    bird.startFlying();
                    BrainUtils.setMemory(brain, FowlPlayMemoryModuleType.IS_FLYING.get(), Unit.INSTANCE);
                    return true;
                }
                return false;
            }
        );
    }
}
