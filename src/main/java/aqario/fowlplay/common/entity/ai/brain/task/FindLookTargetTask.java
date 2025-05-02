package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiPredicate;

public class FindLookTargetTask {
    public static SingleTickBehaviour<BirdEntity> create(SpawnGroup spawnGroup, float maxDistance) {
        return create((entity, target) -> spawnGroup.equals(target.getType().getSpawnGroup()), maxDistance);
    }

    public static SingleTickBehaviour<BirdEntity> create(EntityType<?> type, float maxDistance) {
        return create((entity, target) -> type.equals(target.getType()), maxDistance);
    }

    public static SingleTickBehaviour<BirdEntity> create(float maxDistance) {
        return create((entity, target) -> true, maxDistance);
    }

    public static SingleTickBehaviour<BirdEntity> create(BiPredicate<BirdEntity, LivingEntity> predicate, float maxDistance) {
        float maxSquaredDistance = maxDistance * maxDistance;
        return new SingleTickBehaviour<>(
            List.of(
                Pair.of(MemoryModuleType.LOOK_TARGET, MemoryModuleState.VALUE_ABSENT),
                Pair.of(MemoryModuleType.VISIBLE_MOBS, MemoryModuleState.VALUE_PRESENT)
            ),
            (bird, brain) -> {
                Optional<LivingEntity> targetEntity = Objects.requireNonNull(BrainUtils.getMemory(brain, MemoryModuleType.VISIBLE_MOBS))
                    .findFirst(target ->
                        predicate.test(bird, target)
                            && target.squaredDistanceTo(bird) <= maxSquaredDistance
                            && !bird.hasPassenger(target)
                    );
                if (targetEntity.isEmpty()) {
                    return false;
                }
                BrainUtils.setMemory(brain, MemoryModuleType.LOOK_TARGET, new EntityLookTarget(targetEntity.get(), true));
                return true;
            }
        );
    }
}
