package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.TaskBuilder;
import net.minecraft.entity.ai.brain.task.TaskControl;
import net.minecraft.entity.ai.pathing.MobNavigation;

public class AvoidRainTask {
    public static TaskControl<BirdEntity> run() {
        return TaskBuilder.task(
            builder -> builder.group(
                    builder.registeredMemory(MemoryModuleType.WALK_TARGET)
                )
                .apply(builder, (walkTarget) -> (world, entity, time) -> {
                    if (!world.isRaining()) {
                        return false;
                    }
                    ((MobNavigation) entity.getNavigation()).setAvoidSunlight(true);
                    return true;
                })
        );
    }
}
