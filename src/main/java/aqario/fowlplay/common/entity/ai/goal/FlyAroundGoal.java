package aqario.fowlplay.common.entity.ai.goal;

import aqario.fowlplay.common.entity.BirdEntity;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class FlyAroundGoal extends Goal {
    private final BirdEntity bird;
    private final RandomGenerator random;
    private double x;
    private double y;
    private double z;
    private boolean flightTarget = false;

    public FlyAroundGoal(BirdEntity bird) {
        this.bird = bird;
        this.setControls(EnumSet.of(Control.MOVE));
        this.random = this.bird.getRandom();
    }

    @Override
    public boolean canStart() {
        if (this.bird.hasPassengers() || (this.bird.getTarget() != null && this.bird.getTarget().isAlive()) || this.bird.hasVehicle()) {
            return false;
        }
        if (this.bird.getRandom().nextInt(30) != 0 && !this.bird.isFlying()) {
            return false;
        }
        if (this.bird.isOnGround()) {
            this.flightTarget = random.nextBoolean();
        } else {
            this.flightTarget = random.nextInt(5) > 0 && this.bird.timeFlying < 200;
        }
        Vec3d pos = this.getPosition();
        if (pos == null) {
            return false;
        }
        this.x = pos.x;
        this.y = pos.y;
        this.z = pos.z;
        return true;
    }

    @Override
    public void tick() {
        if (flightTarget) {
            this.bird.getMoveControl().moveTo(x, y, z, 1F);
        } else {
            this.bird.getNavigation().startMovingTo(this.x, this.y, this.z, 1F);

            if (this.bird.isFlying() && this.bird.isOnGround()) {
                this.bird.setFlying(false);
            }
        }

        if (this.bird.isFlying() && this.bird.isOnGround() && this.bird.timeFlying > 10) {
            this.bird.setFlying(false);
        }
    }

    @Nullable
    protected Vec3d getPosition() {
        final Vec3d vec3d = this.bird.getPos();
        if (this.bird.isOverWater()){
            flightTarget = true;
        }
        if (flightTarget) {
            if (this.bird.timeFlying < 50 || this.bird.isOverWater()) {
                return this.bird.getBlockInViewAway(vec3d, 0);
            } else {
                return this.bird.getBlockGrounding(vec3d);
            }
        } else {
            return FuzzyTargeting.find(this.bird, 10, 7);
        }
    }

    @Override
    public boolean shouldContinue() {
        if (flightTarget) {
            return this.bird.isFlying() && this.bird.squaredDistanceTo(x, y, z) > 2F;
        } else {
            return (!this.bird.getNavigation().isIdle()) && !this.bird.hasPassengers();
        }
    }

    @Override
    public void start() {
        if (flightTarget) {
            this.bird.setFlying(true);
            this.bird.getMoveControl().moveTo(x, y, z, 1F);
        } else {
            this.bird.getNavigation().startMovingTo(this.x, this.y, this.z, 1F);
        }
    }

    @Override
    public void stop() {
        this.bird.getNavigation().stop();
        super.stop();
    }
}