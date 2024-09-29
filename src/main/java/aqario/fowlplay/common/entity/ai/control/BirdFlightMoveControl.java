package aqario.fowlplay.common.entity.ai.control;

import aqario.fowlplay.common.entity.BirdEntity;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class BirdFlightMoveControl extends BirdMoveControl {
    private final BirdEntity bird;
    private final int maxPitchChange;

    public BirdFlightMoveControl(BirdEntity bird, int maxPitchChange) {
        super(bird);
        this.bird = bird;
        this.maxPitchChange = maxPitchChange;
    }

    @Override
    public void tick() {
//        if (this.bird.timeFlying < 20 + this.bird.getRandom().nextInt(20)) {
//            float speed = (float) (this.speed * this.bird.getAttributeValue(EntityAttributes.GENERIC_FLYING_SPEED));
//            this.bird.setUpwardSpeed(speed);
//        }
        if (this.state == MoveControl.State.MOVE_TO && !this.entity.getNavigation().isIdle()) {
//            this.state = MoveControl.State.WAIT;
            Vec3d distance = new Vec3d(this.targetX - this.bird.getX(), this.targetY - this.bird.getY(), this.targetZ - this.bird.getZ());
            double squaredDistance = distance.lengthSquared();
            if (squaredDistance < 2.5000003E-7F) {
                this.bird.setUpwardSpeed(0.0F);
                this.bird.setForwardSpeed(0.0F);
                return;
            }

            float yaw = (float) (MathHelper.atan2(distance.z, distance.x) * 180.0F / (float) Math.PI) - 90.0F;
            this.bird.setYaw(this.wrapDegrees(this.bird.getYaw(), yaw, 5.0F));
            float speed = (float) (this.speed * this.bird.getAttributeValue(EntityAttributes.GENERIC_FLYING_SPEED));
            if (this.bird.isFlying()) {
                this.bird.setMovementSpeed(speed);
                double horizontalDistance = Math.sqrt(distance.x * distance.x + distance.z * distance.z);
                if (Math.abs(distance.y) > 1.0E-5F || Math.abs(horizontalDistance) > 1.0E-5F) {
                    float pitch = -(float) (MathHelper.atan2(distance.y, horizontalDistance) * 180.0F / (float) Math.PI);
                    pitch = MathHelper.clamp(MathHelper.wrapDegrees(pitch), (float) -this.maxPitchChange, (float) this.maxPitchChange);
                    this.bird.setPitch(this.wrapDegrees(this.bird.getPitch(), pitch, 5.0F));
                }

                float x = MathHelper.cos(this.bird.getPitch() * (float) (Math.PI / 180.0));
                float y = MathHelper.sin(this.bird.getPitch() * (float) (Math.PI / 180.0));
                this.bird.forwardSpeed = x * speed;
                this.bird.upwardSpeed = -y * speed;
            }
        }
        else {
            this.bird.setMovementSpeed(0.0F);
            this.bird.setSidewaysSpeed(0.0F);
            this.bird.setUpwardSpeed(0.0F);
            this.bird.setForwardSpeed(0.0F);
        }
    }
}
