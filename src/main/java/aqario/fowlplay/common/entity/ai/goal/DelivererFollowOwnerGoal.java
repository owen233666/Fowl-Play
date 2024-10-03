package aqario.fowlplay.common.entity.ai.goal;

import aqario.fowlplay.common.entity.PigeonEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

import java.util.EnumSet;

public class DelivererFollowOwnerGoal extends Goal {
    private final PigeonEntity deliverer;
    private LivingEntity owner;
    private final WorldView world;
    private final double speed;
    private final EntityNavigation navigation;
    private int updateCountdownTicks;
    private final float maxDistance;
    private final float minDistance;
    private float oldWaterPathfindingPenalty;
    private final boolean leavesAllowed;

    public DelivererFollowOwnerGoal(PigeonEntity bird, double speed, float minDistance, float maxDistance, boolean leavesAllowed) {
        this.deliverer = bird;
        this.world = bird.getWorld();
        this.speed = speed;
        this.navigation = bird.getNavigation();
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.leavesAllowed = leavesAllowed;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        if (!(bird.getNavigation() instanceof MobNavigation) && !(bird.getNavigation() instanceof BirdNavigation)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }

    @Override
    public boolean canStart() {
        LivingEntity livingEntity = this.deliverer.getOwner();
        if (livingEntity == null) {
            return false;
        }
        if (livingEntity.isSpectator()) {
            return false;
        }
        if (this.deliverer.isSitting()) {
            return false;
        }
        if (this.deliverer.squaredDistanceTo(livingEntity) < (double) (this.minDistance * this.minDistance)) {
            return false;
        }
        if (this.deliverer.getRecipientUuid() != null) {
            return false;
        }
        this.owner = livingEntity;
        return true;
    }

    @Override
    public boolean shouldContinue() {
        if (this.deliverer.getRecipientUuid() != null) {
            return false;
        }
        if (this.navigation.isIdle()) {
            return false;
        }
        if (this.deliverer.isSitting()) {
            return false;
        }

        return !(this.deliverer.squaredDistanceTo(this.owner) <= (double) (this.maxDistance * this.maxDistance));
    }

    @Override
    public void start() {
        this.updateCountdownTicks = 0;
        this.oldWaterPathfindingPenalty = this.deliverer.getPenalty(PathNodeType.WATER);
        this.deliverer.addPathfindingPenalty(PathNodeType.WATER, 0.0F);
    }

    @Override
    public void stop() {
        this.owner = null;
        this.navigation.stop();
        this.deliverer.addPathfindingPenalty(PathNodeType.WATER, this.oldWaterPathfindingPenalty);
    }

    @Override
    public void tick() {
        this.deliverer.getLookControl().lookAt(this.owner, 10.0F, (float) this.deliverer.getLookPitchSpeed());
        if (--this.updateCountdownTicks <= 0) {
            this.updateCountdownTicks = this.getTickCount(10);
            if (!this.deliverer.isLeashed() && !this.deliverer.hasVehicle()) {
                if (this.deliverer.squaredDistanceTo(this.owner) >= 144.0) {
                    this.tryTeleport();
                }
                else {
                    this.navigation.startMovingTo(this.owner, this.speed);
                }
            }
        }
    }

    private void tryTeleport() {
        BlockPos blockPos = this.owner.getBlockPos();

        for (int i = 0; i < 10; ++i) {
            int j = this.getRandomInt(-3, 3);
            int k = this.getRandomInt(-1, 1);
            int l = this.getRandomInt(-3, 3);
            boolean bl = this.tryTeleportTo(blockPos.getX() + j, blockPos.getY() + k, blockPos.getZ() + l);
            if (bl) {
                return;
            }
        }
    }

    private boolean tryTeleportTo(int x, int y, int z) {
        if (Math.abs((double) x - this.owner.getX()) < 2.0 && Math.abs((double) z - this.owner.getZ()) < 2.0) {
            return false;
        }
        else if (!this.canTeleportTo(new BlockPos(x, y, z))) {
            return false;
        }
        else {
            this.deliverer.refreshPositionAndAngles((double) x + 0.5, (double) y, (double) z + 0.5, this.deliverer.getYaw(), this.deliverer.getPitch());
            this.navigation.stop();
            return true;
        }
    }

    private boolean canTeleportTo(BlockPos pos) {
        PathNodeType pathNodeType = LandPathNodeMaker.getLandNodeType(this.deliverer, pos.mutableCopy());
        if (pathNodeType != PathNodeType.WALKABLE) {
            return false;
        }
        else {
            BlockState blockState = this.world.getBlockState(pos.down());
            if (!this.leavesAllowed && blockState.getBlock() instanceof LeavesBlock) {
                return false;
            }
            else {
                BlockPos blockPos = pos.subtract(this.deliverer.getBlockPos());
                return this.world.isSpaceEmpty(this.deliverer, this.deliverer.getBounds().offset(blockPos));
            }
        }
    }

    private int getRandomInt(int min, int max) {
        return this.deliverer.getRandom().nextInt(max - min + 1) + min;
    }
}
