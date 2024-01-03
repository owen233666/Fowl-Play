package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.ai.brain.sensor.FowlPlayMemoryModuleType;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PenguinStrollTask extends Task<PassiveEntity> {
    private static final int MIN_RUN_TIME = 10;
    private static final int MAX_RUN_TIME = 7;
    private final float speed;
    protected final int horizontalRadius;
    protected final int verticalRadius;

    public PenguinStrollTask(float speed) {
        this(speed, 10, 7);
    }

    public PenguinStrollTask(float speed, int horizontalRadius, int verticalRadius) {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT));
        this.speed = speed;
        this.horizontalRadius = horizontalRadius;
        this.verticalRadius = verticalRadius;
    }

    @Override
    protected boolean shouldRun(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        PassiveEntity passiveEntity2 = this.getNearestVisiblePenguin(passiveEntity);
        return passiveEntity.isInRange(passiveEntity2, this.horizontalRadius);
    }

    @Override
    protected void run(ServerWorld serverWorld, PassiveEntity passiveEntity, long l) {
        Optional<Vec3d> optional = Optional.ofNullable(this.findWalkTarget(passiveEntity));
        passiveEntity.getBrain().remember(MemoryModuleType.WALK_TARGET, optional.map(pos -> new WalkTarget(pos, this.speed, 0)));
    }

    @Nullable
    protected Vec3d findWalkTarget(PathAwareEntity entity) {
        return FuzzyTargeting.find(entity, this.horizontalRadius, this.verticalRadius);
    }

    private PassiveEntity getNearestVisiblePenguin(PassiveEntity passiveEntity) {
        return (PassiveEntity) passiveEntity.getBrain().getOptionalMemory(FowlPlayMemoryModuleType.NEAREST_VISIBLE_PENGUIN).get();
    }
}
