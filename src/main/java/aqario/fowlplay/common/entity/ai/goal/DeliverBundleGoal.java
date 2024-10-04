package aqario.fowlplay.common.entity.ai.goal;

import aqario.fowlplay.common.entity.PigeonEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

import java.util.EnumSet;

public class DeliverBundleGoal extends Goal {
    private final PigeonEntity deliverer;
    private final WorldView world;
    private final double speed;
    private final EntityNavigation navigation;
    private int updateCountdownTicks;
    private final float maxDistance;
    private final float minDistance;
    private float oldWaterPathfindingPenalty;
    private final boolean leavesAllowed;
    private PlayerEntity recipient;

    public DeliverBundleGoal(PigeonEntity pigeon, double speed, float minDistance, float maxDistance, boolean leavesAllowed) {
        this.deliverer = pigeon;
        this.world = pigeon.getWorld();
        this.speed = speed;
        this.navigation = pigeon.getNavigation();
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.leavesAllowed = leavesAllowed;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        if (!(pigeon.getNavigation() instanceof MobNavigation) && !(pigeon.getNavigation() instanceof BirdNavigation)) {
            throw new IllegalArgumentException("Unsupported mob type for DeliverBundleGoal");
        }
    }

    @Override
    public boolean canStart() {
        if (!this.deliverer.isTamed()) {
            return false;
        }
        if (this.deliverer.getRecipientUuid() == null) {
            return false;
        }
        this.recipient = this.deliverer.getWorld().getPlayerByUuid(this.deliverer.getRecipientUuid());
        if (this.recipient == null) {
            return false;
        }
        return this.deliverer.squaredDistanceTo(this.recipient) > (this.minDistance * this.minDistance);
    }

    @Override
    public boolean shouldContinue() {
        if (this.navigation.isIdle()) {
            return false;
        }
        return this.deliverer.squaredDistanceTo(this.recipient) < (this.maxDistance * this.maxDistance);
    }

    @Override
    public void start() {
        this.updateCountdownTicks = 0;
        this.oldWaterPathfindingPenalty = this.deliverer.getPenalty(PathNodeType.WATER);
        this.deliverer.addPathfindingPenalty(PathNodeType.WATER, 0.0F);
    }

    @Override
    public void stop() {
        this.recipient = null;
        this.navigation.stop();
        this.deliverer.addPathfindingPenalty(PathNodeType.WATER, this.oldWaterPathfindingPenalty);
    }

    @Override
    public void tick() {
        this.deliverer.getLookControl().lookAt(this.recipient, 10.0F, this.deliverer.getLookPitchSpeed());
        if (--this.updateCountdownTicks <= 0) {
            this.updateCountdownTicks = 10;
            if (this.deliverer.squaredDistanceTo(this.recipient) > 10000 && this.deliverer.squaredDistanceTo(this.deliverer.getOwner()) > 1024) {
                this.tryTeleport();
            }
            else {
                this.navigation.startMovingTo(this.recipient, this.speed);
            }
        }
    }

    private void tryTeleport() {
        BlockPos blockPos = this.recipient.getBlockPos();
        for (int i = 0; i < 10; i++) {
            int j = getRandomInt(-3, 3);
            int k = getRandomInt(-1, 1);
            int l = getRandomInt(-3, 3);
            if (this.tryTeleportTo(blockPos.getX() + j, blockPos.getY() + k, blockPos.getZ() + l)) {
                return;
            }
        }
    }

    private boolean tryTeleportTo(int x, int y, int z) {
        if (Math.abs(x - this.recipient.getX()) < 2.0 && Math.abs(z - this.recipient.getZ()) < 2.0) {
            return false;
        }
        if (!canTeleportTo(new BlockPos(x, y, z))) {
            return false;
        }
        this.deliverer.refreshPositionAndAngles(x + 0.5, y, z + 0.5, this.deliverer.headYaw, this.deliverer.getPitch());
        this.navigation.stop();
        return true;
    }

    private boolean canTeleportTo(BlockPos pos) {
        PathNodeType pathNodeType = LandPathNodeMaker.getLandNodeType(this.deliverer, pos.mutableCopy());
        if (pathNodeType != PathNodeType.WALKABLE) {
            return false;
        }
        BlockState state = this.world.getBlockState(pos.down());
        if (!this.leavesAllowed && state.getBlock() instanceof LeavesBlock) {
            return false;
        }
        BlockPos blockPos = pos.subtract(this.deliverer.getBlockPos());
        return this.world.isSpaceEmpty(this.deliverer, this.deliverer.getBounds().offset(blockPos));
    }

    private int getRandomInt(int min, int max) {
        return this.deliverer.getRandom().nextInt(max - min + 1) + min;
    }
}
