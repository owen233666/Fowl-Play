package aqario.fowlplay.common.entity.ai.goal;

import aqario.fowlplay.common.entity.BirdEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.mob.PathAwareEntity;

import java.util.function.Predicate;

public class BirdFleeEntityGoal<T extends BirdEntity> extends FleeEntityGoal<T> {
    public BirdFleeEntityGoal(PathAwareEntity mob, Class<T> fleeFromType, float distance, double slowSpeed, double fastSpeed) {
        super(mob, fleeFromType, distance, slowSpeed, fastSpeed);
    }

    public BirdFleeEntityGoal(PathAwareEntity mob, Class<T> fleeFromType, Predicate<LivingEntity> extraInclusionSelector, float distance, double slowSpeed, double fastSpeed, Predicate<LivingEntity> inclusionSelector) {
        super(mob, fleeFromType, extraInclusionSelector, distance, slowSpeed, fastSpeed, inclusionSelector);
    }

    public BirdFleeEntityGoal(PathAwareEntity fleeingEntity, Class<T> classToFleeFrom, float fleeDistance, double fleeSlowSpeed, double fleeFastSpeed, Predicate<LivingEntity> inclusionSelector) {
        super(fleeingEntity, classToFleeFrom, fleeDistance, fleeSlowSpeed, fleeFastSpeed, inclusionSelector);
    }
//    protected final AIScatter.Sorter theNearestAttackableTargetSorter;
//    protected final Predicate<? super Entity> targetEntitySelector;
//    protected int executionChance = 8;
//    protected boolean mustUpdate;
//    private Entity targetEntity;
//    private Vec3 flightTarget = null;
//    private int cooldown = 0;
//
//    AIScatter() {
//        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
//        this.theNearestAttackableTargetSorter = new AIScatter.Sorter(EntityCrow.this);
//        this.targetEntitySelector = new Predicate<Entity>() {
//            @Override
//            public boolean apply(@Nullable Entity e) {
//                return e.isAlive() && e.getType().is(AMTagRegistry.SCATTERS_CROWS) || e instanceof Player && !((Player) e).isCreative();
//            }
//        };
//    }
//
//    @Override
//    public boolean canUse() {
//        if (EntityCrow.this.isPassenger() || EntityCrow.this.aiItemFlag || EntityCrow.this.isVehicle() || EntityCrow.this.isTame()) {
//            return false;
//        }
//        if (!this.mustUpdate) {
//            final long worldTime = EntityCrow.this.level.getGameTime() % 10;
//            if (worldTime != 0) {
//                if (EntityCrow.this.getNoActionTime() >= 100) {
//                    return false;
//                }
//                if (EntityCrow.this.getRandom().nextInt(this.executionChance) != 0) {
//                    return false;
//                }
//            }
//        }
//        final List<Entity> list = EntityCrow.this.level.getEntitiesOfClass(Entity.class, this.getTargetableArea(this.getTargetDistance()), this.targetEntitySelector);
//        if (list.isEmpty()) {
//            return false;
//        } else {
//            list.sort(this.theNearestAttackableTargetSorter);
//            this.targetEntity = list.get(0);
//            this.mustUpdate = false;
//            return true;
//        }
//    }
//
//    @Override
//    public boolean canContinueToUse() {
//        return targetEntity != null && !EntityCrow.this.isTame();
//    }
//
//    public void stop() {
//        flightTarget = null;
//        this.targetEntity = null;
//    }
//
//    @Override
//    public void tick() {
//        if (cooldown > 0) {
//            cooldown--;
//        }
//        if (flightTarget != null) {
//            EntityCrow.this.setFlying(true);
//            EntityCrow.this.getMoveControl().setWantedPosition(flightTarget.x, flightTarget.y, flightTarget.z, 1F);
//            if(cooldown == 0 && EntityCrow.this.isTargetBlocked(flightTarget)){
//                cooldown = 30;
//                flightTarget = null;
//            }
//        }
//
//        if (targetEntity != null) {
//            if (EntityCrow.this.onGround || flightTarget == null || EntityCrow.this.distanceToSqr(flightTarget) < 3) {
//                final Vec3 vec = EntityCrow.this.getBlockInViewAway(targetEntity.position(), 0);
//                if (vec != null && vec.y() > EntityCrow.this.getY()) {
//                    flightTarget = vec;
//                }
//            }
//            if (EntityCrow.this.distanceTo(targetEntity) > 20.0F) {
//                this.stop();
//            }
//        }
//    }
//
//    protected double getTargetDistance() {
//        return 4D;
//    }
//
//    protected AABB getTargetableArea(double targetDistance) {
//        final Vec3 renderCenter = new Vec3(EntityCrow.this.getX(), EntityCrow.this.getY() + 0.5, EntityCrow.this.getZ());
//        final AABB aabb = new AABB(-2, -2, -2, 2, 2, 2);
//        return aabb.move(renderCenter);
//    }
//
//
//    public class Sorter implements Comparator<Entity> {
//        private final Entity theEntity;
//
//        public Sorter(Entity theEntityIn) {
//            this.theEntity = theEntityIn;
//        }
//
//        public int compare(Entity p_compare_1_, Entity p_compare_2_) {
//            final double d0 = this.theEntity.distanceToSqr(p_compare_1_);
//            final double d1 = this.theEntity.distanceToSqr(p_compare_2_);
//            return Double.compare(d0, d1);
//        }
//    }
}
