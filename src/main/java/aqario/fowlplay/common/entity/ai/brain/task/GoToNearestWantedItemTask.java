package aqario.fowlplay.common.entity.ai.brain.task;

import com.mojang.datafixers.kinds.K1;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryAccessor;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.TaskBuilder;
import net.minecraft.entity.ai.brain.task.TaskControl;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Improved {@link net.minecraft.entity.ai.brain.task.WalkToNearestVisibleWantedItemTask WalkToNearestVisibleWantedItemTask} with a speedGetter
 */
public class GoToNearestWantedItemTask {
    public static <E extends LivingEntity> TaskControl<E> create(Predicate<E> startPredicate, Function<E, Float> entitySpeedGetter, boolean requiresWalkTarget, int radius) {
        return TaskBuilder.task(
            instance -> {
                TaskBuilder<E, ? extends MemoryAccessor<? extends K1, WalkTarget>> taskBuilder = requiresWalkTarget
                    ? instance.registeredMemory(MemoryModuleType.WALK_TARGET)
                    : instance.absentMemory(MemoryModuleType.WALK_TARGET);
                return instance.group(
                        instance.registeredMemory(MemoryModuleType.LOOK_TARGET),
                        taskBuilder,
                        instance.presentMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM),
                        instance.registeredMemory(MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS)
                    )
                    .apply(
                        instance,
                        (lookTarget, walkTarget, nearestWantedItem, pickupCooldownTicks) -> (world, entity, l) -> {
                            ItemEntity itemEntity = instance.getValue(nearestWantedItem);
                            if (instance.getValueOptional(pickupCooldownTicks).isEmpty()
                                && startPredicate.test(entity)
                                && itemEntity.isInRange(entity, radius)
                                && entity.getWorld().getWorldBorder().contains(itemEntity.getBlockPos())) {
                                WalkTarget newWalkTarget = new WalkTarget(new EntityLookTarget(itemEntity, false), entitySpeedGetter.apply(entity), 0);
                                lookTarget.remember(new EntityLookTarget(itemEntity, true));
                                walkTarget.remember(newWalkTarget);
                                return true;
                            }
                            return false;
                        }
                    );
            }
        );
    }
}