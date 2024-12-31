package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.tags.FowlPlayEntityTypeTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ReportingTaskControl;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.TaskBuilder;
import net.minecraft.entity.ai.brain.task.TaskControl;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class FlyTask {
    public static TaskControl<FlyingBirdEntity> create(float speed, int horizontalRange, int verticalRange) {
        return create(speed, (entity) -> findTargetPos(entity, horizontalRange, verticalRange), (entity) -> true);
    }

    @Nullable
    private static Vec3d findTargetPos(FlyingBirdEntity entity, int horizontalRange, int verticalRange) {
        if (entity.getType().isIn(FowlPlayEntityTypeTags.PASSERINES) && entity.getRandom().nextFloat() < 0.8F) {
            return findTreePos(entity);
        }
        return FuzzyTargeting.find(entity, horizontalRange, verticalRange);
    }

    private static ReportingTaskControl<FlyingBirdEntity> create(float speed, Function<FlyingBirdEntity, Vec3d> targetGetter, Predicate<FlyingBirdEntity> predicate) {
        return TaskBuilder.task((instance) -> instance.group(instance.absentMemory(MemoryModuleType.WALK_TARGET)).apply(instance, (walkTarget) -> (world, entity, time) -> {
            if (!predicate.test(entity)) {
                return false;
            }
            Optional<Vec3d> target = Optional.ofNullable(targetGetter.apply(entity));
            walkTarget.remember(target.map((vec3d) -> new WalkTarget(vec3d, speed, 0)));
            return true;
        }));
    }

    @Nullable
    private static Vec3d findTreePos(FlyingBirdEntity entity) {
        BlockPos entityPos = entity.getBlockPos();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        BlockPos.Mutable mutable2 = new BlockPos.Mutable();

        for (BlockPos targetPos : BlockPos.iterate(
            MathHelper.floor(entity.getX() - 12.0),
            MathHelper.floor(entity.getY() - 18.0),
            MathHelper.floor(entity.getZ() - 12.0),
            MathHelper.floor(entity.getX() + 12.0),
            MathHelper.floor(entity.getY() + 18.0),
            MathHelper.floor(entity.getZ() + 12.0)
        )) {
            if (!entityPos.equals(targetPos)) {
                BlockState state = entity.getWorld().getBlockState(mutable2.set(targetPos, Direction.DOWN));
                boolean validBlock = state.isIn(BlockTags.LEAVES) || state.isIn(BlockTags.LOGS);
                if (
                    validBlock && entity.getWorld().isAir(targetPos)
                        && (entity.getBounds().getYLength() <= 1 || entity.getWorld().isAir(mutable.set(targetPos, Direction.UP)))
                ) {
                    return Vec3d.ofBottomCenter(targetPos);
                }
            }
        }

        return null;
    }
}
