package aqario.fowlplay.common.entity.ai.control;

import aqario.fowlplay.common.entity.BirdEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;

public class BirdMoveControl extends MoveControl {
    private final BirdEntity bird;

    public BirdMoveControl(BirdEntity bird) {
        super(bird);
        this.bird = bird;
    }

    public void tick() {
        if (this.state == MoveControl.State.STRAFE) {
            float f = (float) this.bird.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            float g = (float) this.speed * f;
            float h = this.forwardMovement;
            float i = this.sidewaysMovement;
            float j = MathHelper.sqrt(h * h + i * i);
            if (j < 1.0F) {
                j = 1.0F;
            }

            j = g / j;
            h *= j;
            i *= j;
            float k = MathHelper.sin(this.bird.getYaw() * (float) (Math.PI / 180.0));
            float l = MathHelper.cos(this.bird.getYaw() * (float) (Math.PI / 180.0));
            float m = h * l - i * k;
            float n = i * l + h * k;
            if (!this.isWalkable(m, n)) {
                this.forwardMovement = 1.0F;
                this.sidewaysMovement = 0.0F;
            }

            this.bird.setMovementSpeed(g);
            this.bird.setForwardSpeed(this.forwardMovement);
            this.bird.setSidewaysSpeed(this.sidewaysMovement);
            this.state = MoveControl.State.WAIT;
        }
        else if (this.state == MoveControl.State.MOVE_TO) {
            this.state = MoveControl.State.WAIT;
            double d = this.targetX - this.bird.getX();
            double e = this.targetZ - this.bird.getZ();
            double o = this.targetY - this.bird.getY();
            double p = d * d + o * o + e * e;
            if (p < 2.5000003E-7F) {
                this.bird.setForwardSpeed(0.0F);
                return;
            }

            float n = (float) (MathHelper.atan2(e, d) * 180.0F / (float) Math.PI) - 90.0F;
            this.bird.setYaw(this.wrapDegrees(this.bird.getYaw(), n, 15.0F));
            this.bird.setMovementSpeed((float) (this.speed * this.bird.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
            BlockPos blockPos = this.bird.getBlockPos();
            BlockState blockState = this.bird.getWorld().getBlockState(blockPos);
            VoxelShape voxelShape = blockState.getCollisionShape(this.bird.getWorld(), blockPos);
            if (o > (double) this.bird.getStepHeight() && d * d + e * e < (double) Math.max(1.0F, this.bird.getWidth())
                || !voxelShape.isEmpty()
                && this.bird.getY() < voxelShape.getMax(Direction.Axis.Y) + (double) blockPos.getY()
                && !blockState.isIn(BlockTags.DOORS)
                && !blockState.isIn(BlockTags.FENCES)) {
                this.bird.getJumpControl().setActive();
                this.state = MoveControl.State.JUMPING;
            }
        }
        else if (this.state == MoveControl.State.JUMPING) {
            this.bird.setMovementSpeed((float) (this.speed * this.bird.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
            if (this.bird.isOnGround()) {
                this.state = MoveControl.State.WAIT;
            }
        }
        else {
            this.bird.setForwardSpeed(0.0F);
        }
    }

    private boolean isWalkable(float x, float z) {
        EntityNavigation entityNavigation = this.bird.getNavigation();
        if (entityNavigation != null) {
            PathNodeMaker pathNodeMaker = entityNavigation.getNodeMaker();
            return pathNodeMaker == null
                || pathNodeMaker.getDefaultNodeType(
                this.bird, BlockPos.create(this.bird.getX() + (double) x, this.bird.getBlockY(), this.bird.getZ() + (double) z)
            ) == PathNodeType.WALKABLE;
        }

        return true;
    }
}
