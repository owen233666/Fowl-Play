package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.util.Unit;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class AvoidTask {
    public static <E extends BirdEntity> SingleTickBehaviour<E> run() {
        return run(bird -> true);
    }

    public static <E extends BirdEntity> SingleTickBehaviour<E> run(Predicate<E> predicate) {
        return new SingleTickBehaviour<>(
            List.of(
                Pair.of(FowlPlayMemoryModuleType.IS_AVOIDING, MemoryModuleState.VALUE_ABSENT),
                Pair.of(MemoryModuleType.AVOID_TARGET, MemoryModuleState.VALUE_PRESENT)
            ),
            (bird, brain) -> {
                if (!predicate.test(bird)) {
                    return false;
                }
                LivingEntity target = BrainUtils.getMemory(brain, MemoryModuleType.AVOID_TARGET);
                if (bird.isInRange(
                    target,
                    bird.getFleeRange(target)
                )) {
                    BrainUtils.setMemory(brain, FowlPlayMemoryModuleType.IS_AVOIDING, Unit.INSTANCE);
                    return true;
                }
                return false;
            }
        );
    }

    public static <E extends BirdEntity> SingleTickBehaviour<E> forget() {
        return forget(bird -> true);
    }

    public static <E extends BirdEntity> SingleTickBehaviour<E> forget(Predicate<E> predicate) {
        return new SingleTickBehaviour<>(
            List.of(
                Pair.of(MemoryModuleType.AVOID_TARGET, MemoryModuleState.REGISTERED),
                Pair.of(FowlPlayMemoryModuleType.IS_AVOIDING, MemoryModuleState.VALUE_PRESENT)
            ),
            (bird, brain) -> {
                if (!predicate.test(bird)) {
                    return false;
                }
                Optional<LivingEntity> target = Optional.ofNullable(BrainUtils.getMemory(brain, MemoryModuleType.AVOID_TARGET));
                if (target.isPresent() && bird.isInRange(
                    target.get(),
                    bird.getFleeRange(target.get())
                )) {
                    return false;
                }
                BrainUtils.clearMemory(brain, FowlPlayMemoryModuleType.IS_AVOIDING);
                return true;
            }
        );
    }
}
