package aqario.fowlplay.common.entity.ai.goal;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.goal.FlyOntoTreeGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class BirdWanderGoal extends FlyOntoTreeGoal {
    public BirdWanderGoal(PathAwareEntity pathAwareEntity, double speed) {
        super(pathAwareEntity, speed);
    }

    @Nullable
    @Override
    protected Vec3d getWanderTarget() {
        Vec3d vec3d = null;
        if (this.mob.isTouchingWater()) {
            vec3d = FuzzyTargeting.find(this.mob, 15, 15);
        }

        if (this.mob.getRandom().nextFloat() >= this.probability) {
            vec3d = this.getTreePos();
        }

        return vec3d == null ? this.wander() : vec3d;
    }

    private Vec3d wander() {
        Vec3d vec3d = this.mob.getRotationVec(0.0F);
        Vec3d vec3d2 = AboveGroundTargeting.find(this.mob, 32, 16, vec3d.x, vec3d.z, (float) (Math.PI / 2), 3, 1);
        return vec3d2 != null ? vec3d2 : NoPenaltySolidTargeting.find(this.mob, 12, 4, -2, vec3d.x, vec3d.z, (float) (Math.PI / 2));
    }

    @Nullable
    private Vec3d getTreePos() {
        BlockPos blockPos = this.mob.getBlockPos();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        BlockPos.Mutable mutable2 = new BlockPos.Mutable();

        for(BlockPos blockPos2 : BlockPos.iterate(
            MathHelper.floor(this.mob.getX() - 3.0),
            MathHelper.floor(this.mob.getY() - 6.0),
            MathHelper.floor(this.mob.getZ() - 3.0),
            MathHelper.floor(this.mob.getX() + 3.0),
            MathHelper.floor(this.mob.getY() + 6.0),
            MathHelper.floor(this.mob.getZ() + 3.0)
        )) {
            if (!blockPos.equals(blockPos2)) {
                BlockState blockState = this.mob.world.getBlockState(mutable2.set(blockPos2, Direction.DOWN));
                boolean bl = blockState.getBlock() instanceof LeavesBlock || blockState.isIn(BlockTags.LOGS);
                if (bl && this.mob.world.isAir(blockPos2) && this.mob.world.isAir(mutable.set(blockPos2, Direction.UP))) {
                    return Vec3d.ofBottomCenter(blockPos2);
                }
            }
        }

        return null;
    }
}