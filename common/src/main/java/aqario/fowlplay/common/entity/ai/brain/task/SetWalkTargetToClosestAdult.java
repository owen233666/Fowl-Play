package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;
import java.util.function.Function;

public class SetWalkTargetToClosestAdult {
    public static SingleTickBehaviour<BirdEntity> create(UniformIntProvider executionRange, float speed) {
        return create(executionRange, entity -> speed);
    }

    public static SingleTickBehaviour<BirdEntity> create(UniformIntProvider executionRange, Function<BirdEntity, Float> speedGetter) {
        return new SingleTickBehaviour<>(
            List.of(
                Pair.of(MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleState.VALUE_PRESENT),
                Pair.of(MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED),
                Pair.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT)
            ),
            (bird, brain) -> {
                PassiveEntity nearest = BrainUtils.getMemory(brain, MemoryModuleType.NEAREST_VISIBLE_ADULT);
                if (bird.isInRange(nearest, executionRange.getMax() + 1)
                    && !bird.isInRange(nearest, executionRange.getMin())) {
                    WalkTarget newWalkTarget = new WalkTarget(
                        new EntityLookTarget(nearest, false), speedGetter.apply(bird), executionRange.getMin() - 1
                    );
                    BrainUtils.setMemory(brain, MemoryModuleType.LOOK_TARGET, new EntityLookTarget(nearest, true));
                    BrainUtils.setMemory(brain, MemoryModuleType.WALK_TARGET, newWalkTarget);
                    return true;
                }
                return false;
            }
        );
    }
}
