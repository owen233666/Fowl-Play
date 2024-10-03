package aqario.fowlplay.common.entity.ai.goal;

import aqario.fowlplay.common.entity.PigeonEntity;
import aqario.fowlplay.common.entity.TameableBirdEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

import java.util.EnumSet;

public class DeliverBundleGoal extends Goal {
    private final TameableBirdEntity deliverer;
    private final WorldView world;
    private final double speed;
    private final EntityNavigation navigation;
    private int updateCountdownTicks;
    private final float maxDistance;
    private final float minDistance;
    private float oldWaterPathfindingPenalty;
    private final boolean leavesAllowed;
    private PlayerEntity recipient;

    public DeliverBundleGoal(TameableBirdEntity bird, double speed, float minDistance, float maxDistance, boolean leavesAllowed) {
        this.deliverer = bird;
        this.world = bird.getWorld();
        this.speed = speed;
        this.navigation = bird.getNavigation();
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.leavesAllowed = leavesAllowed;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        if ((!(bird.getNavigation() instanceof MobNavigation) && !(bird.getNavigation() instanceof BirdNavigation)) || !(bird instanceof PigeonEntity)) {
            throw new IllegalArgumentException("Unsupported mob type for DeliverBundleGoal");
        }
    }

    @Override
    public boolean canStart() {
        LivingEntity owner = this.deliverer.getOwner();
        if (((PigeonEntity) this.deliverer).getRecipientUuid() != null) {
            this.recipient = this.deliverer.getWorld().getPlayerByUuid(((PigeonEntity) this.deliverer).getRecipientUuid());
            PlayerEntity recipient = this.recipient;
            if (this.recipient != null) {
                if (owner == null) {
                    return false;
                }
                if (owner.isSpectator()) {
                    return false;
                }
                if (this.deliverer.squaredDistanceTo(recipient) < (this.minDistance * this.minDistance)) {
                    return false;
                }
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean shouldContinue() {
        if (this.navigation.isIdle()) {
            return false;
        }
        return (this.deliverer.squaredDistanceTo(this.recipient) > (this.maxDistance * this.maxDistance));
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
            if (this.deliverer.squaredDistanceTo(this.recipient) >= 10000.0D) {
                tryTeleport();
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
            boolean bl = this.tryTeleportTo(blockPos.getX() + j, blockPos.getY() + k, blockPos.getZ() + l);
            if (bl) {
                return;
            }
        }
    }

    private boolean tryTeleportTo(int x, int y, int z) {
        if (Math.abs(x - this.recipient.getX()) < 2.0D && Math.abs(z - this.recipient.getZ()) < 2.0D) {
            return false;
        }
        if (!canTeleportTo(new BlockPos(x, y, z))) {
            return false;
        }
        this.deliverer.refreshPositionAndAngles(x + 0.5D, y, z + 0.5D, this.deliverer.headYaw, this.deliverer.getPitch());
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
