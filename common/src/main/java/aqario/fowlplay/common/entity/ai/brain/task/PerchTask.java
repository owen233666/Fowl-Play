package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.entity.ai.pathing.FlightTargeting;
import aqario.fowlplay.common.util.MemoryList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.util.math.Vec3d;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.SquareRadius;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;
import java.util.Optional;

public class PerchTask<E extends FlyingBirdEntity> extends ExtendedBehaviour<E> {
    private static final MemoryList MEMORIES = MemoryList.create(1)
        .absent(MemoryModuleType.WALK_TARGET);
    public static final SquareRadius PERCH_RANGE = new SquareRadius(32, 32);
    public static final SquareRadius GROUND_RANGE = new SquareRadius(8, 64);

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryModuleState>> getMemoryRequirements() {
        return MEMORIES;
    }

    @Override
    protected void start(E entity) {
        Brain<?> brain = entity.getBrain();
        Optional<Vec3d> target = Optional.ofNullable(FlightTargeting.findPerchOrGround(entity, PERCH_RANGE, GROUND_RANGE));
        BrainUtils.setMemory(brain, MemoryModuleType.WALK_TARGET, target.map(vec3d -> new WalkTarget(vec3d, 1.0f, 0))
            .orElse(null));
    }
}
