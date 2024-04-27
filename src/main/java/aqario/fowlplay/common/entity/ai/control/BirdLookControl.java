package aqario.fowlplay.common.entity.ai.control;

import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.mob.MobEntity;

public class BirdLookControl extends LookControl {
    public BirdLookControl(MobEntity entity) {
        super(entity);
    }

    @Override
    public void lookAt(double x, double y, double z, float yawSpeed, float pitchSpeed) {
        this.lookX = x;
        this.lookY = y;
        this.lookZ = z;
        this.yawSpeed = yawSpeed;
        this.pitchSpeed = pitchSpeed;
        this.lookAtCooldown = Math.max(2, this.entity.getRandom().nextInt(8));
    }
}
