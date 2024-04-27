package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.entity.ai.brain.PenguinBrain;
import aqario.fowlplay.common.entity.ai.brain.sensor.FowlPlaySensorType;
import aqario.fowlplay.common.sound.FowlPlaySoundEvents;
import aqario.fowlplay.common.tags.FowlPlayItemTags;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.serialization.Dynamic;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PenguinEntity extends BirdEntity implements Saddleable {
    protected static final ImmutableList<SensorType<? extends Sensor<? super PenguinEntity>>> SENSORS;
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;
    protected static final TrackedData<Boolean> SLIDING = DataTracker.registerData(PenguinEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState flyAnimationState = new AnimationState();
    public final AnimationState floatAnimationState = new AnimationState();
    public int fleeTime = 0;
    private boolean pressingLeft;
    private boolean pressingRight;
    private boolean pressingForward;
    private boolean pressingBack;

    public PenguinEntity(EntityType<? extends PenguinEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 4.0F);
        this.moveControl = new AquaticMoveControl(this, 85, 10, 1.5F, 1F, false);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0f);
        this.setPathfindingPenalty(PathNodeType.WATER, 12.0f);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0f);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0f);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0f);
        this.stepHeight = 1.0F;
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new MobNavigation(this, world);
    }

    private boolean isWalking() {
        return this.onGround && this.getVelocity().horizontalLengthSquared() > 1.0E-6 && !this.isInsideWaterOrBubbleColumn();
    }

    @Override
    public void tick() {
        if (this.world.isClient()) {
            if (this.isOnGround() && !this.isWalking()) {
                this.idleAnimationState.start(this.age);
            } else {
                this.idleAnimationState.stop();
            }

            if (!this.isOnGround()) {
                this.flyAnimationState.start(this.age);
            } else {
                this.flyAnimationState.stop();
            }

            if (this.isWalking()) {
                this.walkAnimationState.start(this.age);
            } else {
                this.walkAnimationState.stop();
            }

            if (this.isInsideWaterOrBubbleColumn()) {
                this.floatAnimationState.start(this.age);
            } else {
                this.floatAnimationState.stop();
            }
        }
        if (this.world.isClient() && !getPassengerList().isEmpty()) {
            Entity pilot = getPassengerList().get(0);
            MinecraftClient client = MinecraftClient.getInstance();
            if (pilot instanceof ClientPlayerEntity) {
                this.setInputs(client.options.leftKey.isPressed(), client.options.rightKey.isPressed(), client.options.forwardKey.isPressed(), client.options.backKey.isPressed());
            }
        }
        super.tick();
    }

    protected void copyEntityData(Entity entity) {
        entity.setBodyYaw(this.getYaw());
        float f = MathHelper.wrapDegrees(entity.getYaw() - this.getYaw());
        float g = MathHelper.clamp(f, -105.0F, 105.0F);
        entity.prevYaw += g - f;
        entity.setYaw(entity.getYaw() + g - f);
        entity.setHeadYaw(entity.getYaw());
    }

    @Override
    public Direction getMovementDirection() {
        return this.getHorizontalFacing().rotateYClockwise();
    }

    @Override
    public void onPassengerLookAround(Entity passenger) {
        this.copyEntityData(passenger);
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        if (!this.hasPassenger(passenger)) {
            return;
        }
        float g = (float) ((this.isRemoved() ? 0.01F : this.getMountedHeightOffset()) + passenger.getHeightOffset());

        Vec3d vec3d = new Vec3d(this.getMountedXOffset(), 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - (float) (Math.PI / 2));
        passenger.setPosition(this.getX() + vec3d.x, this.getY() + (double) g, this.getZ() + vec3d.z);
        passenger.setYaw(passenger.getYaw());
        passenger.setHeadYaw(passenger.getHeadYaw());
        this.copyEntityData(passenger);
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        Vec3d vec3d = getPassengerDismountOffset(this.getWidth() * MathHelper.SQUARE_ROOT_OF_TWO, passenger.getWidth(), passenger.getYaw());
        double d = this.getX() + vec3d.x;
        double e = this.getZ() + vec3d.z;
        BlockPos blockPos = new BlockPos(d, this.getBoundingBox().maxY, e);
        BlockPos blockPos2 = blockPos.down();
        if (!this.world.isWater(blockPos2)) {
            List<Vec3d> list = Lists.newArrayList();
            double f = this.world.getDismountHeight(blockPos);
            if (Dismounting.canDismountInBlock(f)) {
                list.add(new Vec3d(d, (double) blockPos.getY() + f, e));
            }

            double g = this.world.getDismountHeight(blockPos2);
            if (Dismounting.canDismountInBlock(g)) {
                list.add(new Vec3d(d, (double) blockPos2.getY() + g, e));
            }

            for (EntityPose entityPose : passenger.getPoses()) {
                for (Vec3d vec3d2 : list) {
                    if (Dismounting.canPlaceEntityAt(this.world, vec3d2, passenger, entityPose)) {
                        passenger.setPose(entityPose);
                        return vec3d2;
                    }
                }
            }
        }

        this.setSliding(false);

        return super.updatePassengerForDismount(passenger);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return FowlPlayEntityType.PENGUIN.create(world);
    }

    @Override
    public double getMountedHeightOffset() {
        return 0;
    }

    public float getMountedXOffset() {
        return -0.1F;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return Ingredient.ofTag(FowlPlayItemTags.PENGUIN_TEMPT_ITEMS).test(stack);
    }

    public static DefaultAttributeContainer.Builder createPenguinAttributes() {
        return BirdEntity.createBirdAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0f)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.135f)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0f);
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        EntityDimensions entityDimensions = super.getDimensions(pose);
        return this.isSliding() || this.isTouchingWater() ? entityDimensions.scaled(1.0F, 0.35F) : entityDimensions;
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return this.isSliding() || this.isTouchingWater() ? dimensions.height * 0.35F : dimensions.height * 0.965F;
    }

    @Override
    public boolean canBreatheInWater() {
        return false;
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.isAlive()) {
            Entity entity = this.getPrimaryPassenger();
            if (this.hasPassengers() && entity instanceof LivingEntity) {
                float sidewaysSpeed = ((LivingEntity) entity).sidewaysSpeed * 0.5F;
                float forwardSpeed = ((LivingEntity) entity).forwardSpeed;
                if (this.isLogicalSideForUpdatingMovement()) {
                    this.setMovementSpeed((float) this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
                    super.travel(new Vec3d(sidewaysSpeed, movementInput.y, forwardSpeed));
                } else if (entity instanceof PlayerEntity) {
                    this.setVelocity(Vec3d.ZERO);
                }
            }
            if (this.canMoveVoluntarily() && this.isTouchingWater()) {
                this.updateVelocity(this.getMovementSpeed(), movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                this.setVelocity(this.getVelocity().multiply(0.8F));
            } else {
                super.travel(movementInput);
            }
        }
    }

//    private void updateMovement() {
//        if (!this.hasPassengers()) {
//            return;
//        }
//        float f = 0.0f;
//        if (this.pressingLeft) {
//            this.yawVelocity -= 1.0f;
//        }
//        if (this.pressingRight) {
//            this.yawVelocity += 1.0f;
//        }
//        if (this.pressingRight != this.pressingLeft && !this.pressingForward && !this.pressingBack) {
//            f += 0.005f;
//        }
//        this.setYaw(this.getYaw() + this.yawVelocity);
//        if (this.pressingForward) {
//            f += 0.04f;
//        }
//        if (this.pressingBack) {
//            f -= 0.005f;
//        }
//        this.setVelocity(this.getVelocity().add(MathHelper.sin(-this.getYaw() * ((float) Math.PI / 180)) * f, 0.0, MathHelper.cos(this.getYaw() * ((float) Math.PI / 180)) * f));
//    }

//    @Override
//    protected void initGoals() {
//        this.goalSelector.add(0, new BreatheAirGoal(this));
//        this.goalSelector.add(1, new ExtinguishFire());
//        this.goalSelector.add(1, new EscapeDangerGoal(this, 0.7));
//        this.goalSelector.add(2, new AnimalMateGoal(this, 0.67));
//        this.goalSelector.add(3, new FleeEntityGoal<>(this, PolarBearEntity.class, 10.0f, 0.7, 0.75));
//        this.goalSelector.add(4, new TemptGoal(this, 0.67, BREEDING_INGREDIENT, false));
//        this.goalSelector.add(5, new FollowParentGoal(this, 0.67));
//        this.goalSelector.add(5, new PenguinFlapGoal(this));
//        this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.67));
//        this.goalSelector.add(7, new MoveIntoWaterGoal(this));
//        this.goalSelector.add(8, new SwimAroundGoal(this, 1.0, 10));
//        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 15.0f));
//        this.goalSelector.add(10, new LookAroundGoal(this));
//    }

    @Override
    public int getMaxAir() {
        return 2400;
    }

    @Override
    protected int getNextAirOnLand(int air) {
        return this.getMaxAir();
    }

    @Override
    public boolean canBeSaddled() {
        return false;
    }

    @Override
    public void saddle(@Nullable SoundCategory sound) {
    }

    @Override
    public boolean isSaddled() {
        return false;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        boolean bl = this.isBreedingItem(player.getStackInHand(hand));
        if (!bl && !this.hasPassengers() && !player.shouldCancelInteraction() && !this.isBaby()) {
            if (!this.world.isClient) {
                player.startRiding(this);
                this.setSliding(true);
            }
            return ActionResult.success(this.world.isClient);
        }
        return super.interactMob(player, hand);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SLIDING, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Sliding", this.isSliding());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setSliding(nbt.getBoolean("Sliding"));
    }

    public boolean isSliding() {
        return /*this.hasPassengers() || */dataTracker.get(SLIDING);
    }

    void setSliding(boolean sliding) {
        this.dataTracker.set(SLIDING, sliding);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (!this.world.isDay() && this.random.nextFloat() < 0.1F) {
            List<PenguinEntity> list = this.world
                .getEntitiesByClass(PenguinEntity.class, this.getBoundingBox().expand(16.0, 16.0, 16.0), EntityPredicates.VALID_LIVING_ENTITY);
            if (!list.isEmpty()) {
                return this.isBaby() ? FowlPlaySoundEvents.ENTITY_PENGUIN_BABY_AMBIENT : FowlPlaySoundEvents.ENTITY_PENGUIN_AMBIENT;
            }
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

    public void setInputs(boolean pressingLeft, boolean pressingRight, boolean pressingForward, boolean pressingBack) {
        this.pressingLeft = pressingLeft;
        this.pressingRight = pressingRight;
        this.pressingForward = pressingForward;
        this.pressingBack = pressingBack;
    }

    @Override
    public boolean isFlying() {
        return false;
    }

    @Override
    public void setFlying(boolean flying) {
    }

    @Override
    protected Brain.Profile<PenguinEntity> createBrainProfile() {
        return Brain.createProfile(MEMORY_MODULES, SENSORS);
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
        this.setSliding(!this.getPassengerList().isEmpty());
        this.getWorld().getProfiler().push("penguinBrain");
        this.getBrain().tick((ServerWorld) this.getWorld(), this);
        this.getWorld().getProfiler().pop();
        this.getWorld().getProfiler().push("penguinActivityUpdate");
        PenguinBrain.updateActivities(this);
        this.getWorld().getProfiler().pop();
        super.mobTick();
    }

    static {
        SENSORS = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.NEAREST_ADULT,
            SensorType.HURT_BY,
            SensorType.AXOLOTL_ATTACKABLES,
            FowlPlaySensorType.PENGUIN_TEMPTATIONS,
            SensorType.IS_IN_WATER
        );
        MEMORY_MODULES = ImmutableList.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.MOBS,
            MemoryModuleType.VISIBLE_MOBS,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,
            MemoryModuleType.BREED_TARGET,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryModuleType.NEAREST_VISIBLE_ADULT,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.HAS_HUNTING_COOLDOWN,
            MemoryModuleType.TEMPTING_PLAYER,
            MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
            MemoryModuleType.IS_TEMPTED,
            MemoryModuleType.HURT_BY,
            MemoryModuleType.HURT_BY_ENTITY,
            MemoryModuleType.NEAREST_ATTACKABLE,
            MemoryModuleType.IS_IN_WATER,
            MemoryModuleType.IS_PREGNANT,
            MemoryModuleType.IS_PANICKING
        );
    }
}
