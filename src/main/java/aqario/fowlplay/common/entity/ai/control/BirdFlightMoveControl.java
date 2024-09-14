package aqario.fowlplay.common.entity.ai.control;

import aqario.fowlplay.common.entity.BirdEntity;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class BirdFlightMoveControl extends BirdMoveControl {
    private final BirdEntity bird;
    private final int maxPitchChange;
    private final boolean noGravity;

    public BirdFlightMoveControl(BirdEntity bird, int maxPitchChange, boolean noGravity) {
        super(bird);
        this.bird = bird;
        this.maxPitchChange = maxPitchChange;
        this.noGravity = noGravity;
    }

    @Override
    public void tick() {
        if (this.bird.timeFlying < 20 + this.bird.getRandom().nextInt(20)) {
            float speed = (float) (this.speed * this.bird.getAttributeValue(EntityAttributes.GENERIC_FLYING_SPEED));
            this.bird.setUpwardSpeed(speed / 4);
        }
        if (this.state == MoveControl.State.MOVE_TO) {
            this.state = MoveControl.State.WAIT;
            this.bird.setNoGravity(true);
            Vec3d vec3d = new Vec3d(this.targetX - this.bird.getX(), this.targetY - this.bird.getY(), this.targetZ - this.bird.getZ());
            double g = vec3d.lengthSquared();
            if (g < 2.5000003E-7F) {
                this.bird.setUpwardSpeed(0.0F);
                this.bird.setForwardSpeed(0.0F);
                return;
            }

            float h = (float) (MathHelper.atan2(vec3d.z, vec3d.x) * 180.0F / (float) Math.PI) - 90.0F;
            this.bird.setYaw(this.wrapDegrees(this.bird.getYaw(), h, 5.0F));
            float speed;
//            if (this.bird.isOnGround()) {
//                speed = (float)(this.speed * this.bird.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
//            } else {
            speed = (float) (this.speed * this.bird.getAttributeValue(EntityAttributes.GENERIC_FLYING_SPEED));
//            }

            this.bird.setMovementSpeed(speed);
            double j = vec3d.horizontalLength();
            if (Math.abs(vec3d.y) > 1.0E-5F || Math.abs(j) > 1.0E-5F) {
                float k = (float) (-(MathHelper.atan2(vec3d.y, j) * 180.0F / (float) Math.PI));
                this.bird.setPitch(this.wrapDegrees(this.bird.getPitch(), k, (float) this.maxPitchChange));
                this.bird.setUpwardSpeed(vec3d.y > 0.0 ? speed / 4 : -speed / 4);
            }
            if (this.bird.getPitch() < 0.0F && vec3d.length() < 1.0F) {
                this.bird.setUpwardSpeed(speed / 4);
            }
        }
        else {
            if (!this.noGravity) {
                this.bird.setNoGravity(false);
            }

            this.bird.setUpwardSpeed(0.0F);
            this.bird.setForwardSpeed(0.0F);
        }
    }
}
