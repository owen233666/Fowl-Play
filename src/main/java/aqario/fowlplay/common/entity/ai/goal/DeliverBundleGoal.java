package aqario.fowlplay.common.entity.ai.goal;

import aqario.fowlplay.common.entity.PigeonEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

import java.util.EnumSet;

public class DeliverBundleGoal<T extends TameableEntity> extends Goal {
    private final T tameable;

    private LivingEntity owner;

    private final WorldView world;

    private final double speed;

    private final EntityNavigation navigation;

    private int updateCountdownTicks;

    private final float maxDistance;

    private final float minDistance;

    private float oldWaterPathfindingPenalty;

    private final boolean leavesAllowed;

    private PlayerEntity recipient;

    public DeliverBundleGoal(T tameable, double speed, float minDistance, float maxDistance, boolean leavesAllowed) {
        this.tameable = tameable;
        this.world = tameable.world;
        this.speed = speed;
        this.navigation = tameable.getNavigation();
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.leavesAllowed = leavesAllowed;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        if ((!(tameable.getNavigation() instanceof MobNavigation) && !(tameable.getNavigation() instanceof BirdNavigation)) || !(tameable instanceof PigeonEntity))
            throw new IllegalArgumentException("Unsupported mob type for DeliverBundleGoal");
    }

    @Override
    public boolean canStart() {
        LivingEntity owner = this.tameable.getOwner();
        if (((PigeonEntity)this.tameable).getRecipientUuid() != null) {
            this.recipient = this.tameable.getWorld().getPlayerByUuid(((PigeonEntity)this.tameable).getRecipientUuid());
            PlayerEntity recipient = this.recipient;
            if (this.recipient != null) {
                if (owner == null) {
                    return false;
                }
                if (owner.isSpectator()) {
                    return false;
                }
                if (this.tameable.squaredDistanceTo(recipient) < (this.minDistance * this.minDistance)) {
                    return false;
                }
                this.owner = owner;
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean shouldContinue() {
        if (this.navigation.isIdle())
            return false;
        return (this.tameable.squaredDistanceTo(this.recipient) > (this.maxDistance * this.maxDistance));
    }

    @Override
    public void start() {
        this.updateCountdownTicks = 0;
        this.oldWaterPathfindingPenalty = this.tameable.getPathfindingPenalty(PathNodeType.WATER);
        this.tameable.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
        this.tameable.getDataTracker().set(PigeonEntity.DELIVERING, true);
    }

    @Override
    public void stop() {
        this.recipient = null;
        this.navigation.stop();
        this.tameable.setPathfindingPenalty(PathNodeType.WATER, this.oldWaterPathfindingPenalty);
    }

    @Override
    public void tick() {
        this.tameable.getLookControl().lookAt(this.recipient, 10.0F, this.tameable.getLookPitchSpeed());
        if (--this.updateCountdownTicks <= 0) {
            this.updateCountdownTicks = 10;
            if (this.tameable.squaredDistanceTo(this.recipient) >= 10000.0D) {
                tryTeleport();
            } else {
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
            if (bl)
                return;
        }
    }

    private boolean tryTeleportTo(int x, int y, int z) {
        if (Math.abs(x - this.recipient.getX()) < 2.0D && Math.abs(z - this.recipient.getZ()) < 2.0D)
            return false;
        if (!canTeleportTo(new BlockPos(x, y, z)))
            return false;
        this.tameable.refreshPositionAndAngles(x + 0.5D, y, z + 0.5D, this.tameable.headYaw, this.tameable.getPitch());
        this.navigation.stop();
        return true;
    }

    private boolean canTeleportTo(BlockPos pos) {
        PathNodeType pathNodeType = LandPathNodeMaker.getLandNodeType(this.world, pos.mutableCopy());
        if (pathNodeType != PathNodeType.WALKABLE)
            return false;
        BlockState state = this.world.getBlockState(pos.down());
        if (!this.leavesAllowed && state.getBlock() instanceof LeavesBlock)
            return false;
        BlockPos blockPos = pos.subtract(this.tameable.getBlockPos());
        return this.world.isSpaceEmpty(this.tameable, this.tameable.getBoundingBox().offset(blockPos));
    }

    private int getRandomInt(int min, int max) {
        return this.tameable.getRandom().nextInt(max - min + 1) + min;
    }
}
