package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.ai.brain.FowlPlayMemoryModuleType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.TaskBuilder;
import net.minecraft.entity.ai.brain.task.TaskControl;
import net.minecraft.util.Unit;

import java.util.Optional;
import java.util.function.Predicate;

public class AvoidTask {
    public static <E extends BirdEntity> TaskControl<E> run(int radius) {
        return run(bird -> true, radius);
    }

    public static <E extends BirdEntity> TaskControl<E> run(Predicate<E> predicate, int radius) {
        return TaskBuilder.task(
            instance -> instance.group(
                    instance.presentMemory(MemoryModuleType.AVOID_TARGET),
                    instance.absentMemory(FowlPlayMemoryModuleType.IS_AVOIDING)
                )
                .apply(instance, (avoidTarget, isAvoiding) -> (world, entity, time) -> {
                    if (!predicate.test(entity)) {
                        return false;
                    }
                    if (entity.isInRange(instance.getValue(avoidTarget), radius)) {
                        isAvoiding.remember(Unit.INSTANCE);
                        return true;
                    }
                    return false;
                })
        );
    }

    public static <E extends BirdEntity> TaskControl<E> forget(int radius) {
        return forget(bird -> true, radius);
    }

    public static <E extends BirdEntity> TaskControl<E> forget(Predicate<E> predicate, int radius) {
        return TaskBuilder.task(
            instance -> instance.group(
                    instance.registeredMemory(MemoryModuleType.AVOID_TARGET),
                    instance.presentMemory(FowlPlayMemoryModuleType.IS_AVOIDING)
                )
                .apply(instance, (avoidTarget, isAvoiding) -> (world, entity, time) -> {
                    if (!predicate.test(entity)) {
                        return false;
                    }
                    Optional<LivingEntity> target = instance.getValueOptional(avoidTarget);
                    if (target.isPresent() && entity.isInRange(target.get(), radius)) {
                        return false;
                    }
                    isAvoiding.forget();
                    return true;
                })
        );
    }
}
