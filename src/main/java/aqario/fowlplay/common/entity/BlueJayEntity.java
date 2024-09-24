package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.entity.ai.goal.BirdWanderGoal;
import aqario.fowlplay.common.entity.ai.goal.FlyAroundGoal;
import aqario.fowlplay.common.entity.ai.goal.PickupItemGoal;
import aqario.fowlplay.common.sound.FowlPlaySoundEvents;
import aqario.fowlplay.common.tags.FowlPlayItemTags;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BlueJayEntity extends BirdEntity {
    public final AnimationState idleState = new AnimationState();
    public final AnimationState flapState = new AnimationState();
    public final AnimationState flyState = new AnimationState();
    public final AnimationState floatState = new AnimationState();
    private int flapAnimationTimeout = 0;

    protected BlueJayEntity(EntityType<? extends BirdEntity> entityType, World world) {
        super(entityType, world);
        this.addPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0f);
        this.addPathfindingPenalty(PathNodeType.WATER, -1.0f);
        this.addPathfindingPenalty(PathNodeType.WATER_BORDER, -1.0f);
        this.addPathfindingPenalty(PathNodeType.DANGER_POWDER_SNOW, -1.0f);
        this.addPathfindingPenalty(PathNodeType.COCOA, -1.0f);
        this.addPathfindingPenalty(PathNodeType.FENCE, -1.0f);
    }

    @Override
    public Ingredient getFood() {
        return Ingredient.ofTag(FowlPlayItemTags.BLUE_JAY_FOOD);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new EscapeDangerGoal(this, 1.5));
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(2, new PickupItemGoal(this));
        this.goalSelector.add(2, new FleeEntityGoal<>(this, PlayerEntity.class, 10.0f, 1.2, 1.5, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR::test));
        this.goalSelector.add(3, new FlyAroundGoal(this));
        this.goalSelector.add(4, new BirdWanderGoal(this, 1.0));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 20.0f));
        this.goalSelector.add(6, new LookAroundGoal(this));
    }

    @Override
    public int getFlapFrequency() {
        return 7;
    }

    @Override
    public SoundEvent getEatSound(ItemStack stack) {
        return SoundEvents.ENTITY_PARROT_EAT;
    }

    @Override
    public void tick() {
        if (this.getWorld().isClient()) {
            if (!this.isOnGround()) {
                this.flapState.start(this.age);
            }
            else if (this.flapAnimationTimeout <= 0) {
                this.flapAnimationTimeout = this.getFlapFrequency();
                this.flapState.restart(this.age);
            }
            else {
                --this.flapAnimationTimeout;
            }
            if (this.isOnGround() && !this.isInsideWaterOrBubbleColumn()) {
                this.idleState.start(this.age);
            }
            else {
                this.idleState.stop();
            }

            if (!this.isOnGround()) {
                this.flyState.start(this.age);
            }
            else {
                this.flyState.stop();
            }

            if (this.isInsideWaterOrBubbleColumn()) {
                this.floatState.start(this.age);
            }
            else {
                this.floatState.stop();
            }
        }

        super.tick();
    }

    @Override
    public void playAmbientSound() {
        SoundEvent soundEvent = this.getAmbientSound();
        if (soundEvent == FowlPlaySoundEvents.ENTITY_BLUE_JAY_CALL) {
            this.playSound(soundEvent, 12.0F, this.getSoundPitch());
        }
        else {
            super.playAmbientSound();
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (this.getWorld().isDay() && this.random.nextFloat() < 0.3F) {
            return FowlPlaySoundEvents.ENTITY_BLUE_JAY_CALL;
        }

        return null;
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
