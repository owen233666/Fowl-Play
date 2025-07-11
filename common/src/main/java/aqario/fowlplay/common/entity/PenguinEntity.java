package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.ai.brain.sensor.AttackTargetSensor;
import aqario.fowlplay.common.entity.ai.brain.sensor.AttackedSensor;
import aqario.fowlplay.common.entity.ai.brain.sensor.NearbyAdultsSensor;
import aqario.fowlplay.common.entity.ai.brain.task.*;
import aqario.fowlplay.common.entity.ai.control.BirdAquaticMoveControl;
import aqario.fowlplay.common.util.Birds;
import aqario.fowlplay.core.*;
import aqario.fowlplay.core.tags.FowlPlayBiomeTags;
import aqario.fowlplay.core.tags.FowlPlayBlockTags;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import aqario.fowlplay.core.tags.FowlPlayItemTags;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.control.YawAdjustingLookControl;
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
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.BreedWithPartner;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.InvalidateMemory;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Panic;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FollowParent;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FollowTemptation;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.navigation.SmoothAmphibiousPathNavigation;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.custom.NearbyItemsSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.InWaterSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.ItemTemptingSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class PenguinEntity extends BirdEntity implements SmartBrainOwner<PenguinEntity> {
    private static final int SLIDING_TRANSITION_TICKS = (int) (0.75F * 20);
    private static final int STANDING_TRANSITION_TICKS = (int) (1.0F * 20);
    private static final long LAST_POSE_CHANGE_TICKS = 0L;
    public static final TrackedData<Long> LAST_POSE_TICK = DataTracker.registerData(PenguinEntity.class, TrackedDataHandlerRegistry.LONG);
    private static final int SWIM_PARTICLE_COUNT = 20;
    public final AnimationState standingState = new AnimationState();
    public final AnimationState slidingState = new AnimationState();
    public final AnimationState slidingTransitionState = new AnimationState();
    public final AnimationState standingTransitionState = new AnimationState();
    public final AnimationState flappingState = new AnimationState();
    public final AnimationState swimmingState = new AnimationState();
    public final AnimationState dancingState = new AnimationState();
    private boolean songPlaying;
    @Nullable
    private BlockPos songSource;

    public PenguinEntity(EntityType<? extends PenguinEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
        this.lookControl = new YawAdjustingLookControl(this, 85);
    }

    @Override
    protected float getOffGroundSpeed() {
        return this.isInsideWaterOrBubbleColumn() ? this.getMovementSpeed() : super.getOffGroundSpeed();
    }

    @Override
    public float getMovementSpeed() {
        return this.getPose() == EntityPose.SLIDING ? super.getMovementSpeed() * 1.5F : super.getMovementSpeed();
    }

    @Override
    protected MoveControl createMoveControl() {
        return new BirdAquaticMoveControl(this, 85, 15, 1.0F, 1.0F, true);
    }

    @Override
    public int getMaxLookPitchChange() {
        return this.isInsideWaterOrBubbleColumn() ? 1 : super.getMaxLookPitchChange();
    }

    @Override
    public int getMaxHeadRotation() {
        return this.isInsideWaterOrBubbleColumn() ? 1 : super.getMaxHeadRotation();
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new SmoothAmphibiousPathNavigation(this, world);
    }

    @Nullable
    @Override
    public LivingEntity getTarget() {
        return this.getTargetInBrain();
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        this.initLastPoseTick(world.toServerWorld().getTime());
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return FowlPlayEntityType.PENGUIN.get().create(world);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return this.getFood().test(stack);
    }

    @Override
    public Ingredient getFood() {
        return Ingredient.fromTag(FowlPlayItemTags.PENGUIN_FOOD);
    }

    @Override
    public boolean canHunt(LivingEntity target) {
        return target.getType().isIn(FowlPlayEntityTypeTags.PENGUIN_HUNT_TARGETS);
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().isIn(FowlPlayEntityTypeTags.PENGUIN_AVOIDS);
    }

    public static DefaultAttributeContainer.Builder createPenguinAttributes() {
        return BirdEntity.createBirdAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0f)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0f)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.145f)
            .add(EntityAttributes.GENERIC_WATER_MOVEMENT_EFFICIENCY, 1.0f);
    }

    @Override
    public void setNearbySongPlaying(BlockPos songPosition, boolean playing) {
        this.songSource = songPosition;
        this.songPlaying = playing;
    }

    @Override
    public void tickMovement() {
        if(this.songSource == null
            || !this.songSource.isWithinDistance(this.getPos(), 5)
            || !this.getWorld().getBlockState(this.songSource).isOf(Blocks.JUKEBOX)) {
            this.songPlaying = false;
            this.songSource = null;
        }

        super.tickMovement();
    }

    public boolean isSongPlaying() {
        return this.songPlaying;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(LAST_POSE_TICK, LAST_POSE_CHANGE_TICKS);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putLong("lastPoseTick", this.dataTracker.get(LAST_POSE_TICK));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        long l = nbt.getLong("lastPoseTick");
        if(l < LAST_POSE_CHANGE_TICKS) {
            this.setPose(EntityPose.SLIDING);
        }

        this.setLastPoseTick(l);
    }

    @Override
    public void onDataTrackerUpdate(List<DataTracker.SerializedEntry<?>> entries) {
        super.onDataTrackerUpdate(entries);
        this.calculateDimensions();
    }

    @Override
    public void tick() {
        if(this.getControllingPassenger() != null && this.isInsideWaterOrBubbleColumn()) {
            this.getControllingPassenger().stopRiding();
        }
        if(this.isInsideWaterOrBubbleColumn() && !this.isSliding()) {
            this.setSliding();
        }

        super.tick();

        if(this.getWorld().isClient() && this.isInsideWaterOrBubbleColumn() && this.getVelocity().lengthSquared() > 0.02) {
            this.addSwimParticles();
        }

        if(this.isSwimming()) {
            this.setPose(EntityPose.SWIMMING);
        }
        else if(this.isSliding()) {
            this.setPose(EntityPose.SLIDING);
        }
        else {
            this.setPose(EntityPose.STANDING);
        }
    }

    private void addSwimParticles() {
        Vec3d velocity = this.getRotationVector().negate().multiply(0.5);
        for(int i = 0; i < SWIM_PARTICLE_COUNT; i++) {
            this.getWorld().addParticle(
                FowlPlayParticleTypes.SMALL_BUBBLE.get(),
                this.getX() + (this.random.nextFloat() * 0.75F - 0.375F),
                (this.getY() + this.getBoundingBox().getLengthY() / 2) + (this.random.nextFloat() * 0.75F - 0.375F),
                this.getZ() + (this.random.nextFloat() * 0.75F - 0.375F),
                velocity.x,
                velocity.y,
                velocity.z
            );
        }
    }

    @Override
    protected void updateAnimations() {
        this.standingState.setRunning(this.isOnGround() && !this.isInsideWaterOrBubbleColumn() && !this.isSliding(), this.age);

        if(this.isInsideWaterOrBubbleColumn()) {
            this.standingState.stop();
            this.swimmingState.startIfNotRunning(this.age);
        }
        else {
            this.swimmingState.stop();
        }

        if(this.shouldUpdateSlidingAnimations() && !this.isInsideWaterOrBubbleColumn()) {
            this.standingState.stop();
            if(this.shouldPlaySlidingTransition()) {
                this.slidingTransitionState.startIfNotRunning(this.age);
                this.slidingState.stop();
            }
            else {
                this.slidingTransitionState.stop();
                this.slidingState.startIfNotRunning(this.age);
            }
        }
        else {
            this.slidingTransitionState.stop();
            this.slidingState.stop();
            this.standingTransitionState.setRunning(this.isChangingPose() && this.getLastPoseTickDelta() >= LAST_POSE_CHANGE_TICKS, this.age);
        }

        if(this.isSongPlaying() && this.isOnGround()) {
            this.dancingState.startIfNotRunning(this.age);
            this.setStanding();
            this.standingState.stop();
        }
        else {
            this.dancingState.stop();
        }
    }

    public boolean canStartSliding() {
        return !this.isInsideWaterOrBubbleColumn()
            && !this.hasPassengers()
            && this.isOnGround()
            && (this.getWorld().getBlockState(this.getBlockPos().down()).isIn(FowlPlayBlockTags.PENGUINS_SLIDE_ON)
            || this.getWorld().getBlockState(this.getBlockPos()).isIn(FowlPlayBlockTags.PENGUINS_SLIDE_ON));
    }

    public boolean isSliding() {
        return this.dataTracker.get(LAST_POSE_TICK) < LAST_POSE_CHANGE_TICKS;
    }

    public boolean shouldUpdateSlidingAnimations() {
        return this.getLastPoseTickDelta() < LAST_POSE_CHANGE_TICKS != this.isSliding();
    }

    public boolean isChangingPose() {
        long l = this.getLastPoseTickDelta();
        return l < (long) (this.isSliding() ? SLIDING_TRANSITION_TICKS : STANDING_TRANSITION_TICKS);
    }

    private boolean shouldPlaySlidingTransition() {
        return this.isSliding() && this.getLastPoseTickDelta() < SLIDING_TRANSITION_TICKS && this.getLastPoseTickDelta() >= LAST_POSE_CHANGE_TICKS;
    }

    public void startSliding() {
        if(!this.isSliding()) {
            this.setPose(EntityPose.SLIDING);
            this.emitGameEvent(GameEvent.ENTITY_ACTION);
            this.setLastPoseTick(-this.getWorld().getTime());
        }
    }

    public void stopSliding() {
        if(this.isSliding()) {
            this.setPose(EntityPose.STANDING);
            this.emitGameEvent(GameEvent.ENTITY_ACTION);
            this.setLastPoseTick(this.getWorld().getTime());
        }
    }

    public void setStanding() {
        this.setPose(EntityPose.STANDING);
        this.emitGameEvent(GameEvent.ENTITY_ACTION);
        this.initLastPoseTick(this.getWorld().getTime());
    }

    public void setSliding() {
        this.setPose(EntityPose.SLIDING);
        this.emitGameEvent(GameEvent.ENTITY_ACTION);
        this.setLastPoseTick(-Math.max(LAST_POSE_CHANGE_TICKS, this.getWorld().getTime() - SLIDING_TRANSITION_TICKS - 1L));
    }

    private void setLastPoseTick(long lastPoseTick) {
        this.dataTracker.set(LAST_POSE_TICK, lastPoseTick);
    }

    private void initLastPoseTick(long time) {
        this.setLastPoseTick(Math.max(LAST_POSE_CHANGE_TICKS, time - STANDING_TRANSITION_TICKS - 1L));
    }

    public long getLastPoseTickDelta() {
        return this.getWorld().getTime() - Math.abs(this.dataTracker.get(LAST_POSE_TICK));
    }

    @Override
    public void updateSwimming() {
        this.setSwimming(this.isInsideWaterOrBubbleColumn() && !this.hasVehicle());
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
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        Vec3d vec3d = getPassengerDismountOffset(this.getWidth() * MathHelper.SQUARE_ROOT_OF_TWO, passenger.getWidth(), passenger.getYaw());
        double d = this.getX() + vec3d.x;
        double e = this.getZ() + vec3d.z;
        BlockPos blockPos = new BlockPos((int) d, (int) this.getAttackBox().maxY, (int) e);
        BlockPos blockPos2 = blockPos.down();
        if(!this.getWorld().isWater(blockPos2)) {
            List<Vec3d> list = Lists.newArrayList();
            double f = this.getWorld().getDismountHeight(blockPos);
            if(Dismounting.canDismountInBlock(f)) {
                list.add(new Vec3d(d, (double) blockPos.getY() + f, e));
            }

            double g = this.getWorld().getDismountHeight(blockPos2);
            if(Dismounting.canDismountInBlock(g)) {
                list.add(new Vec3d(d, (double) blockPos2.getY() + g, e));
            }

            for(EntityPose entityPose : passenger.getPoses()) {
                for(Vec3d vec3d2 : list) {
                    if(Dismounting.canPlaceEntityAt(this.getWorld(), vec3d2, passenger, entityPose)) {
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
        return this.getPose() == EntityPose.SLIDING ? 1.1F : super.getStepHeight();
    }

    @Override
    public float getScaleFactor() {
        return this.isBaby() ? 0.62F : 1.0F;
    }

    @Override
    public EntityDimensions getBaseDimensions(EntityPose pose) {
        EntityDimensions dimensions = super.getBaseDimensions(pose);
        return pose == EntityPose.SLIDING || pose == EntityPose.SWIMMING ? dimensions.scaled(1.0F, 0.35F) : dimensions;
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

    public boolean shouldStepDown() {
        BlockPos pos = this.getBlockPos();
        return !this.isOnGround()
            && this.fallDistance > 0f
            && this.fallDistance < 0.1f
            && !this.getWorld().getBlockState(pos.down()).getCollisionShape(this.getWorld(), pos.down()).isEmpty()
            /*|| !this.getWorld().getBlockState(pos.down(2)).getCollisionShape(this.getWorld(), pos.down(2)).isEmpty()*/;
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
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
        if(touchingWater != this.isTouchingWater()) {
            this.setPose(this.isTouchingWater() ? EntityPose.SWIMMING : EntityPose.STANDING);
            this.calculateDimensions();
        }
        return bl;
    }

    @SuppressWarnings("unused")
    public static boolean canSpawnPenguins(EntityType<? extends BirdEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBiome(pos).isIn(FowlPlayBiomeTags.SPAWNS_PENGUINS) && world.getBlockState(pos.down()).isIn(FowlPlayBlockTags.PENGUINS_SPAWNABLE_ON);
    }

    @Override
    protected void tickControlled(PlayerEntity player, Vec3d input) {
        super.tickControlled(player, input);
        float sidewaysMovement = player.sidewaysSpeed;

        double rotation = 3;
        if(Math.abs(sidewaysMovement) == 0) {
            rotation = 0;
        }
        this.setRotation((float) (this.getYaw() + (rotation * (sidewaysMovement < 0 ? 1 : -1))), this.getPitch());
        player.setYaw((float) (player.getYaw() + (rotation * (sidewaysMovement < 0 ? 1 : -1))) % 360.0F);
        this.prevYaw = this.bodyYaw = this.headYaw = this.getYaw();
    }

    @Override
    protected Vec3d getControlledMovementInput(PlayerEntity player, Vec3d input) {
        float forwardMovement = player.forwardSpeed * 0.2F;
        if(this.getWorld().getBlockState(this.getVelocityAffectingPos()).isIn(FowlPlayBlockTags.PENGUINS_SLIDE_ON) || this.getBlockStateAtPos().isIn(FowlPlayBlockTags.PENGUINS_SLIDE_ON)) {
            forwardMovement *= 2.0F;
        }

        return new Vec3d(0.0, 0.0, Math.max(forwardMovement, 0));
    }

    @Override
    protected float getSaddledSpeed(PlayerEntity player) {
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
    public float getWaterline() {
        return 0F;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        boolean bl = this.isBreedingItem(player.getStackInHand(hand));
        if(!bl && !this.hasPassengers() && !player.shouldCancelInteraction() && !this.isBaby() && this.isSliding()) {
            if(!this.getWorld().isClient) {
                player.startRiding(this);
            }
            return ActionResult.success(this.getWorld().isClient);
        }
        return super.interactMob(player, hand);
    }

    @Override
    protected int computeFallDamage(float fallDistance, float damageMultiplier) {
        if(this.getPose() == EntityPose.SLIDING) {
            return (super.computeFallDamage(fallDistance, damageMultiplier) - 3) / 2;
        }
        return super.computeFallDamage(fallDistance, damageMultiplier);
    }

    @Override
    protected boolean canCall() {
        return !this.isInsideWaterOrBubbleColumn() && super.canCall();
    }

    @Nullable
    @Override
    protected SoundEvent getCallSound() {
        return this.isBaby() ? FowlPlaySoundEvents.ENTITY_PENGUIN_BABY_CALL.get() : FowlPlaySoundEvents.ENTITY_PENGUIN_CALL.get();
    }

    @Override
    protected float getCallVolume() {
        return FowlPlayConfig.getInstance().penguinCallVolume;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return FowlPlaySoundEvents.ENTITY_PENGUIN_SWIM.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FowlPlaySoundEvents.ENTITY_PENGUIN_HURT.get();
    }

    @Override
    protected Brain.Profile<PenguinEntity> createBrainProfile() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    public List<? extends ExtendedSensor<? extends PenguinEntity>> getSensors() {
        return ObjectArrayList.of(
            new NearbyLivingEntitySensor<>(),
            new NearbyPlayersSensor<>(),
            new NearbyItemsSensor<>(),
            new NearbyAdultsSensor<>(),
            new ItemTemptingSensor<PenguinEntity>()
                .temptedWith((entity, stack) -> this.getFood().test(stack)),
            new InWaterSensor<>(),
            new AttackedSensor<PenguinEntity>()
                .setScanRate(bird -> 10),
            new AttackTargetSensor<PenguinEntity>()
                .setScanRate(bird -> 2)
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public BrainActivityGroup<? extends PenguinEntity> getCoreTasks() {
        return new BrainActivityGroup<PenguinEntity>(Activity.CORE)
            .priority(0)
            .behaviours(
                new BreatheAirTask(Birds.WALK_SPEED),
                new Panic<>(),
                new PickupFoodTask<>(),
                new LookAtTarget<>()
                    .runFor(entity -> entity.getRandom().nextBetween(45, 90)),
                new MoveToWalkTarget<>()
            );
    }

    @SuppressWarnings("unchecked")
    @Override
    public BrainActivityGroup<? extends PenguinEntity> getIdleTasks() {
        return new BrainActivityGroup<PenguinEntity>(Activity.IDLE)
            .priority(10)
            .behaviours(
                SwimTasks.startSwimming(),
                new BreedWithPartner<>(),
                new FollowParent<>(),
                SetEntityLookTargetTask.create(EntityType.PLAYER),
                new FollowTemptation<>()
                    .speedMod((entity, target) -> Birds.WALK_SPEED),
                new FollowParent<>(),
                new SetRandomLookTarget<>()
                    .lookTime(entity -> entity.getRandom().nextBetween(150, 250)),
                new SetAttackTarget<PenguinEntity>()
                    .attackPredicate(Birds::canAttack),
                new OneRandomBehaviour<>(
                    Pair.of(
                        new SetRandomWalkTarget<PenguinEntity>()
                            .speedModifier((entity, target) -> Birds.WALK_SPEED)
                            .setRadius(16, 8)
                            .startCondition(Predicate.not(Birds::isPerched)),
                        2
                    ),
                    Pair.of(
                        SlideTasks.toggleSliding(20),
                        5
                    ),
                    Pair.of(
                        new Idle<PenguinEntity>()
                            .runFor(entity -> entity.getRandom().nextBetween(400, 800)),
                        5
                    ),
                    Pair.of(
                        PenguinSpecificTasks.goToWater(),
                        6
                    )
                ).startCondition(entity -> !BrainUtils.hasMemory(entity, MemoryModuleType.WALK_TARGET))
            )
            .onlyStartWithMemoryStatus(MemoryModuleType.IS_IN_WATER, MemoryModuleState.VALUE_ABSENT)
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.SEES_FOOD.get(), MemoryModuleState.VALUE_ABSENT)
            .onlyStartWithMemoryStatus(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_ABSENT);
    }

    @SuppressWarnings("unchecked")
    public BrainActivityGroup<? extends PenguinEntity> getSwimTasks() {
        return new BrainActivityGroup<PenguinEntity>(Activity.SWIM)
            .priority(10)
            .behaviours(
                SwimTasks.stopSwimming(),
                new FollowParent<>(),
                new SetAttackTarget<PenguinEntity>()
                    .attackPredicate(Birds::canAquaticAttack),
                new OneRandomBehaviour<>(
                    Pair.of(
                        GoToLandTask.create(32, Birds.WALK_SPEED),
                        5
                    ),
                    Pair.of(
                        PenguinSpecificTasks.swim(Birds.WALK_SPEED),
                        2
                    )
                ).startCondition(entity -> !BrainUtils.hasMemory(entity, MemoryModuleType.WALK_TARGET))
            )
            .onlyStartWithMemoryStatus(MemoryModuleType.IS_IN_WATER, MemoryModuleState.VALUE_PRESENT)
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.SEES_FOOD.get(), MemoryModuleState.VALUE_ABSENT)
            .onlyStartWithMemoryStatus(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_ABSENT);
    }

    @SuppressWarnings("unchecked")
    public BrainActivityGroup<? extends PenguinEntity> getPickupFoodTasks() {
        return new BrainActivityGroup<PenguinEntity>(FowlPlayActivities.PICK_UP.get())
            .priority(10)
            .behaviours(
                SlideTasks.startSliding(),
                GoToNearestWantedItemTask.create(
                    Birds::canPickupFood,
                    entity -> Birds.RUN_SPEED,
                    true,
                    Birds.ITEM_PICK_UP_RANGE
                ),
                new InvalidateMemory<PenguinEntity, Boolean>(FowlPlayMemoryModuleType.SEES_FOOD.get())
                    .invalidateIf((entity, memory) -> !Birds.canPickupFood(entity))
            )
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.SEES_FOOD.get(), MemoryModuleState.VALUE_PRESENT);
    }

    @SuppressWarnings("unchecked")
    @Override
    public BrainActivityGroup<? extends PenguinEntity> getFightTasks() {
        return new BrainActivityGroup<PenguinEntity>(Activity.FIGHT)
            .priority(10)
            .behaviours(
                new InvalidateAttackTarget<>(),
                SlideTasks.startSliding(),
                new SetWalkTargetToAttackTarget<>()
                    .speedMod((entity, target) -> Birds.RUN_SPEED),
                new AnimatableMeleeAttack<>(0),
                new InvalidateMemory<PenguinEntity, LivingEntity>(MemoryModuleType.ATTACK_TARGET)
                    .invalidateIf((entity, memory) -> LookTargetUtil.hasBreedTarget(entity))
            )
            .requireAndWipeMemoriesOnUse(MemoryModuleType.ATTACK_TARGET);
    }

    @Override
    public Map<Activity, BrainActivityGroup<? extends PenguinEntity>> getAdditionalTasks() {
        Object2ObjectOpenHashMap<Activity, BrainActivityGroup<? extends PenguinEntity>> taskList = new Object2ObjectOpenHashMap<>();
        taskList.put(Activity.SWIM, this.getSwimTasks());
        taskList.put(FowlPlayActivities.PICK_UP.get(), this.getPickupFoodTasks());
        return taskList;
    }

    @Override
    public List<Activity> getActivityPriorities() {
        return ObjectArrayList.of(
            Activity.IDLE,
            Activity.SWIM,
            FowlPlayActivities.PICK_UP.get(),
            Activity.FIGHT
        );
    }

    @Override
    protected void mobTick() {
        Brain<?> brain = this.getBrain();
        Activity activity = brain.getFirstPossibleNonCoreActivity().orElse(null);
        this.tickBrain(this);
        if(activity == Activity.FIGHT && brain.getFirstPossibleNonCoreActivity().orElse(null) != Activity.FIGHT) {
            brain.remember(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, 2400L);
        }
        super.mobTick();
    }
}
