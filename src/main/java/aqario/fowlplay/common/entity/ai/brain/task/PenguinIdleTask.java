package aqario.fowlplay.common.entity.ai.brain.task;

import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.Map;

public class PenguinIdleTask extends Task<PathAwareEntity> {
    private static final int MIN_RUN_TIME = 10;
    private static final int MAX_RUN_TIME = 7;

    public PenguinIdleTask(Map<MemoryModuleType<?>, MemoryModuleState> requiredMemoryState) {
        super(requiredMemoryState);
    }

    @Override
    protected boolean shouldRun(ServerWorld world, PathAwareEntity entity) {
        return super.shouldRun(world, entity);
    }

    @Override
    protected void run(ServerWorld world, PathAwareEntity entity, long time) {
        super.run(world, entity, time);
    }
}
