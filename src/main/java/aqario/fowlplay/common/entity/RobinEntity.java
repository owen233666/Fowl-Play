package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.sound.FowlPlaySoundEvents;
import aqario.fowlplay.common.tags.FowlPlayItemTags;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.VariantProvider;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class RobinEntity extends FlyingBirdEntity implements VariantProvider<RobinEntity.Variant> {
    private static final TrackedData<String> VARIANT = DataTracker.registerData(RobinEntity.class, TrackedDataHandlerRegistry.STRING);
    public final AnimationState idleState = new AnimationState();
    public final AnimationState flapState = new AnimationState();
    public final AnimationState flyState = new AnimationState();
    public final AnimationState floatState = new AnimationState();
    private int flapAnimationTimeout = 0;

    public RobinEntity(EntityType<? extends RobinEntity> entityType, World world) {
        super(entityType, world);
        this.addPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0f);
        this.addPathfindingPenalty(PathNodeType.WATER, -1.0f);
        this.addPathfindingPenalty(PathNodeType.WATER_BORDER, -1.0f);
        this.addPathfindingPenalty(PathNodeType.DANGER_POWDER_SNOW, -1.0f);
        this.addPathfindingPenalty(PathNodeType.COCOA, -1.0f);
        this.addPathfindingPenalty(PathNodeType.FENCE, -1.0f);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VARIANT, /*Util.getRandom(Variant.VARIANTS, random).toString()*/ Variant.AMERICAN.toString());
    }

    @Override
    public Variant getVariant() {
        return Variant.valueOf(this.dataTracker.get(VARIANT));
    }

    @Override
    public void setVariant(Variant variant) {
        this.dataTracker.set(VARIANT, variant.toString());
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("variant", this.getVariant().toString());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("variant")) {
            this.setVariant(Variant.valueOf(nbt.getString("variant")));
        }
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

    public static DefaultAttributeContainer.Builder createAttributes() {
        return FlyingBirdEntity.createAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0f)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
            .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.2f);
    }

    @Override
    public Ingredient getFood() {
        return Ingredient.ofTag(FowlPlayItemTags.ROBIN_FOOD);
    }

    @Override
    public SoundEvent getEatSound(ItemStack stack) {
        return SoundEvents.ENTITY_PARROT_EAT;
    }

    @Override
    public int getFlapFrequency() {
        return 7;
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

            if (this.isFlying()) {
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
    public boolean damage(DamageSource source, float amount) {
        boolean bl = super.damage(source, amount);
        if (this.getWorld().isClient) {
            return false;
        }
        if (bl && source.getAttacker() instanceof LivingEntity entity) {
            RobinBrain.onAttacked(this, entity);
        }

        return bl;
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
        SoundEvent soundEvent = this.getAmbientSound();
        if (soundEvent == FowlPlaySoundEvents.ENTITY_ROBIN_SONG) {
            this.playSound(soundEvent, 8.0F, this.getSoundPitch());
        }
        else {
            this.playSound(soundEvent, 2.0F, this.getSoundPitch());
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (this.getWorld().isDay() && this.random.nextFloat() < 0.3F) {
            return FowlPlaySoundEvents.ENTITY_ROBIN_SONG;
        }

        return FowlPlaySoundEvents.ENTITY_ROBIN_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FowlPlaySoundEvents.ENTITY_ROBIN_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Override
    protected Brain.Profile<RobinEntity> createBrainProfile() {
        return RobinBrain.createProfile();
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return RobinBrain.create(this.createBrainProfile().deserialize(dynamic));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Brain<RobinEntity> getBrain() {
        return (Brain<RobinEntity>) super.getBrain();
    }

    @Override
    protected void mobTick() {
        this.getWorld().getProfiler().push("robinBrain");
        this.getBrain().tick((ServerWorld) this.getWorld(), this);
        this.getWorld().getProfiler().pop();
        this.getWorld().getProfiler().push("robinActivityUpdate");
        RobinBrain.reset(this);
        this.getWorld().getProfiler().pop();
        super.mobTick();
    }

    @Override
    protected void sendAiDebugData() {
        super.sendAiDebugData();
        DebugInfoSender.sendBrainDebugData(this);
    }

    public enum Variant {
        AMERICAN("american"),
        REDBREAST("redbreast");

        private final String id;

        Variant(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}
