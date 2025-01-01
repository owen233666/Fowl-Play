package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.TrustingBirdEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.TaskBuilder;
import net.minecraft.entity.ai.brain.task.TaskControl;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;

import java.util.Optional;
import java.util.function.Predicate;

public class AvoidTargetTask {
    public static <E extends BirdEntity> TaskControl<E> locate(int radius) {
        return locate(bird -> true, radius);
    }

    public static <E extends BirdEntity> TaskControl<E> locate(Predicate<E> predicate, int radius) {
        return TaskBuilder.task(
            instance -> instance.group(
                    instance.presentMemory(MemoryModuleType.VISIBLE_MOBS),
                    instance.absentMemory(MemoryModuleType.AVOID_TARGET)
                )
                .apply(instance, (visibleMobs, avoidTarget) -> (world, entity, time) -> {
                    if (!predicate.test(entity)) {
                        return false;
                    }
                    Optional<LivingEntity> avoidingEntity = instance.getValue(visibleMobs).stream(entity::shouldAvoid).findFirst();
                    if (avoidingEntity.isEmpty()) {
                        return false;
                    }
                    if (!shouldAvoid(entity, avoidingEntity.get(), radius)) {
                        return false;
                    }
                    avoidTarget.remember(avoidingEntity.get());
                    return true;
                })
        );
    }

    public static <E extends BirdEntity> TaskControl<E> forget(int radius) {
        return forget(bird -> true, radius);
    }

    public static <E extends BirdEntity> TaskControl<E> forget(Predicate<E> predicate, int radius) {
        return TaskBuilder.task(
            instance -> instance.group(
                    instance.presentMemory(MemoryModuleType.AVOID_TARGET)
                )
                .apply(instance, (avoidTarget) -> (world, entity, time) -> {
                    if (!predicate.test(entity)) {
                        return false;
                    }
                    LivingEntity target = instance.getValue(avoidTarget);
                    if (shouldAvoid(entity, target, radius)) {
                        return false;
                    }
                    avoidTarget.forget();
                    return true;
                })
        );
    }

    private static boolean shouldAvoid(BirdEntity entity, LivingEntity target, int radius) {
        if (!entity.isInRange(target, radius)) {
            return false;
        }
        if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(target)) {
            return false;
        }
        if (target instanceof PlayerEntity player) {
            return !(entity instanceof TrustingBirdEntity trusting) || !trusting.trusts(player);
        }
        return true;
    }
}
