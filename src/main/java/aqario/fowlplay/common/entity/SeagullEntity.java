package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.entity.ai.goal.PickupItemGoal;
import aqario.fowlplay.common.sound.FowlPlaySoundEvents;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.core.manager.SingletonAnimationFactory;

public class SeagullEntity extends TrustingBirdEntity implements IAnimatable {
    private final AnimationFactory factory = new SingletonAnimationFactory(this);
    public float flapProgress;
    public float maxWingDeviation;
    public float prevMaxWingDeviation;
    public float prevFlapProgress;
    private boolean isFlightMoveControl;
    public float flapSpeed = 1.0f;

    public SeagullEntity(EntityType<? extends SeagullEntity> entityType, World world) {
        super(entityType, world);
        this.setMoveControl(false);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0f);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0f);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, -1.0f);
        this.setPathfindingPenalty(PathNodeType.DANGER_POWDER_SNOW, -1.0f);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0f);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0f);
    }

    private void setMoveControl(boolean isFlying) {
        if (isFlying) {
            this.moveControl = new FlightMoveControl(this, 10, false);
            this.isFlightMoveControl = true;
        } else {
            this.moveControl = new MoveControl(this);
            this.isFlightMoveControl = false;
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0)
            .add(EntityAttributes.GENERIC_FLYING_SPEED, 1.0f)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(2, new PickupItemGoal(this));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, PlayerEntity.class, (entity) -> !this.isTrusted(entity.getUuid()), 6.0f, 1.4, 1.8, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR::test));
        this.goalSelector.add(4, new TemptGoal(this, 1.0, this.getTemptItems(), true));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 20.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    public Ingredient getTemptItems() {
        return Ingredient.ofTag(ConventionalItemTags.FOODS);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        if (!this.isFlying()) {
            MobNavigation mobNavigation = new MobNavigation(this, world);
            mobNavigation.setCanPathThroughDoors(false);
            mobNavigation.setCanSwim(true);
            mobNavigation.setCanEnterOpenDoors(true);
            return mobNavigation;
        }
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.5f;
    }

    @Override
    public SoundEvent getEatSound(ItemStack stack) {
        return SoundEvents.ENTITY_GENERIC_EAT;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.isFlying()) {
            this.flapWings();
        }
    }

    private void flapWings() {
        this.prevFlapProgress = this.flapProgress;
        this.prevMaxWingDeviation = this.maxWingDeviation;
        this.maxWingDeviation += (float) (this.onGround || this.hasVehicle() ? -1 : 4) * 0.3f;
        this.maxWingDeviation = MathHelper.clamp(this.maxWingDeviation, 0.0f, 1.0f);
        if (!this.onGround && this.flapSpeed < 1.0f) {
            this.flapSpeed = 1.0f;
        }
        this.flapSpeed *= 0.9f;
        Vec3d vec3d = this.getVelocity();
        if (!this.onGround && vec3d.y < 0.0) {
            this.setVelocity(vec3d.multiply(1.0, 0.6, 1.0));
        }
        this.flapProgress += this.flapSpeed * 2.0f;
    }

    @Override
    protected void mobTick() {
        super.mobTick();
        if (!this.world.isClient) {
            if (this.isFlying() != this.isFlightMoveControl) {
                this.setMoveControl(this.isFlying());
            }
        }
    }

    @Override
    protected void addFlapEffects() {
        this.playSound(SoundEvents.ENTITY_PARROT_FLY, 0.15f, 1.0f);
    }

    @Override
    public Vec3d getLeashOffset() {
        return new Vec3d(0.0, 0.5f * this.getStandingEyeHeight(), this.getWidth() * 0.4f);
    }

    @Override
    public void playAmbientSound() {
        if (this.random.nextFloat() < 0.1F) {
            this.playSound(this.getAmbientSound(), 3.0F, this.getSoundPitch());
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FowlPlaySoundEvents.ENTITY_SEAGULL_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    public boolean isMoving(AnimationEvent<SeagullEntity> event) {
        float limbSwingAmount = event.getLimbSwingAmount();
        return Math.abs(limbSwingAmount) >= 0.05F;
    }

    private PlayState predicate(AnimationEvent<SeagullEntity> event) {
        if (this.isFlying()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.seagull.flying", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
//        if (this.isTouchingWater()) {
//            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.seagull.idle", ILoopType.EDefaultLoopTypes.LOOP));
//            return PlayState.CONTINUE;
//        }
        if (this.isMoving(event)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.seagull.walk", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.seagull.idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 4, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
