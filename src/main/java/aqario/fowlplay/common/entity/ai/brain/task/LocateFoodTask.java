package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.ai.brain.GullBrain;
import aqario.fowlplay.common.entity.ai.brain.sensor.FowlPlayMemoryModuleType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.TaskBuilder;
import net.minecraft.entity.ai.brain.task.TaskControl;

public class LocateFoodTask {
    public static TaskControl<BirdEntity> run() {
        return TaskBuilder.task(
            builder -> builder.group(
                    builder.presentMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM),
                    builder.absentMemory(FowlPlayMemoryModuleType.HAS_FOOD),
                    builder.absentMemory(FowlPlayMemoryModuleType.CANNOT_EAT_FOOD)
                )
                .apply(builder, (nearestVisibleWantedItem, hasFood, cannotEatFood) -> (world, entity, time) -> {
                    ItemEntity itemEntity = builder.getValue(nearestVisibleWantedItem);
                    if (!GullBrain.getFood().test(itemEntity.getStack())) {
                        return false;
                    }
                    else {
                        hasFood.remember(true);
                        return true;
                    }
                })
        );
    }
}
