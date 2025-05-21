package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.PigeonEntity;
import aqario.fowlplay.common.entity.ai.brain.TeleportTarget;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.player.PlayerEntity;
import net.tslat.smartbrainlib.object.MemoryTest;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.function.Function;
import java.util.function.Predicate;

public class DeliverBundleTask {
    public static <E extends PigeonEntity> SingleTickBehaviour<E> run(Predicate<E> startPredicate, Function<E, Float> entitySpeedGetter) {
        return new SingleTickBehaviour<>(
            MemoryTest.builder(4)
                .hasMemories(FowlPlayMemoryModuleType.RECIPIENT)
                .usesMemory(MemoryModuleType.LOOK_TARGET)
                .usesMemory(MemoryModuleType.WALK_TARGET)
                .usesMemory(FowlPlayMemoryModuleType.TELEPORT_TARGET),
            (bird, brain) -> {
                PlayerEntity recipient = bird.getWorld().getPlayerByUuid(BrainUtils.getMemory(brain, FowlPlayMemoryModuleType.RECIPIENT));
                if (recipient != null && startPredicate.test(bird)) {
                    WalkTarget walkTarget = new WalkTarget(new EntityLookTarget(recipient, false), entitySpeedGetter.apply(bird), 0);
                    BrainUtils.setMemory(brain, MemoryModuleType.LOOK_TARGET, new EntityLookTarget(recipient, true));
                    BrainUtils.setMemory(brain, MemoryModuleType.WALK_TARGET, walkTarget);
                    if (bird.getOwner() != null && bird.squaredDistanceTo(recipient) > 100 * 100 && bird.squaredDistanceTo(bird.getOwner()) > 16 * 16) {
                        BrainUtils.setMemory(brain, FowlPlayMemoryModuleType.TELEPORT_TARGET, new TeleportTarget(recipient));
                    }
                    return true;
                }
                return false;
            }
        );
    }
}
