package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.util.MemoryList;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.util.Unit;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.InvalidateMemory;
import net.tslat.smartbrainlib.util.BrainUtils;

public class AvoidTasks {
    public static <E extends BirdEntity> SingleTickBehaviour<E> avoid() {
        return new SingleTickBehaviour<>(
            MemoryList.create(2)
                .absent(FowlPlayMemoryModuleType.IS_AVOIDING.get())
                .present(MemoryModuleType.AVOID_TARGET),
            (bird, brain) -> {
                LivingEntity target = BrainUtils.getMemory(brain, MemoryModuleType.AVOID_TARGET);
                if(bird.isInRange(target, bird.getFleeRange(target))) {
                    BrainUtils.setMemory(brain, FowlPlayMemoryModuleType.IS_AVOIDING.get(), Unit.INSTANCE);
                    return true;
                }
                return false;
            }
        );
    }

    public static <E extends BirdEntity> ExtendedBehaviour<E> forget() {
//        return new SingleTickBehaviour<>(
//            MemoryList.create(2)
//                .registered(MemoryModuleType.AVOID_TARGET)
//                .present(FowlPlayMemoryModuleType.IS_AVOIDING.get()),
//            (bird, brain) -> {
//                LivingEntity target = BrainUtils.getMemory(brain, MemoryModuleType.AVOID_TARGET);
//                if(target != null && bird.isInRange(target, bird.getFleeRange(target))) {
//                    return false;
//                }
//                BrainUtils.clearMemory(brain, FowlPlayMemoryModuleType.IS_AVOIDING.get());
//                return true;
//            }
//        );
        return new InvalidateMemory<E, Unit>(FowlPlayMemoryModuleType.IS_AVOIDING.get())
            .invalidateIf((entity, memory) -> {
                LivingEntity target = BrainUtils.getMemory(entity, MemoryModuleType.AVOID_TARGET);
                return target == null || !entity.isInRange(target, entity.getFleeRange(target));
            });
    }
}
