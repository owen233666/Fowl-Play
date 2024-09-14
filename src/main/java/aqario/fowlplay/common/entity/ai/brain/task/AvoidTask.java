package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ReportingTaskControl;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.TaskBuilder;
import net.minecraft.util.math.int_provider.UniformIntProvider;

import java.util.function.Function;

public class AvoidTask {
    public static <T extends LivingEntity> ReportingTaskControl<BirdEntity> create(UniformIntProvider executionRange, Class<T> fleeFromType, UniformIntProvider speedRange) {
        return create(executionRange, fleeFromType, livingEntity -> speedRange);
    }

    public static <T extends LivingEntity> ReportingTaskControl<BirdEntity> create(UniformIntProvider executionRange, Class<T> fleeFromType, Function<LivingEntity, UniformIntProvider> speedGetter) {
        return TaskBuilder.task(
            instance -> instance.group(
                    instance.presentMemory(MemoryModuleType.AVOID_TARGET),
                    instance.registeredMemory(MemoryModuleType.LOOK_TARGET),
                    instance.absentMemory(MemoryModuleType.WALK_TARGET)
                )
                .apply(
                    instance,
                    instance.constant(
                        () -> "[Flee " + fleeFromType + "]", (avoidTarget, lookTarget, walkTarget) -> (world, passiveEntity, l) -> {
                            LivingEntity entity = instance.getValue(avoidTarget);
                            if (passiveEntity.getClass() != fleeFromType) {
                                return false;
                            }
                            if (passiveEntity.isInRange(entity, executionRange.getMax() + 1)
                                && !passiveEntity.isInRange(entity, executionRange.getMin())) {
                                WalkTarget target = new WalkTarget(
                                    new EntityLookTarget(entity, false), speedGetter.apply(passiveEntity).getMin(), executionRange.getMax() + 1
                                );
                                lookTarget.remember(new EntityLookTarget(entity, true));
                                walkTarget.remember(target);
                                return true;
                            }
                            else if (passiveEntity.isInRange(entity, executionRange.getMin())) {
                                WalkTarget target = new WalkTarget(
                                    new EntityLookTarget(entity, false), speedGetter.apply(passiveEntity).getMax(), executionRange.getMax() + 1
                                );
                                lookTarget.remember(new EntityLookTarget(entity, true));
                                walkTarget.remember(target);
                                return true;
                            }
                            else {
                                return false;
                            }
                        }
                    )
                )
        );
    }
}
