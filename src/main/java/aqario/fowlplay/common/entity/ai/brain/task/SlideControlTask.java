package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.PenguinEntity;
import aqario.fowlplay.common.tags.FowlPlayBlockTags;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.TaskBuilder;
import net.minecraft.entity.ai.brain.task.TaskControl;

/**
 * A collection of tasks that control the sliding behavior of penguins.
 */
public class SlideControlTask {
    public static <E extends PenguinEntity> TaskControl<E> startSliding() {
        return TaskBuilder.task(
            instance -> instance.group(
                    instance.registeredMemory(MemoryModuleType.WALK_TARGET)
                )
                .apply(
                    instance,
                    (walkTarget) -> (world, penguin, l) -> {
                        if (!penguin.isSliding()) {
                            penguin.startSliding();
                            walkTarget.forget();
                            return true;
                        }
                        return false;
                    }
                )
        );
    }

    public static <E extends PenguinEntity> TaskControl<E> stopSliding() {
        return TaskBuilder.task(
            instance -> instance.group(
                    instance.registeredMemory(MemoryModuleType.WALK_TARGET)
                )
                .apply(
                    instance,
                    (walkTarget) -> (world, penguin, l) -> {
                        if (penguin.isSliding()) {
                            penguin.stopSliding();
                            walkTarget.forget();
                            return true;
                        }
                        return false;
                    }
                )
        );
    }

    public static <E extends PenguinEntity> TaskControl<E> toggleSliding(int seconds) {
        return TaskBuilder.task(
            instance -> instance.group(
                    instance.registeredMemory(MemoryModuleType.WALK_TARGET)
                )
                .apply(
                    instance,
                    (walkTarget) -> (world, penguin, l) -> {
                        if (penguin.isInsideWaterOrBubbleColumn()
                            || penguin.getAnimationTicks() < (long) seconds * 20
                            || penguin.hasPassengers()
                            || !penguin.isOnGround()
                            || (!penguin.isSliding() && !world.getBlockState(penguin.getBlockPos()).isIn(FowlPlayBlockTags.PENGUINS_SLIDE_ON))
                        ) {
                            return false;
                        }
                        if (penguin.isSliding()) {
                            penguin.stopSliding();
                        }
                        else {
                            penguin.startSliding();
                        }
                        walkTarget.forget();
                        return true;
                    }
                )
        );
    }
}
