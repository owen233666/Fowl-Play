package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.entity.ai.brain.GullBrain;
import aqario.fowlplay.common.entity.ai.control.BirdFlightMoveControl;
import aqario.fowlplay.common.entity.ai.control.BirdMoveControl;
import aqario.fowlplay.common.entity.ai.pathing.BirdNavigation;
import aqario.fowlplay.common.sound.FowlPlaySoundEvents;
import aqario.fowlplay.common.tags.FowlPlayItemTags;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class GullEntity extends TrustingBirdEntity implements VariantProvider<GullEntity.Variant> {
    private static final TrackedData<String> VARIANT = DataTracker.registerData(GullEntity.class, TrackedDataHandlerRegistry.STRING);
    public final AnimationState idleState = new AnimationState();
    public final AnimationState flyState = new AnimationState();
    public final AnimationState floatState = new AnimationState();
    public float flapProgress;
    public float maxWingDeviation;
    public float prevMaxWingDeviation;
    public float prevFlapProgress;
    private boolean isFlightMoveControl;
    public float flapSpeed = 1.0f;

    public GullEntity(EntityType<? extends GullEntity> entityType, World world) {
        super(entityType, world);
//        this.setMoveControl(false);
        this.addPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0f);
        this.addPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0f);
        this.addPathfindingPenalty(PathNodeType.DANGER_POWDER_SNOW, -1.0f);
        this.addPathfindingPenalty(PathNodeType.COCOA, -1.0f);
        this.addPathfindingPenalty(PathNodeType.FENCE, -1.0f);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        GullBrain.init();
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    private void setMoveControl(boolean isFlying) {
        if (isFlying) {
            this.moveControl = new BirdFlightMoveControl(this, 40);
            BirdNavigation birdNavigation = new BirdNavigation(this, getWorld());
            birdNavigation.setCanPathThroughDoors(false);
            birdNavigation.setCanEnterOpenDoors(true);
            birdNavigation.setCanSwim(false);
            this.navigation = birdNavigation;
            this.isFlightMoveControl = true;
        }
        else {
            this.moveControl = new BirdMoveControl(this);
            this.navigation = new MobNavigation(this, getWorld());
            this.isFlightMoveControl = false;
        }
    }

    @Override
    public int getFlapFrequency() {
        return 0;
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.225f)
            .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.2f);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VARIANT, Util.getRandom(Variant.VARIANTS, this.random).toString());
    }

    @Override
    public GullEntity.Variant getVariant() {
        return GullEntity.Variant.valueOf(this.dataTracker.get(VARIANT));
    }

    @Override
    public void setVariant(GullEntity.Variant variant) {
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
            this.setVariant(GullEntity.Variant.valueOf(nbt.getString("variant")));
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean bl = super.damage(source, amount);
        if (this.getWorld().isClient) {
            return false;
        }
        else {
            if (bl && source.getAttacker() instanceof LivingEntity entity) {
                GullBrain.onAttacked(this, entity);
            }

            return bl;
        }
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
        return Ingredient.ofTag(FowlPlayItemTags.GULL_FOOD);
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

    @Override
    public void tick() {
        if (this.getWorld().isClient()) {
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
    protected void addFlapEffects() {
        this.playSound(SoundEvents.ENTITY_PARROT_FLY, 0.15f, 1.0f);
    }

    @Override
    public Vec3d getLeashOffset() {
        return new Vec3d(0.0, 0.5f * this.getStandingEyeHeight(), this.getWidth() * 0.4f);
    }

    @Override
    public void playAmbientSound() {
        if (this.random.nextFloat() < 0.3F) {
            this.playSound(this.getAmbientSound(), 8.0F, this.getSoundPitch());
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FowlPlaySoundEvents.ENTITY_GULL_AMBIENT;
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
    protected Brain.Profile<GullEntity> createBrainProfile() {
        return GullBrain.createProfile();
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return GullBrain.create(this.createBrainProfile().deserialize(dynamic));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Brain<GullEntity> getBrain() {
        return (Brain<GullEntity>) super.getBrain();
    }

    @Override
    protected void mobTick() {
        this.getWorld().getProfiler().push("gullBrain");
        this.getBrain().tick((ServerWorld) this.getWorld(), this);
        this.getWorld().getProfiler().pop();
        this.getWorld().getProfiler().push("gullActivityUpdate");
        GullBrain.reset(this);
        this.getWorld().getProfiler().pop();
        super.mobTick();

//        if (!this.getWorld().isClient) {
//            if (this.isFlying() != this.isFlightMoveControl) {
//                this.setMoveControl(this.isFlying());
//            }
//        }
    }

    @Override
    protected void sendAiDebugData() {
        super.sendAiDebugData();
        DebugInfoSender.sendBrainDebugData(this);
    }

    public enum Variant {
        HERRING("herring"),
        RING_BILLED("ring_billed");

        public static final List<GullEntity.Variant> VARIANTS = List.of(Arrays.stream(values())
            .toArray(GullEntity.Variant[]::new));

        private final String id;

        Variant(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}
