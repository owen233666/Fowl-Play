package aqario.fowlplay.common.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.ConditionalTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;

import java.util.Map;
import java.util.function.Predicate;

public class DebugConditionalTask<E extends LivingEntity> extends ConditionalTask<E> {

    public DebugConditionalTask(Map<MemoryModuleType<?>, MemoryModuleState> requiredMemoryStates, Predicate<E> condition, Task<? super E> delegate, boolean allowsContinuation) {
        super(requiredMemoryStates, condition, delegate, allowsContinuation);
    }

    public DebugConditionalTask(Predicate<E> condition, Task<? super E> delegate, boolean allowsContinuation) {
        this(ImmutableMap.of(), condition, delegate, allowsContinuation);
    }

    public DebugConditionalTask(Predicate<E> condition, Task<? super E> delegate) {
        this(ImmutableMap.of(), condition, delegate, false);
    }

    public DebugConditionalTask(Map<MemoryModuleType<?>, MemoryModuleState> memory, Task<? super E> delegate) {
        this(memory, livingEntity -> true, delegate, false);
    }

    @Override
    protected boolean shouldRun(ServerWorld world, E entity) {
        System.out.println("DebugConditionalTask.shouldRun");
        return super.shouldRun(world, entity);
    }

    @Override
    protected void run(ServerWorld world, E entity, long time) {
        System.out.println("DebugConditionalTask.run");
        super.run(world, entity, time);
    }
}
