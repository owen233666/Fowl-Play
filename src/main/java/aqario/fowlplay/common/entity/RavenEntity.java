package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import aqario.fowlplay.core.FowlPlaySoundEvents;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import aqario.fowlplay.core.tags.FowlPlayItemTags;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class RavenEntity extends TrustingBirdEntity {
    public final AnimationState standingState = new AnimationState();
    public final AnimationState glidingState = new AnimationState();
    public final AnimationState floatingState = new AnimationState();

    public RavenEntity(EntityType<? extends RavenEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public int getFlapFrequency() {
        return 0;
    }

    public static DefaultAttributeContainer.Builder createRavenAttributes() {
        return FlyingBirdEntity.createFlyingBirdAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0f)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0f)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.225f)
            .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.24f);
    }

    @Override
    public int getMaxYawChange() {
        return 18;
    }

    @Nullable
    @Override
    public LivingEntity getTarget() {
        return this.getTargetInBrain();
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
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
        return Ingredient.fromTag(FowlPlayItemTags.RAVEN_FOOD);
    }

    @Override
    public boolean canHunt(LivingEntity target) {
        return target.getType().isIn(FowlPlayEntityTypeTags.RAVEN_HUNT_TARGETS) ||
            (target.getType().isIn(FowlPlayEntityTypeTags.RAVEN_BABY_HUNT_TARGETS) && target.isBaby());
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        if (this.hasLowHealth()) {
            return false;
        }
        Brain<RavenEntity> brain = this.getBrain();
        Optional<LivingEntity> hurtBy = this.getBrain().getOptionalRegisteredMemory(MemoryModuleType.HURT_BY_ENTITY);
        if (!target.getType().isIn(FowlPlayEntityTypeTags.RAVEN_ATTACK_TARGETS) && (hurtBy.isEmpty() || !hurtBy.get().equals(target))) {
            return false;
        }
        Optional<List<? extends PassiveEntity>> nearbyAdults = brain.getOptionalRegisteredMemory(FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS);
        return nearbyAdults.filter(passiveEntities -> passiveEntities.size() >= 2).isPresent();
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().isIn(FowlPlayEntityTypeTags.RAVEN_AVOIDS);
    }

    @Override
    public void tick() {
        if (this.getWorld().isClient()) {
            this.standingState.setRunning(!this.isFlying() && !this.isInsideWaterOrBubbleColumn(), this.age);
            this.glidingState.setRunning(this.isFlying(), this.age);
            this.floatingState.setRunning(!this.isFlying() && this.isInsideWaterOrBubbleColumn(), this.age);
        }

        super.tick();
    }

    @Override
    public float getWaterline() {
        return 0.5F;
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
        return FowlPlaySoundEvents.ENTITY_RAVEN_CALL;
    }

    @Override
    protected float getCallVolume() {
        return FowlPlayConfig.getInstance().ravenCallVolume;
    }

    @Override
    public int getCallDelay() {
        return 1200;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FowlPlaySoundEvents.ENTITY_RAVEN_HURT;
    }

    @Override
    protected Brain.Profile<RavenEntity> createBrainProfile() {
        return RavenBrain.createProfile();
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return RavenBrain.create(this.createBrainProfile().deserialize(dynamic));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Brain<RavenEntity> getBrain() {
        return (Brain<RavenEntity>) super.getBrain();
    }

    @Override
    protected void mobTick() {
        this.getWorld().getProfiler().push("ravenBrain");
        this.getBrain().tick((ServerWorld) this.getWorld(), this);
        this.getWorld().getProfiler().pop();
        this.getWorld().getProfiler().push("ravenActivityUpdate");
        RavenBrain.reset(this);
        this.getWorld().getProfiler().pop();
        super.mobTick();
    }
}
