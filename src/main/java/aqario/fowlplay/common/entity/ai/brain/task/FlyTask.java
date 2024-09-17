package aqario.fowlplay.common.entity.ai.brain.task;

import net.minecraft.entity.ReportingTaskControl;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.TaskBuilder;
import net.minecraft.entity.ai.brain.task.TaskControl;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class FlyTask {
    public static TaskControl<PathAwareEntity> create(float speed, int horizontalRange, int verticalRange) {
        return create(speed, (entity) -> findFlyTargetPos(entity, horizontalRange, verticalRange), (entity) -> true);
    }

    @Nullable
    private static Vec3d findFlyTargetPos(PathAwareEntity entity, int horizontalRange, int verticalRange) {
        Vec3d vec3d = entity.getRotationVec(0.0F);
        return NoPenaltySolidTargeting.find(entity, horizontalRange, verticalRange, -2, vec3d.x, vec3d.z, 1.5707963705062866);
    }

    private static ReportingTaskControl<PathAwareEntity> create(float speed, Function<PathAwareEntity, Vec3d> targetGetter, Predicate<PathAwareEntity> predicate) {
        return TaskBuilder.task((instance) -> instance.group(instance.absentMemory(MemoryModuleType.WALK_TARGET)).apply(instance, (memoryAccessor) -> (world, entity, time) -> {
            if (!predicate.test(entity)) {
                return false;
            }
            else {
                Optional<Vec3d> optional = Optional.ofNullable(targetGetter.apply(entity));
                memoryAccessor.remember(optional.map((vec3d) -> new WalkTarget(vec3d, speed, 0)));
                return true;
            }
        }));
    }
}
