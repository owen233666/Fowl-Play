package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.ai.brain.FowlPlayMemoryModuleType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.TaskBuilder;
import net.minecraft.entity.ai.brain.task.TaskControl;

import java.util.function.Predicate;

public class LocateFoodTask {
    public static <E extends BirdEntity> TaskControl<E> run() {
        return run(bird -> true);
    }

    public static <E extends BirdEntity> TaskControl<E> run(Predicate<E> predicate) {
        return TaskBuilder.task(
            builder -> builder.group(
                    builder.presentMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM),
                    builder.absentMemory(FowlPlayMemoryModuleType.SEES_FOOD),
                    builder.absentMemory(FowlPlayMemoryModuleType.CANNOT_PICKUP_FOOD)
                )
                .apply(builder, (nearestVisibleWantedItem, seesFood, cannotEatFood) -> (world, entity, time) -> {
                    ItemEntity item = builder.getValue(nearestVisibleWantedItem);
                    if (!entity.getFood().test(item.getStack())) {
                        return false;
                    }
                    if (!predicate.test(entity)) {
                        return false;
                    }
                    seesFood.remember(true);
                    return true;
                })
        );
    }
}
