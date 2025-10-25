package aqario.fowlplay.common.util;

import aqario.fowlplay.core.tags.FowlPlayBlockTags;
import net.minecraft.entity.ai.FuzzyPositions;
import net.minecraft.entity.ai.NavigationConditions;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import org.jetbrains.annotations.Nullable;

public class TargetingUtil {
    @Nullable
    public static BlockPos validateAny(PathAwareEntity entity, BlockPos pos) {
        BlockPos adjustedPos = FuzzyPositions.upWhile(pos, entity.getWorld().getTopY(), currentPos ->
            NavigationConditions.isSolidAt(entity, currentPos)
        );
        if(NavigationConditions.isWaterAt(entity, adjustedPos)
            || NavigationConditions.hasPathfindingPenalty(entity, adjustedPos)
            || NavigationConditions.isInvalidPosition(entity.getNavigation(), adjustedPos)
        ) {
            return null;
        }
        return adjustedPos;
    }

    @Nullable
    public static BlockPos validateWater(PathAwareEntity entity, BlockPos pos) {
        BlockPos adjustedPos = new BlockPos(
            pos.getX(),
            entity.getWorld().getTopY(Heightmap.Type.OCEAN_FLOOR, pos.getX(), pos.getZ()),
            pos.getZ()
        );
        adjustedPos = FuzzyPositions.upWhile(adjustedPos, entity.getWorld().getTopY(), currentPos ->
            NavigationConditions.isWaterAt(entity, currentPos) || NavigationConditions.isSolidAt(entity, currentPos)
        );
        if(!NavigationConditions.isWaterAt(entity, adjustedPos.down())) {
            return null;
        }
        return adjustedPos.down();
    }

    @Nullable
    public static BlockPos validateGround(PathAwareEntity entity, BlockPos pos) {
        BlockPos adjustedPos = new BlockPos(
            pos.getX(),
            entity.getWorld().getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos.getX(), pos.getZ()),
            pos.getZ()
        );
        if(NavigationConditions.isWaterAt(entity, adjustedPos)
            || NavigationConditions.hasPathfindingPenalty(entity, adjustedPos)
            || !TargetingUtil.isPositionGrounded(entity, adjustedPos)
        ) {
            return null;
        }
        return adjustedPos;
    }

    @Nullable
    public static BlockPos validatePerch(PathAwareEntity entity, BlockPos pos) {
        BlockPos adjustedPos = FuzzyPositions.upWhile(pos, entity.getWorld().getTopY(), currentPos ->
            NavigationConditions.isSolidAt(entity, currentPos)
                && !TargetingUtil.isPerch(entity, currentPos)
        );
        if(NavigationConditions.isWaterAt(entity, adjustedPos)
            || NavigationConditions.hasPathfindingPenalty(entity, adjustedPos)
            || !TargetingUtil.isPerch(entity, adjustedPos)
        ) {
            return null;
        }
        return adjustedPos;
    }

    @Nullable
    public static BlockPos towardTarget(PathAwareEntity entity, int horizontalRange, boolean posTargetInRange, BlockPos relativeInRangePos) {
        BlockPos adjustedPos = FuzzyPositions.towardTarget(entity, horizontalRange, entity.getRandom(), relativeInRangePos);
        if(NavigationConditions.isHeightInvalid(adjustedPos, entity)
            || NavigationConditions.isPositionTargetOutOfWalkRange(posTargetInRange, entity, adjustedPos)
        ) {
            return null;
        }
        return adjustedPos;
    }

    public static boolean isPerch(PathAwareEntity entity, BlockPos pos) {
        return entity.getWorld().getBlockState(pos).isIn(FowlPlayBlockTags.PERCHES);
    }

    public static boolean isPositionGrounded(PathAwareEntity entity, BlockPos pos) {
        BlockPos belowPos = pos.down();
        return entity.getWorld().getBlockState(belowPos).isOpaqueFullCube(entity.getWorld(), belowPos);
    }

    // angle is in radians
    public static boolean isWithinAngle(Vec3d normalVec, Vec3d targetVec, double angle) {
        normalVec = normalVec.normalize();
        targetVec = targetVec.normalize();

        // cosine of angle between the two vectors
        float cosVectorAngle = (float) normalVec.dotProduct(targetVec);

        // if cosine of the vectors' angle >= cosine of max angle the target vector is within the angle
        float cosMaxAngle = MathHelper.cos((float) angle);
        return cosVectorAngle >= cosMaxAngle;
    }

    // angle is in radians
    public static boolean isPosWithinViewAngle(PathAwareEntity entity, BlockPos pos, double angle) {
        Vec3d lookVec = entity.getRotationVec(1.0F);

        Vec3d target = Vec3d.ofCenter(pos);
        Vec3d targetVec = target.subtract(entity.getPos());

        return isWithinAngle(lookVec, targetVec, angle);
    }
}
