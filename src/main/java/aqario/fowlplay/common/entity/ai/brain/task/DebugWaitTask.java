package aqario.fowlplay.common.entity.ai.brain.task;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.WaitTask;
import net.minecraft.server.world.ServerWorld;

public class DebugWaitTask extends WaitTask {
    public DebugWaitTask(int minRunTime, int maxRunTime) {
        super(minRunTime, maxRunTime);
    }

    @Override
    protected void run(ServerWorld world, LivingEntity entity, long time) {
        System.out.println("DebugWaitTask.run");
        super.run(world, entity, time);
    }
}
