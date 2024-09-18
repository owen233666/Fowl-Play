package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.ai.brain.sensor.FowlPlayMemoryModuleType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.TaskBuilder;
import net.minecraft.entity.ai.brain.task.TaskControl;

import java.util.Optional;

public class ForgetSeenFoodTask {
    public static TaskControl<BirdEntity> create(int radius) {
        return TaskBuilder.task(
            instance -> instance.group(
                    instance.presentMemory(FowlPlayMemoryModuleType.SEES_FOOD),
                    instance.registeredMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM)
                )
                .apply(instance, (seesFood, nearestVisibleWantedItem) -> (world, livingEntity, l) -> {
                    if (!livingEntity.getOffHandStack().isEmpty()) {
                        return false;
                    }
                    else {
                        Optional<ItemEntity> item = instance.getValueOptional(nearestVisibleWantedItem);
                        if (item.isPresent() && item.get().isInRange(livingEntity, radius)) {
                            return false;
                        }
                        else {
                            seesFood.forget();
                            return true;
                        }
                    }
                })
        );
    }
}
