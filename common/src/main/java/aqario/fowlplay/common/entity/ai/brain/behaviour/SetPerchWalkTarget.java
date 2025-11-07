package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.entity.ai.pathing.BirdTargeting;
import aqario.fowlplay.common.util.CuboidRadius;
import aqario.fowlplay.common.util.MemoryList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.util.math.Vec3d;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;
import java.util.Optional;

public class SetPerchWalkTarget<E extends FlyingBirdEntity> extends ExtendedBehaviour<E> {
    private static final MemoryList MEMORIES = MemoryList.create(1)
        .absent(MemoryModuleType.WALK_TARGET);
    public static final CuboidRadius<Integer> PERCH_RANGE = new CuboidRadius<>(32, 32);
    public static final CuboidRadius<Integer> GROUND_RANGE = new CuboidRadius<>(8, 64);

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryModuleState>> getMemoryRequirements() {
        return MEMORIES;
    }

    @Override
    protected void start(E entity) {
        Brain<?> brain = entity.getBrain();
        Optional<Vec3d> target = BirdTargeting.findPerchOrGround(entity, PERCH_RANGE, GROUND_RANGE);
        if(target.isPresent()) {
            BrainUtils.setMemory(brain, MemoryModuleType.WALK_TARGET, new WalkTarget(target.get(), 1.0f, 0));
        }
        else {
            BrainUtils.clearMemory(brain, MemoryModuleType.WALK_TARGET);
        }
    }
}
