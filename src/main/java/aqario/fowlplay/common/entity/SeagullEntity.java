package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.entity.ai.goal.FlyAroundGoal;
import aqario.fowlplay.common.entity.ai.goal.PickupItemGoal;
import aqario.fowlplay.common.sound.FowlPlaySoundEvents;
import aqario.fowlplay.common.tags.FowlPlayItemTags;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
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

public class SeagullEntity extends TrustingBirdEntity {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState flyAnimationState = new AnimationState();
    public final AnimationState floatAnimationState = new AnimationState();
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
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0f);
        this.setPathfindingPenalty(PathNodeType.DANGER_POWDER_SNOW, -1.0f);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0f);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0f);
    }

    private void setMoveControl(boolean isFlying) {
        if (isFlying) {
            this.moveControl = new FlightMoveControl(this, 10, false);
            this.isFlightMoveControl = true;
        }
        else {
            this.moveControl = new MoveControl(this);
            this.isFlightMoveControl = false;
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
    }

    @Override
    public int getFlapFrequency() {
        return 0;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0)
            .add(EntityAttributes.GENERIC_FLYING_SPEED, 1.0f)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new PickupItemGoal(this));
        this.goalSelector.add(1, new FleeEntityGoal<>(this, PlayerEntity.class, (entity) -> !this.isTrusted(entity), 6.0f, 1.4, 1.8, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR::test));
        this.goalSelector.add(2, new FlyAroundGoal(this));
        this.goalSelector.add(4, new TemptGoal(this, 1.0, this.getFood(), true));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 20.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
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

    public Ingredient getFood() {
        return Ingredient.ofTag(FowlPlayItemTags.SEAGULL_FOOD);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.5f;
    }

    @Override
    public SoundEvent getEatSound(ItemStack stack) {
        return SoundEvents.ENTITY_PARROT_EAT;
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
        this.maxWingDeviation += (float) (this.isOnGround() || this.hasVehicle() ? -1 : 4) * 0.3f;
        this.maxWingDeviation = MathHelper.clamp(this.maxWingDeviation, 0.0f, 1.0f);
        if (!this.isOnGround() && this.flapSpeed < 1.0f) {
            this.flapSpeed = 1.0f;
        }
        this.flapSpeed *= 0.9f;
        Vec3d vec3d = this.getVelocity();
        if (!this.isOnGround() && vec3d.y < 0.0) {
            this.setVelocity(vec3d.multiply(1.0, 0.6, 1.0));
        }
        this.flapProgress += this.flapSpeed * 2.0f;
    }

    private boolean isWalking() {
        return this.isOnGround() && this.getVelocity().horizontalLengthSquared() > 1.0E-6 && !this.isInsideWaterOrBubbleColumn();
    }

    @Override
    public void tick() {
        if (this.getWorld().isClient()) {
            if (this.isOnGround() && !this.isWalking()) {
                this.idleAnimationState.start(this.age);
            }
            else {
                this.idleAnimationState.stop();
            }

            if (!this.isOnGround()) {
                this.flyAnimationState.start(this.age);
            }
            else {
                this.flyAnimationState.stop();
            }

            if (this.isWalking()) {
                this.walkAnimationState.start(this.age);
            }
            else {
                this.walkAnimationState.stop();
            }

            if (this.isInsideWaterOrBubbleColumn()) {
                this.floatAnimationState.start(this.age);
            }
            else {
                this.floatAnimationState.stop();
            }
        }

        super.tick();
    }

    @Override
    protected void mobTick() {
        super.mobTick();
        if (!this.getWorld().isClient) {
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
            this.playSound(this.getAmbientSound(), 4.0F, this.getSoundPitch());
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
}
