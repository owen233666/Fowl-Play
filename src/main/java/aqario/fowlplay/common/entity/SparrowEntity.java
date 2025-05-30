package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.core.FowlPlaySoundEvents;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import aqario.fowlplay.core.tags.FowlPlayItemTags;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SparrowEntity extends FlyingBirdEntity implements Flocking {
    public final AnimationState standingState = new AnimationState();
    public final AnimationState glidingState = new AnimationState();
    public final AnimationState flappingState = new AnimationState();
    public final AnimationState floatingState = new AnimationState();
    public final AnimationState scratchingState = new AnimationState();
    public final AnimationState preeningState = new AnimationState();
    private int timeSinceLastFlap = this.getFlapFrequency();
    private static final int FLAP_DURATION = 8;
    private int flapTime = 0;

    public SparrowEntity(EntityType<? extends SparrowEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0f);
        this.setPathfindingPenalty(PathNodeType.WATER, -10.0f);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, -1.0f);
        this.setPathfindingPenalty(PathNodeType.DANGER_POWDER_SNOW, -1.0f);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0f);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0f);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    @Override
    public Ingredient getFood() {
        return Ingredient.fromTag(FowlPlayItemTags.SPARROW_FOOD);
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().isIn(FowlPlayEntityTypeTags.SPARROW_AVOIDS);
    }

    @Override
    public int getFlapFrequency() {
        return 1;
    }

    @Override
    public void tick() {
        super.tick();
    }

    private boolean isMoving() {
        return this.limbAnimator.isLimbMoving();
    }

    @Override
    protected void updateAnimations() {
        // on land
        if (!this.isFlying() && !this.isInsideWaterOrBubbleColumn()) {
            if (this.random.nextInt(1000) < this.idleAnimationChance++ && !this.isMoving()) {
                this.resetIdleAnimationDelay();
                this.standingState.stop();
                this.preeningState.stop();
                this.scratchingState.stop();
                if (this.getRandom().nextFloat() < 0.75f) {
                    this.preeningState.start(this.age);
                }
                else {
                    this.scratchingState.start(this.age);
                }
            }
            else if (this.isMoving()) {
                this.preeningState.stop();
                this.scratchingState.stop();
            }
            if (!(this.preeningState.isRunning() || this.scratchingState.isRunning())) {
                this.standingState.startIfNotRunning(this.age);
            }
            else {
                this.standingState.stop();
            }
        }
        else {
            this.standingState.stop();
            this.preeningState.stop();
            this.scratchingState.stop();
        }
        // flying
        if (this.isFlying()) {
            if (this.timeSinceLastFlap >= this.getFlapFrequency()) {
                this.timeSinceLastFlap = 0;
                this.flapTime++;
            }
            else if (this.flapTime >= 0 && this.flapTime < FLAP_DURATION) {
                this.flapTime++;
                this.glidingState.stop();
                this.flappingState.startIfNotRunning(this.age);
            }
            else {
                this.timeSinceLastFlap++;
                this.flapTime = 0;
                this.flappingState.stop();
                this.glidingState.startIfNotRunning(this.age);
            }
        }
        else {
            this.timeSinceLastFlap = this.getFlapFrequency();
            this.flapTime = 0;
            this.flappingState.stop();
            this.glidingState.stop();
        }
        // in water
        this.floatingState.setRunning(!this.isFlying() && this.isInsideWaterOrBubbleColumn(), this.age);
    }

    @Override
    protected int getIdleAnimationDelay() {
        return 400;
    }

    @Override
    public float getWaterline() {
        return 0.45F;
    }

    @Override
    protected void addFlapEffects() {
        this.playSound(SoundEvents.ENTITY_PARROT_FLY, 0.15f, 1.0f);
    }

    @Override
    public Vec3d getLeashOffset() {
        return new Vec3d(0.0, 0.5f * this.getStandingEyeHeight(), this.getWidth() * 0.4f);
    }

    @Nullable
    @Override
    protected SoundEvent getCallSound() {
        return FowlPlaySoundEvents.ENTITY_SPARROW_CALL;
    }

    @Nullable
    @Override
    protected SoundEvent getSongSound() {
        return FowlPlaySoundEvents.ENTITY_SPARROW_SONG;
    }

    @Override
    protected float getCallVolume() {
        return FowlPlayConfig.getInstance().sparrowCallVolume;
    }

    @Override
    protected float getSongVolume() {
        return FowlPlayConfig.getInstance().sparrowSongVolume;
    }

    @Override
    public int getCallDelay() {
        return 120;
    }

    @Override
    public int getSongDelay() {
        return 360;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FowlPlaySoundEvents.ENTITY_SPARROW_HURT;
    }

    @Override
    protected Brain.Profile<SparrowEntity> createBrainProfile() {
        return SparrowBrain.createProfile();
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return SparrowBrain.create(this.createBrainProfile().deserialize(dynamic));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Brain<SparrowEntity> getBrain() {
        return (Brain<SparrowEntity>) super.getBrain();
    }

    @Override
    protected void mobTick() {
        this.getWorld().getProfiler().push("sparrowBrain");
        this.getBrain().tick((ServerWorld) this.getWorld(), this);
        this.getWorld().getProfiler().pop();
        this.getWorld().getProfiler().push("sparrowActivityUpdate");
        SparrowBrain.reset(this);
        this.getWorld().getProfiler().pop();
        super.mobTick();
    }

    @Override
    public boolean isLeader() {
        return false;
    }

    @Override
    public void setLeader() {
    }
}
