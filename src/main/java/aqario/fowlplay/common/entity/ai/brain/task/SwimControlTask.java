package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.TaskBuilder;
import net.minecraft.entity.ai.brain.task.TaskControl;
import net.minecraft.util.Unit;

/**
 * A collection of tasks that control the swimming behavior of birds.
 */
public class SwimControlTask {
    public static <E extends BirdEntity> TaskControl<E> startSwimming() {
        return TaskBuilder.task(
            instance -> instance.group(
                    instance.absentMemory(MemoryModuleType.IS_IN_WATER),
                    instance.registeredMemory(MemoryModuleType.WALK_TARGET)
                )
                .apply(
                    instance,
                    (swimming, walkTarget) -> (world, bird, l) -> {
                        if (bird.isInsideWaterOrBubbleColumn()) {
                            swimming.remember(Unit.INSTANCE);
                            walkTarget.forget();
                            return true;
                        }
                        return false;
                    }
                )
        );
    }

    public static <E extends BirdEntity> TaskControl<E> stopSwimming() {
        return TaskBuilder.task(
            instance -> instance.group(
                    instance.presentMemory(MemoryModuleType.IS_IN_WATER),
                    instance.registeredMemory(MemoryModuleType.WALK_TARGET)
                )
                .apply(
                    instance,
                    (swimming, walkTarget) -> (world, bird, l) -> {
                        if (!bird.isInsideWaterOrBubbleColumn()) {
                            swimming.forget();
                            walkTarget.forget();
                            return true;
                        }
                        return false;
                    }
                )
        );
    }
}
