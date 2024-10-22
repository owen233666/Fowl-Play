package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.sound.FowlPlaySoundEvents;
import aqario.fowlplay.common.tags.FowlPlayItemTags;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BlueJayEntity extends FlyingBirdEntity {
    public final AnimationState idleState = new AnimationState();
    public final AnimationState glideState = new AnimationState();
    public final AnimationState flapState = new AnimationState();
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
//            if (!this.isOnGround()) {
//                this.flapState.start(this.age);
//            }
//            else if (this.flapAnimationTimeout <= 0) {
//                this.flapAnimationTimeout = this.getFlapFrequency();
//                this.flapState.restart(this.age);
//            }
//            else {
//                --this.flapAnimationTimeout;
//            }
            if (this.isOnGround() && !this.isInsideWaterOrBubbleColumn()) {
                this.idleState.start(this.age);
            }
            else {
                this.idleState.stop();
            }

            if (this.isFlying()) {
                this.flapState.start(this.age);
            }
            else {
                this.flapState.stop();
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
    public boolean damage(DamageSource source, float amount) {
        boolean bl = super.damage(source, amount);
        if (this.getWorld().isClient) {
            return false;
        }
        if (bl && source.getAttacker() instanceof LivingEntity entity) {
            BlueJayBrain.onAttacked(this, entity);
        }

        return bl;
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

    @Override
    protected Brain.Profile<BlueJayEntity> createBrainProfile() {
        return BlueJayBrain.createProfile();
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return BlueJayBrain.create(this.createBrainProfile().deserialize(dynamic));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Brain<BlueJayEntity> getBrain() {
        return (Brain<BlueJayEntity>) super.getBrain();
    }

    @Override
    protected void mobTick() {
        this.getWorld().getProfiler().push("blueJayBrain");
        this.getBrain().tick((ServerWorld) this.getWorld(), this);
        this.getWorld().getProfiler().pop();
        this.getWorld().getProfiler().push("blueJayActivityUpdate");
        BlueJayBrain.reset(this);
        this.getWorld().getProfiler().pop();
        super.mobTick();
    }

    @Override
    protected void sendAiDebugData() {
        super.sendAiDebugData();
        DebugInfoSender.sendBrainDebugData(this);
    }
}
