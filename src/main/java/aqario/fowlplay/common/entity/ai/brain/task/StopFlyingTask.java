package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.entity.ai.brain.FowlPlayMemoryModuleType;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;

public class StopFlyingTask extends Task<FlyingBirdEntity> {
    public StopFlyingTask() {
        super(ImmutableMap.of());
    }

    @Override
    protected boolean shouldRun(ServerWorld world, FlyingBirdEntity bird) {
        return bird.isOnGround() || bird.isInsideWaterOrBubbleColumn();
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, FlyingBirdEntity bird, long l) {
        return this.shouldRun(world, bird);
    }

    @Override
    protected void keepRunning(ServerWorld world, FlyingBirdEntity bird, long l) {
        bird.getBrain().forget(FowlPlayMemoryModuleType.IS_FLYING);
        bird.getBrain().forget(MemoryModuleType.WALK_TARGET);
    }
}
