package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.SingleTickTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;

import java.util.Optional;
import java.util.function.BiPredicate;

public class FindLookTargetTask {
    public static Task<BirdEntity> create(SpawnGroup spawnGroup, float maxDistance) {
        return create((entity, target) -> spawnGroup.equals(target.getType().getSpawnGroup()), maxDistance);
    }

    public static SingleTickTask<BirdEntity> create(EntityType<?> type, float maxDistance) {
        return create((entity, target) -> type.equals(target.getType()), maxDistance);
    }

    public static SingleTickTask<BirdEntity> create(float maxDistance) {
        return create((entity, target) -> true, maxDistance);
    }

    public static SingleTickTask<BirdEntity> create(BiPredicate<BirdEntity, LivingEntity> predicate, float maxDistance) {
        float maxSquaredDistance = maxDistance * maxDistance;
        return TaskTriggerer.task(
            context -> context.group(context.queryMemoryAbsent(MemoryModuleType.LOOK_TARGET), context.queryMemoryValue(MemoryModuleType.VISIBLE_MOBS))
                .apply(
                    context,
                    (lookTarget, visibleMobs) -> (world, entity, time) -> {
                        Optional<LivingEntity> targetEntity = context.getValue(visibleMobs)
                            .findFirst(target ->
                                predicate.test(entity, target)
                                    && target.squaredDistanceTo(entity) <= maxSquaredDistance
                                    && !entity.hasPassenger(target)
                            );
                        if (targetEntity.isEmpty()) {
                            return false;
                        }
                        lookTarget.remember(new EntityLookTarget(targetEntity.get(), true));
                        return true;
                    }
                )
        );
    }
}
