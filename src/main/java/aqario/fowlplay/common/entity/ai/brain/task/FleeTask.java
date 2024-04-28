package aqario.fowlplay.common.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class FleeTask<T extends LivingEntity> extends Task<PathAwareEntity> {
    protected final PathAwareEntity mob;
    private final double slowSpeed;
    private final double fastSpeed;
    @Nullable
    protected T targetEntity;
    protected final float fleeDistance;
    @Nullable
    protected Path fleePath;
    protected final EntityNavigation fleeingEntityNavigation;
    protected final Class<T> classToFleeFrom;
    protected final Predicate<LivingEntity> extraInclusionSelector;
    protected final Predicate<LivingEntity> inclusionSelector;
    private final TargetPredicate withinRangePredicate;

    public FleeTask(PathAwareEntity mob, Class<T> fleeFromType, float distance, double slowSpeed, double fastSpeed) {
        this(mob, fleeFromType, livingEntity -> true, distance, slowSpeed, fastSpeed, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR::test);
    }

    public FleeTask(PathAwareEntity mob, Class<T> fleeFromType, Predicate<LivingEntity> extraInclusionSelector, float distance, double slowSpeed, double fastSpeed, Predicate<LivingEntity> inclusionSelector) {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT));
        this.mob = mob;
        this.classToFleeFrom = fleeFromType;
        this.extraInclusionSelector = extraInclusionSelector;
        this.fleeDistance = distance;
        this.slowSpeed = slowSpeed;
        this.fastSpeed = fastSpeed;
        this.inclusionSelector = inclusionSelector;
        this.fleeingEntityNavigation = mob.getNavigation();
        this.withinRangePredicate = TargetPredicate.createAttackable().setBaseMaxDistance(distance).setPredicate(inclusionSelector.and(extraInclusionSelector));
    }

    public FleeTask(PathAwareEntity fleeingEntity, Class<T> classToFleeFrom, float fleeDistance, double fleeSlowSpeed, double fleeFastSpeed, Predicate<LivingEntity> inclusionSelector) {
        this(fleeingEntity, classToFleeFrom, livingEntity -> true, fleeDistance, fleeSlowSpeed, fleeFastSpeed, inclusionSelector);
    }

    @Override
    protected boolean shouldRun(ServerWorld world, PathAwareEntity entity) {
        this.targetEntity = this.mob.getWorld().getClosestEntity(this.mob.getWorld().getEntitiesByClass(this.classToFleeFrom, this.mob.getBoundingBox().expand(this.fleeDistance, 3.0, this.fleeDistance), livingEntity -> true), this.withinRangePredicate, this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ());
        if (this.targetEntity == null) {
            return false;
        }
        Vec3d vec3d = NoPenaltyTargeting.find(this.mob, 16, 7, this.targetEntity.getPos());
        if (vec3d == null) {
            return false;
        }
        if (this.targetEntity.squaredDistanceTo(vec3d.x, vec3d.y, vec3d.z) < this.targetEntity.squaredDistanceTo(this.mob)) {
            return false;
        }
        this.fleePath = this.fleeingEntityNavigation.findPathTo(vec3d.x, vec3d.y, vec3d.z, 0);
        return this.fleePath != null;
    }

    @Override
    protected void run(ServerWorld world, PathAwareEntity entity, long time) {
        if (this.mob.squaredDistanceTo(this.targetEntity) < 49.0) {
            this.mob.getNavigation().setSpeed(this.fastSpeed);
        } else {
            this.mob.getNavigation().setSpeed(this.slowSpeed);
        }
        super.run(world, entity, time);
    }
}
