package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.sound.FowlPlaySoundEvents;
import aqario.fowlplay.common.tags.FowlPlayBiomeTags;
import aqario.fowlplay.common.tags.FowlPlayBlockTags;
import aqario.fowlplay.common.tags.FowlPlayItemTags;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class GullEntity extends TrustingBirdEntity implements VariantProvider<GullEntity.Variant> {
    private static final TrackedData<String> VARIANT = DataTracker.registerData(GullEntity.class, TrackedDataHandlerRegistry.STRING);
    public final AnimationState idleState = new AnimationState();
    public final AnimationState flyState = new AnimationState();
    public final AnimationState floatState = new AnimationState();

    public GullEntity(EntityType<? extends GullEntity> entityType, World world) {
        super(entityType, world);
        this.addPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0f);
        this.addPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0f);
        this.addPathfindingPenalty(PathNodeType.DANGER_POWDER_SNOW, -1.0f);
        this.addPathfindingPenalty(PathNodeType.COCOA, -1.0f);
        this.addPathfindingPenalty(PathNodeType.FENCE, -1.0f);
    }

    @SuppressWarnings("unused")
    public static boolean canSpawn(EntityType<? extends BirdEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
        return world.getBiome(pos).isIn(FowlPlayBiomeTags.SPAWNS_GULLS) && world.getBlockState(pos.down()).isIn(FowlPlayBlockTags.SHOREBIRDS_SPAWNABLE_ON);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        GullBrain.init();
        this.setVariant(Util.getRandom(Variant.VARIANTS, world.getRandom()));
        return super.initialize(world, difficulty, spawnReason, entityData);
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
        builder.add(VARIANT, Variant.HERRING.toString());
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
    public void tick() {
        if (this.getWorld().isClient()) {
            if (this.isOnGround() && !this.isInsideWaterOrBubbleColumn()) {
                this.idleState.start(this.age);
            }
            else {
                this.idleState.stop();
            }

//            if (!this.isOnGround()) {
//                this.flyState.start(this.age);
//            }
//            else {
//                this.flyState.stop();
//            }

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
        if ((!this.getWorld().isDay() && this.random.nextFloat() < 0.1F) || this.random.nextFloat() < 0.3F) {
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
