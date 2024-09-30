package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;

public class StopFallingTask extends Task<FlyingBirdEntity> {
    public StopFallingTask() {
        super(ImmutableMap.of());
    }

    public static boolean shouldRun(FlyingBirdEntity bird) {
        return bird.fallDistance > 1 && bird.getHealth() > 2.0F && !bird.isFlying();
    }

    @Override
    protected boolean shouldRun(ServerWorld world, FlyingBirdEntity bird) {
        return shouldRun(bird);
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, FlyingBirdEntity bird, long l) {
        return this.shouldRun(world, bird);
    }

    @Override
    protected void keepRunning(ServerWorld world, FlyingBirdEntity bird, long l) {
        bird.setFlying(true);
    }
}
