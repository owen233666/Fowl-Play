package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class FlockTask extends Task<FlyingBirdEntity> {
    public final float coherenceChangeRate;
    public final float alignmentChangeRate;
    public final float separationChangeRate;
    public final float separationRange;
    private List<? extends FlyingBirdEntity> nearbyBirds;
    private Vec3d prevVelocity;
    private float speed;

    public FlockTask(float speed, float coherenceChangeRate, float alignmentChangeRate, float separationChangeRate, float separationRange) {
        super(ImmutableMap.of());
        this.speed = speed;
        this.coherenceChangeRate = coherenceChangeRate;
        this.alignmentChangeRate = alignmentChangeRate;
        this.separationChangeRate = separationChangeRate;
        this.separationRange = separationRange;
    }

    @Override
    protected boolean shouldRun(ServerWorld world, FlyingBirdEntity bird) {
        if (!bird.isFlying()) {
            return false;
        }
        this.nearbyBirds = bird.getWorld().getEntitiesByClass(bird.getClass(), bird.getBounds().expand(5), LivingEntity::isAlive);

        return !this.nearbyBirds.isEmpty();
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, FlyingBirdEntity bird, long time) {
        return this.shouldRun(world, bird);
    }

    @Override
    protected void keepRunning(ServerWorld world, FlyingBirdEntity bird, long time) {
//        bird.getNavigation().stop();
//        WalkTarget walkTarget = new WalkTarget(
//            new BlockPosLookTarget(this.alignment(bird)), 1, 1
//        );
//        bird.getBrain().remember(MemoryModuleType.LOOK_TARGET, new BlockPosLookTarget(this.alignment(bird)));
//        bird.getBrain().remember(MemoryModuleType.WALK_TARGET, walkTarget);
//        bird.addVelocity(this.getRandomMovement(bird));
        this.prevVelocity = bird.getVelocity();
        Vec3d heading = this.getHeading(bird).normalize().multiply(bird.getMovementSpeed()).add(bird.getPos());
        bird.getLookControl().lookAt(heading.x, heading.y + 0.5, heading.z, 5, 5);
        bird.getMoveControl().moveTo(heading.x, heading.y, heading.z, 2);
//        bird.addVelocity(this.getHeading(bird).normalize().multiply(bird.getMovementSpeed()));
//        bird.getLookControl().lookAt(bird.getPos().add(bird.getVelocity().multiply(1.1)));
//        bird.addVelocity(this.cohesion(bird));
//        this.prevVelocity = bird.getVelocity();
//        bird.addVelocity(this.alignment(bird));
//        this.prevVelocity = bird.getVelocity();
//        bird.addVelocity(this.separation(bird));
    }

    public Vec3d getRandomMovement(FlyingBirdEntity bird) {
        Vec3d velocity = bird.getVelocity();

        if (MathHelper.abs((float) velocity.x) < 0.1 && MathHelper.abs((float) velocity.z) < 0.1) {
            return new Vec3d(this.randomSign(bird) * 0.4, this.randomSign(bird) * 0.4, this.randomSign(bird) * 0.4);
        }

        return Vec3d.ZERO;
    }

    public int randomSign(FlyingBirdEntity bird) {
        return bird.getRandom().nextBoolean() ? 1 : -1;
    }

    private Vec3d getHeading(FlyingBirdEntity bird) {
        Vec3d separation = Vec3d.ZERO;
        Vec3d alignment = Vec3d.ZERO;
        Vec3d cohesion = Vec3d.ZERO;

        for (PassiveEntity entity : this.nearbyBirds) {
            if ((entity.getPos().subtract(bird.getPos()).length()) < this.separationRange) {
                separation = separation.subtract(entity.getPos().subtract(bird.getPos()));
            }
            alignment = alignment.add(entity.getVelocity());
            cohesion = cohesion.add(entity.getPos());
        }

        alignment = alignment.multiply(1f / this.nearbyBirds.size());
        alignment = alignment.subtract(bird.getVelocity());
        cohesion = cohesion.multiply(1f / this.nearbyBirds.size());
        cohesion = cohesion.subtract(bird.getPos());

        separation.multiply(this.speed);
        alignment.multiply(this.speed);
        cohesion.multiply(this.speed);

        separation = this.prevVelocity.lerp(separation, this.separationChangeRate).add(this.prevVelocity.negate());
        alignment = this.prevVelocity.lerp(alignment, this.alignmentChangeRate).add(this.prevVelocity.negate());
        cohesion = this.prevVelocity.lerp(cohesion, this.coherenceChangeRate).add(this.prevVelocity.negate());

        return separation.add(alignment).add(cohesion);
    }

    public Vec3d separation(FlyingBirdEntity bird) {
        Vec3d velocity = Vec3d.ZERO;

        for (PassiveEntity entity : this.nearbyBirds) {
            if ((entity.getPos().subtract(bird.getPos()).length()) < this.separationRange) {
                velocity = velocity.subtract(entity.getPos().subtract(bird.getPos()));
            }
        }

        return this.prevVelocity.lerp(velocity.multiply(this.speed), this.separationChangeRate);
    }

    public Vec3d alignment(FlyingBirdEntity bird) {
        Vec3d velocity = Vec3d.ZERO;

        for (PassiveEntity entity : this.nearbyBirds) {
            velocity = velocity.add(entity.getVelocity());
        }

        velocity = velocity.multiply(1f / this.nearbyBirds.size());
        velocity = velocity.subtract(bird.getVelocity());
        return this.prevVelocity.lerp(velocity.multiply(this.speed), this.alignmentChangeRate);
    }

    public Vec3d cohesion(FlyingBirdEntity bird) {
        Vec3d velocity = Vec3d.ZERO;

        for (PassiveEntity entity : this.nearbyBirds) {
            velocity = velocity.add(entity.getPos());
        }

        velocity = velocity.multiply(1f / this.nearbyBirds.size());
        velocity = velocity.subtract(bird.getPos());
        return this.prevVelocity.lerp(velocity.multiply(this.speed), this.coherenceChangeRate);
    }
}
