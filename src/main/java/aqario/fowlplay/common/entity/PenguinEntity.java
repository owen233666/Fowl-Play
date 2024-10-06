package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.sound.FowlPlaySoundEvents;
import aqario.fowlplay.common.tags.FowlPlayBiomeTags;
import aqario.fowlplay.common.tags.FowlPlayBlockTags;
import aqario.fowlplay.common.tags.FowlPlayItemTags;
import com.google.common.collect.Lists;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.control.AquaticLookControl;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.pathing.AmphibiousNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PenguinEntity extends BirdEntity implements Sliding {
    public static final TrackedData<Boolean> SLIDING = DataTracker.registerData(PenguinEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Long> LAST_ANIMATION_TICK = DataTracker.registerData(PenguinEntity.class, TrackedDataHandlerRegistry.LONG);
    private boolean isAquaticMoveControl;
    public final AnimationState idleState = new AnimationState();
    public final AnimationState walkState = new AnimationState();
    public final AnimationState slideState = new AnimationState();
    public final AnimationState fallingState = new AnimationState();
    public final AnimationState standUpState = new AnimationState();
    public final AnimationState flapState = new AnimationState();
    public final AnimationState floatState = new AnimationState();

    public PenguinEntity(EntityType<? extends PenguinEntity> entityType, World world) {
        super(entityType, world);
        this.setMoveControl(false);
//        this.addPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0f);
        this.addPathfindingPenalty(PathNodeType.WATER, 0.0f);
//        this.addPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0f);
//        this.addPathfindingPenalty(PathNodeType.COCOA, -1.0f);
//        this.addPathfindingPenalty(PathNodeType.FENCE, -1.0f);
        this.lookControl = new AquaticLookControl(this, 85);
    }

    @Override
    protected float getAirSpeed() {
        return this.isInsideWaterOrBubbleColumn() ? this.getMovementSpeed() : super.getAirSpeed();
    }

    protected void setMoveControl(boolean isSwimming) {
        if (isSwimming) {
            this.moveControl = new AquaticMoveControl(this, 85, 15, 1.0F, 1.0F, true);
            this.isAquaticMoveControl = true;
        }
        else {
            this.moveControl = new MoveControl(this);
            this.isAquaticMoveControl = false;
        }
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new AmphibiousNavigation(this, world);
    }

    @Override
    public float getPathfindingFavor(BlockPos pos) {
        return 0.0F;
    }

    @Nullable
    @Override
    public LivingEntity getTarget() {
        return this.getAttackTarget();
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        PenguinBrain.init();
        this.setLastAnimationTick(world.toServerWorld().getTime());
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return FowlPlayEntityType.PENGUIN.create(world);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return this.getFood().test(stack);
    }

    @Override
    public Ingredient getFood() {
        return Ingredient.ofTag(FowlPlayItemTags.PENGUIN_FOOD);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return BirdEntity.createAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0f)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.135f)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0f)
            .add(EntityAttributes.GENERIC_WATER_MOVEMENT_EFFICIENCY, 1.0f);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(SLIDING, false);
        builder.add(LAST_ANIMATION_TICK, 0L);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putLong("lastPoseTick", this.dataTracker.get(LAST_ANIMATION_TICK));
        nbt.putBoolean("sliding", this.dataTracker.get(SLIDING));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        long l = nbt.getLong("lastPoseTick");
        if (l < 0L) {
            this.setSliding(true);
        }

        this.setLastAnimationTick(l);
    }

    @Override
    public void onTrackedDataUpdate(List<DataTracker.SerializedEntry<?>> entries) {
        super.onTrackedDataUpdate(entries);
        this.calculateDimensions();
    }

    private boolean isWalking() {
        return this.isOnGround() && this.getVelocity().horizontalLengthSquared() > 1.0E-6 && !this.isInsideWaterOrBubbleColumn();
    }

    @Override
    public void tick() {
        if (this.getPrimaryPassenger() != null && this.getPrimaryPassenger().isSubmergedInWater()) {
            this.getPrimaryPassenger().stopRiding();
        }
        if (this.getWorld().isClient()) {
            if (this.isOnGround() && !this.isInsideWaterOrBubbleColumn()) {
                this.idleState.start(this.age);
            }
            else {
                this.idleState.stop();
            }

            if (this.isWalking()) {
                this.walkState.start(this.age);
            }
            else {
                this.walkState.stop();
            }

            if (this.isInsideWaterOrBubbleColumn()) {
                this.floatState.start(this.age);
            }
            else {
                this.floatState.stop();
            }

            if (this.isVisuallyFallingDown()) {
                this.idleState.stop();
                if (this.isVisuallySliding()) {
                    this.slideState.start(this.age);
                    this.fallingState.stop();
                }
                else {
                    this.slideState.stop();
                    this.fallingState.start(this.age);
                }
            }
            else {
                this.slideState.stop();
                this.fallingState.stop();
                this.standUpState.animateIf(this.isInAnimationTransition() && this.getAnimationTicks() >= 0L, this.age);
            }
        }

        if (!this.getWorld().isClient) {
            if (this.isSliding() && this.isInsideWaterOrBubbleColumn()) {
                this.standUp();
            }
            if (this.isInsideWaterOrBubbleColumn() != this.isAquaticMoveControl) {
                this.setMoveControl(this.isInsideWaterOrBubbleColumn());
            }
        }

        super.tick();

        if (this.getWorld().isClient && this.isInsideWaterOrBubbleColumn() && this.getVelocity().lengthSquared() > 0.02) {
            this.addSwimParticles();
        }
    }

    private void addSwimParticles() {
        for (int i = 0; i < 20; i++) {
            this.getWorld().addParticle(
                ParticleTypes.DOLPHIN,
                (this.getX() + (this.random.nextFloat() * 0.5F - 0.25F)),
                (this.getY() + this.getBounds().getYLength() / 2) + (this.random.nextFloat() * 0.5F - 0.25F),
                (this.getZ() + (this.random.nextFloat() * 0.5F - 0.25F)),
                0.0,
                0.0,
                0.0
            );
        }
    }

    protected void clampPassengerYaw(Entity entity) {
        entity.setBodyYaw(this.getYaw());
        float f = MathHelper.wrapDegrees(entity.getYaw() - this.getYaw());
        float g = MathHelper.clamp(f, -105.0F, 105.0F);
        entity.prevYaw += g - f;
        entity.setYaw(entity.getYaw() + g - f);
        entity.setHeadYaw(entity.getYaw());
    }

    @Override
    public void onPassengerLookAround(Entity passenger) {
        this.clampPassengerYaw(passenger);
    }

    @Override
    public void updatePassengerPosition(Entity passenger, Entity.PositionUpdater positionUpdater) {
        super.updatePassengerPosition(passenger, positionUpdater);
        this.clampPassengerYaw(passenger);
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        Vec3d vec3d = getPassengerDismountOffset(this.getWidth() * MathHelper.SQUARE_ROOT_OF_TWO, passenger.getWidth(), passenger.getYaw());
        double d = this.getX() + vec3d.x;
        double e = this.getZ() + vec3d.z;
        BlockPos blockPos = new BlockPos((int) d, (int) this.getBoundingBox().maxY, (int) e);
        BlockPos blockPos2 = blockPos.down();
        if (!this.getWorld().isWater(blockPos2)) {
            List<Vec3d> list = Lists.newArrayList();
            double f = this.getWorld().getDismountHeight(blockPos);
            if (Dismounting.canDismountInBlock(f)) {
                list.add(new Vec3d(d, (double) blockPos.getY() + f, e));
            }

            double g = this.getWorld().getDismountHeight(blockPos2);
            if (Dismounting.canDismountInBlock(g)) {
                list.add(new Vec3d(d, (double) blockPos2.getY() + g, e));
            }

            for (EntityPose entityPose : passenger.getPoses()) {
                for (Vec3d vec3d2 : list) {
                    if (Dismounting.canPlaceEntityAt(this.getWorld(), vec3d2, passenger, entityPose)) {
                        passenger.setPose(entityPose);
                        return vec3d2;
                    }
                }
            }
        }

        return super.updatePassengerForDismount(passenger);
    }

    @Override
    public float getStepHeight() {
        return this.isSliding() ? 1.1F : super.getStepHeight();
    }

    @Override
    public EntityDimensions getDefaultDimensions(EntityPose pose) {
        EntityDimensions entityDimensions = super.getDefaultDimensions(pose);
        return this.isSliding() || this.isInsideWaterOrBubbleColumn() ? entityDimensions.scaled(1.0F, 0.35F) : entityDimensions;
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return !this.hasPassengers();
    }

    protected boolean canBreed() {
        return !this.hasPassengers() && !this.hasVehicle() && !this.isBaby() && this.getHealth() >= this.getMaxHealth() && this.isInLove();
    }

    @Override
    public boolean canBreedWith(AnimalEntity other) {
        return other != this && other instanceof PenguinEntity penguin && this.canBreed() && penguin.canBreed();
    }

    @Nullable
    @Override
    public LivingEntity getPrimaryPassenger() {
        return (LivingEntity) this.getFirstPassenger();
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return super.canAddPassenger(passenger) && !this.isSubmergedInWater();
    }

    @Override
    protected boolean updateWaterState() {
        boolean touchingWater = this.isTouchingWater();
        boolean bl = super.updateWaterState();
        if (touchingWater != this.isTouchingWater()) {
            this.calculateDimensions();
        }
        return bl;
    }

    @SuppressWarnings("unused")
    public static boolean canSpawn(EntityType<? extends BirdEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
        return world.getBiome(pos).isIn(FowlPlayBiomeTags.SPAWNS_PENGUINS) && world.getBlockState(pos.down()).isIn(FowlPlayBlockTags.PENGUINS_SPAWNABLE_ON);
    }

    @Override
    protected void tickControlled(PlayerEntity player, Vec3d input) {
        super.tickControlled(player, input);
        float sidewaysMovement = player.sidewaysSpeed;

        double rotation = 3;
        if (Math.abs(sidewaysMovement) == 0) {
            rotation = 0;
        }
        this.setRotation((float) (this.getYaw() + (rotation * (sidewaysMovement < 0 ? 1 : -1))), this.getPitch());
        player.setYaw((float) (player.getYaw() + (rotation * (sidewaysMovement < 0 ? 1 : -1))) % 360.0F);
        this.prevYaw = this.bodyYaw = this.headYaw = this.getYaw();
    }

    @Override
    protected Vec3d getControlledMovementInput(PlayerEntity player, Vec3d input) {
        float forwardMovement = player.forwardSpeed * 0.2F;
        if (this.getWorld().getBlockState(this.getVelocityAffectingPos()).isIn(FowlPlayBlockTags.PENGUINS_SLIDE_ON) || this.getBlockStateAtPos().isIn(FowlPlayBlockTags.PENGUINS_SLIDE_ON)) {
            forwardMovement *= 2.0F;
        }

        return new Vec3d(0.0, 0.0, Math.max(forwardMovement, 0));
    }

    @Override
    protected float getRiddenSpeed(PlayerEntity player) {
        return (float) this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
    }

    @Override
    public int getMaxAir() {
        return 9600;
    }

    @Override
    protected int getNextAirOnLand(int air) {
        return this.getMaxAir();
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        boolean bl = this.isBreedingItem(player.getStackInHand(hand));
        if (!bl && !this.hasPassengers() && !player.shouldCancelInteraction() && !this.isBaby() && this.isSliding()) {
            if (!this.getWorld().isClient) {
                player.startRiding(this);
            }
            return ActionResult.success(this.getWorld().isClient);
        }
        return super.interactMob(player, hand);
    }

    public boolean isSliding() {
        return this.dataTracker.get(LAST_ANIMATION_TICK) < 0L;
    }

    public boolean isVisuallyFallingDown() {
        return this.getAnimationTicks() < 0L != this.isSliding();
    }

    public boolean isInAnimationTransition() {
        long l = this.getAnimationTicks();
        return l < (long) (this.isSliding() ? 40 : 52);
    }

    private boolean isVisuallySliding() {
        return this.isSliding() && this.getAnimationTicks() < 40L && this.getAnimationTicks() >= 0L;
    }

    public void startSliding() {
        if (!this.isSliding()) {
            this.playSound(SoundEvents.ENTITY_CAMEL_SIT, 1.0F, 1.0F);
            this.setSliding(true);
            this.setLastAnimationTick(-this.getWorld().getTime());
        }
    }

    public void standUp() {
        if (this.isSliding()) {
            this.playSound(SoundEvents.ENTITY_CAMEL_STAND, 1.0F, 1.0F);
            this.setSliding(false);
            this.setLastAnimationTick(this.getWorld().getTime());
        }
    }

    public void setSliding(boolean sliding) {
        this.dataTracker.set(SLIDING, sliding);
    }

    public long getAnimationTicks() {
        return this.getWorld().getTime() - Math.abs(this.dataTracker.get(LAST_ANIMATION_TICK));
    }

    public void setLastAnimationTick(long tick) {
        this.dataTracker.set(LAST_ANIMATION_TICK, tick);
    }

    @Override
    protected int computeFallDamage(float fallDistance, float damageMultiplier) {
        if (this.isSliding()) {
            return (super.computeFallDamage(fallDistance, damageMultiplier) * 2 - 3) / 2;
        }
        return super.computeFallDamage(fallDistance, damageMultiplier) * 2;
    }

    @Override
    public void playAmbientSound() {
        SoundEvent soundEvent = this.getAmbientSound();
        if (soundEvent == FowlPlaySoundEvents.ENTITY_PENGUIN_AMBIENT) {
            this.playSound(soundEvent, 4.0F, this.getSoundPitch());
        }
        else {
            super.playAmbientSound();
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (!this.isInsideWaterOrBubbleColumn() && this.random.nextFloat() < 0.1F) {
            return this.isBaby() ? FowlPlaySoundEvents.ENTITY_PENGUIN_BABY_AMBIENT : FowlPlaySoundEvents.ENTITY_PENGUIN_AMBIENT;
        }

        return null;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return FowlPlaySoundEvents.ENTITY_PENGUIN_SWIM;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FowlPlaySoundEvents.ENTITY_PENGUIN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FowlPlaySoundEvents.ENTITY_PENGUIN_DEATH;
    }

    @Override
    protected Brain.Profile<PenguinEntity> createBrainProfile() {
        return PenguinBrain.createProfile();
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return PenguinBrain.create(this.createBrainProfile().deserialize(dynamic));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Brain<PenguinEntity> getBrain() {
        return (Brain<PenguinEntity>) super.getBrain();
    }

    @Override
    protected void mobTick() {
        this.getWorld().getProfiler().push("penguinBrain");
        this.getBrain().tick((ServerWorld) this.getWorld(), this);
        this.getWorld().getProfiler().pop();
        this.getWorld().getProfiler().push("penguinActivityUpdate");
        PenguinBrain.reset(this);
        this.getWorld().getProfiler().pop();
        super.mobTick();
    }

    @Override
    protected void sendAiDebugData() {
        super.sendAiDebugData();
        DebugInfoSender.sendBrainDebugData(this);
    }
}
