package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;
import java.util.function.Predicate;

public class PickupFoodTask {
    public static <E extends BirdEntity> SingleTickBehaviour<E> run() {
        return run(bird -> true);
    }

    public static <E extends BirdEntity> SingleTickBehaviour<E> run(Predicate<E> predicate) {
        return new SingleTickBehaviour<>(
            List.of(
                Pair.of(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryModuleState.VALUE_PRESENT),
                Pair.of(FowlPlayMemoryModuleType.SEES_FOOD, MemoryModuleState.VALUE_ABSENT),
                Pair.of(FowlPlayMemoryModuleType.CANNOT_PICKUP_FOOD, MemoryModuleState.VALUE_ABSENT)
            ),
            (bird, brain) -> {
                ItemEntity item = BrainUtils.getMemory(brain, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
                assert item != null;
                if (!bird.getFood().test(item.getStack())) {
                    return false;
                }
                if (!predicate.test(bird)) {
                    return false;
                }
                BrainUtils.setMemory(brain, FowlPlayMemoryModuleType.SEES_FOOD, true);
                return true;
            }
        );
    }
}
