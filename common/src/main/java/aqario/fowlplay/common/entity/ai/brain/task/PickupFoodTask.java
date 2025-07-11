package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.util.Birds;
import aqario.fowlplay.common.util.MemoryList;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.server.world.ServerWorld;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.registry.SBLMemoryTypes;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

// probably doesn't need to be a task, but it is for now
public class PickupFoodTask<E extends BirdEntity> extends ExtendedBehaviour<E> {
    private static final MemoryList MEMORIES = MemoryList.create(3)
        .present(
            SBLMemoryTypes.NEARBY_ITEMS.get()
        )
        .absent(
            FowlPlayMemoryModuleType.SEES_FOOD.get(),
            FowlPlayMemoryModuleType.CANNOT_PICKUP_FOOD.get()
        );

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryModuleState>> getMemoryRequirements() {
        return MEMORIES;
    }

    @Override
    protected boolean shouldRun(ServerWorld level, E entity) {
        return Birds.canPickupFood(entity);
    }

    @Override
    protected void start(E entity) {
        BrainUtils.setMemory(entity, FowlPlayMemoryModuleType.SEES_FOOD.get(), true);
    }
}
