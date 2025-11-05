package aqario.fowlplay.common.entity.ai.pathing;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.util.CuboidRadius;
import aqario.fowlplay.common.util.TargetingUtil;
import net.minecraft.entity.ai.FuzzyPositions;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.NavigationConditions;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.ToDoubleFunction;

/**
 * Similar to {@link FuzzyTargeting} but only for flying birds.
 */
public class FlightTargeting {
    @NotNull
    public static Optional<Vec3d> findWaterOrGround(FlyingBirdEntity entity, CuboidRadius<Integer> waterRange, CuboidRadius<Integer> groundRange) {
        return findWater(entity, waterRange)
            .or(() -> findGround(entity, groundRange));
    }

    @NotNull
    public static Optional<Vec3d> findWater(FlyingBirdEntity entity, CuboidRadius<Integer> range) {
        return Optional.ofNullable(FuzzyPositions.guessBest(() -> {
                BlockPos pos = FuzzyPositions.localFuzz(entity.getRandom(), range.horizontal(), range.vertical(), -2, 0, 0, Math.PI * 3 / 2);
                return pos == null ? null : TargetingUtil.validateWater(entity, pos);
            }, pos -> 0))
            .or(() -> fallback(entity, range));
    }

    @NotNull
    public static Optional<Vec3d> findNonAir(FlyingBirdEntity entity, CuboidRadius<Integer> range) {
        return Optional.ofNullable(FuzzyPositions.guessBest(() -> {
                BlockPos pos = FuzzyPositions.localFuzz(entity.getRandom(), range.horizontal(), range.vertical(), -2, 0, 0, Math.PI * 3 / 2);
                return pos == null ? null : TargetingUtil.validateNonAir(entity, pos);
            }, pos -> 0))
            .or(() -> fallback(entity, range));
    }

    @NotNull
    public static Optional<Vec3d> findPerchOrGround(FlyingBirdEntity entity, CuboidRadius<Integer> perchRange, CuboidRadius<Integer> groundRange) {
        return findPerch(entity, perchRange)
            .or(() -> findGround(entity, groundRange));
    }

    @NotNull
    public static Optional<Vec3d> findGround(FlyingBirdEntity entity, CuboidRadius<Integer> range) {
        return Optional.ofNullable(FuzzyPositions.guessBest(() -> {
                BlockPos pos = FuzzyPositions.localFuzz(entity.getRandom(), range.horizontal(), range.vertical(), -2, 0, 0, Math.PI * 3 / 2);
                return pos == null ? null : TargetingUtil.validateGround(entity, pos);
            }, pos -> 0))
            .or(() -> fallback(entity, range));
    }

    @NotNull
    public static Optional<Vec3d> findPerch(FlyingBirdEntity entity, CuboidRadius<Integer> range) {
        return findPerch(entity, range, pos -> 0);
    }

    @NotNull
    public static Optional<Vec3d> findPerch(FlyingBirdEntity entity, CuboidRadius<Integer> range, ToDoubleFunction<BlockPos> scorer) {
        boolean posTargetInRange = NavigationConditions.isPositionTargetInRange(entity, range.horizontal());
        Vec3d direction = entity.getRotationVec(1);
        return Optional.ofNullable(FuzzyPositions.guessBest(() -> {
                BlockPos blockPos = FuzzyPositions.localFuzz(entity.getRandom(), range.horizontal(), range.vertical(), 0, direction.x, direction.z, Math.PI * 3 / 2);
                if(blockPos == null) {
                    return null;
                }
                BlockPos blockPos2 = TargetingUtil.towardTarget(entity, range.horizontal(), posTargetInRange, blockPos);
                if(blockPos2 == null) {
                    return null;
                }
                return TargetingUtil.validatePerch(entity, blockPos2);
            }, scorer))
            .or(() -> fallback(entity, range));
    }

    @NotNull
    public static Optional<Vec3d> findRandom(FlyingBirdEntity entity, CuboidRadius<Integer> range) {
        return findRandom(entity, range, entity::getFlyingPathfindingFavor);
    }

    @NotNull
    public static Optional<Vec3d> findRandom(FlyingBirdEntity entity, CuboidRadius<Integer> range, ToDoubleFunction<BlockPos> scorer) {
        boolean posTargetInRange = NavigationConditions.isPositionTargetInRange(entity, range.horizontal());
        // the entity's path should be in the same direction as its look vector
        Vec3d direction = entity.getRotationVec(1);
        // the angle within which the target position should be in regard to the entity's look vector
        final double angle = 15.0;
        return Optional.ofNullable(FuzzyPositions.guessBest(() -> {
            BlockPos blockPos = FuzzyPositions.localFuzz(entity.getRandom(), range.horizontal(), range.vertical(), 0, direction.x, direction.z, angle * (Math.PI / 180));
            if(blockPos == null) {
                return null;
            }
            BlockPos blockPos2 = TargetingUtil.towardTarget(entity, range.horizontal(), posTargetInRange, blockPos);
            if(blockPos2 == null) {
                return null;
            }
            return TargetingUtil.isPosWithinViewAngle(entity, blockPos2, angle * (Math.PI / 180)) ? TargetingUtil.validateAny(entity, blockPos2) : null;
        }, scorer));
    }

    @NotNull
    public static Optional<Vec3d> fallback(FlyingBirdEntity entity, CuboidRadius<Integer> range) {
        return entity.isFlying()
            ? findRandom(entity, range)
            : Optional.empty();
    }

    @Nullable
    public static Vec3d findTo(FlyingBirdEntity entity, int horizontalRange, int verticalRange, Vec3d end) {
        Vec3d vec3d = end.subtract(entity.getX(), entity.getY(), entity.getZ());
        boolean bl = NavigationConditions.isPositionTargetInRange(entity, horizontalRange);
        return findValid(entity, horizontalRange, verticalRange, vec3d, bl);
    }

    @Nullable
    public static Vec3d findFrom(FlyingBirdEntity entity, int horizontalRange, int verticalRange, Vec3d start) {
        Vec3d vec3d = entity.getPos().subtract(start);
        boolean bl = NavigationConditions.isPositionTargetInRange(entity, horizontalRange);
        return findValid(entity, horizontalRange, verticalRange, vec3d, bl);
    }

    @Nullable
    private static Vec3d findValid(FlyingBirdEntity entity, int horizontalRange, int verticalRange, Vec3d direction, boolean posTargetInRange) {
        return FuzzyPositions.guessBestPathTarget(entity, () -> {
            BlockPos blockPos = FuzzyPositions.localFuzz(entity.getRandom(), horizontalRange, verticalRange, 0, direction.x, direction.z, (float) (Math.PI / 2));
            if(blockPos == null) {
                return null;
            }
            BlockPos blockPos2 = TargetingUtil.towardTarget(entity, horizontalRange, posTargetInRange, blockPos);
            return blockPos2 == null ? null : TargetingUtil.validateAny(entity, blockPos2);
        });
    }
}
