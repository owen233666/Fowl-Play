package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.entity.ai.pathing.FlightTargeting;
import aqario.fowlplay.common.util.CuboidRadius;
import aqario.fowlplay.common.util.MemoryList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.util.math.Vec3d;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class SetNonAirWalkTargetTask<E extends FlyingBirdEntity> extends SpeedModifiableBehaviour<E> {
    private static final MemoryList MEMORIES = MemoryList.create(1)
        .absent(MemoryModuleType.WALK_TARGET);
    protected Predicate<E> avoidWaterPredicate = entity -> true;
    protected CuboidRadius<Integer> radius = new CuboidRadius<>(32, 16);
    protected BiPredicate<E, Vec3d> positionPredicate = (entity, pos) -> true;

    public SetNonAirWalkTargetTask<E> setRadius(int radius) {
        return setRadius(radius, radius);
    }

    public SetNonAirWalkTargetTask<E> setRadius(int xz, int y) {
        this.radius = new CuboidRadius<>(xz, y);

        return this;
    }

    public SetNonAirWalkTargetTask<E> walkTargetPredicate(BiPredicate<E, Vec3d> predicate) {
        this.positionPredicate = predicate;

        return this;
    }

    public SetNonAirWalkTargetTask<E> dontAvoidWater() {
        return this.avoidWaterWhen(entity -> false);
    }

    public SetNonAirWalkTargetTask<E> avoidWaterWhen(Predicate<E> predicate) {
        this.avoidWaterPredicate = predicate;

        return this;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryModuleState>> getMemoryRequirements() {
        return MEMORIES;
    }

    @Override
    protected void start(E entity) {
        Vec3d targetPos = this.getTargetPos(entity);

        if(!this.positionPredicate.test(entity, targetPos)) {
            targetPos = null;
        }

        if(targetPos == null) {
            BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
        }
        else {
            BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(targetPos, this.speedModifier.apply(entity, targetPos), 0));
        }
    }

    @Nullable
    protected Vec3d getTargetPos(E entity) {
        if(this.avoidWaterPredicate.test(entity)) {
            return FlightTargeting.findGround(entity, this.radius.xz(), this.radius.y());
        }
        return FlightTargeting.findNonAir(entity, this.radius);
    }
}