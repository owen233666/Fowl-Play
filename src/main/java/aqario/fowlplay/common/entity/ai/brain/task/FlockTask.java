package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.entity.ai.brain.FowlPlayMemoryModuleType;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class FlockTask extends Task<FlyingBirdEntity> {
    public final float coherence;
    public final float alignment;
    public final float separation;
    public final float separationRange;
    private List<? extends PassiveEntity> nearbyBirds;

    public FlockTask(float coherence, float alignment, float separation, float separationRange) {
        super(ImmutableMap.of());
        this.coherence = coherence;
        this.alignment = alignment;
        this.separation = separation;
        this.separationRange = separationRange;
    }

    @Override
    protected boolean shouldRun(ServerWorld world, FlyingBirdEntity bird) {
        if (!bird.isFlying()) {
            return false;
        }
        if (!bird.getBrain().hasMemoryModule(FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS)) {
            return false;
        }

        this.nearbyBirds = bird.getBrain().getOptionalMemory(FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS).get();

        return this.nearbyBirds.size() > 5;
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, FlyingBirdEntity bird, long time) {
        if (!bird.isFlying()) {
            return false;
        }
        if (!bird.getBrain().hasMemoryModule(FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS)) {
            return false;
        }
//        if (time % 2 == bird.flockTickOffset) {
            this.nearbyBirds = bird.getBrain().getOptionalMemory(FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS).get();
//        }

        return this.nearbyBirds.size() > 5;
    }

    @Override
    protected void keepRunning(ServerWorld world, FlyingBirdEntity bird, long time) {
        Vec3d heading = this.getHeading(bird).add(bird.getPos());
        System.out.println(heading.x + " " + heading.y + " " + heading.z);
        bird.getMoveControl().moveTo(heading.x, heading.y, heading.z, (bird.getRandom().nextFloat() - bird.getRandom().nextFloat()) * 1.5 + 2);
//        bird.addVelocity(this.getAlignment(bird).normalize().multiply(bird.getMovementSpeed() * 2));
//        bird.addVelocity(this.getCohesion(bird).normalize().multiply(bird.getMovementSpeed() * 2));
//        bird.addVelocity(this.getSeparation(bird).normalize().multiply(bird.getMovementSpeed() * 2));
    }

    private Vec3d getHeading(FlyingBirdEntity bird) {
        Vec3d separation = Vec3d.ZERO;
        Vec3d alignment = Vec3d.ZERO;
        Vec3d cohesion = Vec3d.ZERO;

        for (PassiveEntity entity : this.nearbyBirds) {
            if (entity.getPos().subtract(bird.getPos()).length() < this.separationRange) {
                separation = separation.subtract(entity.getPos().subtract(bird.getPos()));
            }
            alignment = alignment.add(entity.getVelocity());
            cohesion = cohesion.add(entity.getPos());
        }

        alignment = alignment.multiply(1f / this.nearbyBirds.size());
        cohesion = cohesion.multiply(1f / this.nearbyBirds.size());
        cohesion = cohesion.subtract(bird.getPos());

        alignment = alignment.multiply(0.5);
        separation = separation.multiply(0.05);
        cohesion = cohesion.multiply(0.05);

        return cohesion.add(separation).add(alignment);
    }

    private Vec3d getAlignment(FlyingBirdEntity bird) {
        Vec3d alignment = Vec3d.ZERO;

        for (PassiveEntity entity : this.nearbyBirds) {
            alignment = alignment.add(entity.getVelocity());
        }

        alignment = alignment.multiply(1f / this.nearbyBirds.size());
        alignment = alignment.subtract(bird.getVelocity());
        alignment.multiply(0.05);

        return alignment;
    }

    private Vec3d getCohesion(FlyingBirdEntity bird) {
        Vec3d cohesion = Vec3d.ZERO;

        for (PassiveEntity entity : this.nearbyBirds) {
            cohesion = cohesion.add(entity.getPos());
        }
        cohesion = cohesion.multiply(1f / this.nearbyBirds.size());
        cohesion = cohesion.subtract(bird.getPos());

        cohesion.multiply(0.005);

        return cohesion;
    }

    private Vec3d getSeparation(FlyingBirdEntity bird) {
        Vec3d separation = Vec3d.ZERO;

        for (PassiveEntity entity : this.nearbyBirds) {
            if (entity.getPos().subtract(bird.getPos()).length() < this.separationRange) {
                separation = separation.subtract(entity.getPos().subtract(bird.getPos()));
            }
        }

        separation.multiply(0.005);

        return separation;
    }
}
