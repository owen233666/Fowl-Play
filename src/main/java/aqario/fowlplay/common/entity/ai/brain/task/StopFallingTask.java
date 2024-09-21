package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;

public class StopFallingTask extends Task<BirdEntity> {
    public StopFallingTask() {
        super(ImmutableMap.of());
    }

    public static boolean shouldRun(BirdEntity bird) {
        return bird.fallDistance > 1 && bird.getHealth() > 2.0F && !bird.isFlying();
    }

    @Override
    protected boolean shouldRun(ServerWorld world, BirdEntity bird) {
        return shouldRun(bird);
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, BirdEntity bird, long l) {
        return this.shouldRun(world, bird);
    }

    @Override
    protected void keepRunning(ServerWorld world, BirdEntity bird, long l) {
        bird.setFlying(true);
    }
}
