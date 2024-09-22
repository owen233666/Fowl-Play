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
        if (this.state == MoveControl.State.MOVE_TO) {
            this.state = MoveControl.State.WAIT;
            Vec3d vec3d = new Vec3d(this.targetX - this.bird.getX(), this.targetY - this.bird.getY(), this.targetZ - this.bird.getZ());
            double g = vec3d.lengthSquared();
            if (g < 2.5000003E-7F) {
                this.bird.setUpwardSpeed(0.0F);
                this.bird.setForwardSpeed(0.0F);
                return;
            }

            float h = (float) (MathHelper.atan2(vec3d.z, vec3d.x) * 180.0F / (float) Math.PI) - 90.0F;
            this.bird.setYaw(this.wrapDegrees(this.bird.getYaw(), h, 5.0F));
            float speed = (float) (this.speed * this.bird.getAttributeValue(EntityAttributes.GENERIC_FLYING_SPEED));
            this.bird.setMovementSpeed(speed);

            double horizontalDistance = vec3d.horizontalLength();
            if (Math.abs(vec3d.y) > 1.0E-5F || Math.abs(horizontalDistance) > 1.0E-5F) {
                float k = (float) (-(MathHelper.atan2(vec3d.y, horizontalDistance) * 180.0F / (float) Math.PI));
                this.bird.setPitch(this.wrapDegrees(this.bird.getPitch(), k, (float) this.maxPitchChange));
//                this.bird.setUpwardSpeed(vec3d.y > 0.0 ? speed : -speed);
            }
            if (this.bird.getPitch() < 0.0F/* && vec3d.length() < 1.0F*/) {
                this.bird.setUpwardSpeed(speed * Math.abs(this.bird.getPitch() / 60));
            }
            if (this.bird.getPitch() > 0.0F/* && vec3d.length() < 1.0F*/) {
                this.bird.setUpwardSpeed(-speed * Math.abs(this.bird.getPitch() / 60));
            }
        }
        else {
            this.bird.setUpwardSpeed(0.0F);
            this.bird.setForwardSpeed(0.0F);
        }
    }
}
