package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.entity.ai.goal.BirdWanderGoal;
import aqario.fowlplay.common.entity.ai.goal.DeliverBundleGoal;
import aqario.fowlplay.common.entity.ai.goal.DelivererFollowOwnerGoal;
import aqario.fowlplay.common.entity.ai.goal.FlyAroundGoal;
import aqario.fowlplay.common.sound.FowlPlaySoundEvents;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.core.manager.SingletonAnimationFactory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PigeonEntity extends TameableBirdEntity implements IAnimatable {
    private final AnimationFactory factory = new SingletonAnimationFactory(this);
    private static final TrackedData<Optional<UUID>> RECIPIENT = DataTracker.registerData(PigeonEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    public static final TrackedData<Boolean> DELIVERING = DataTracker.registerData(PigeonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public float flapProgress;
    public float maxWingDeviation;
    public float prevMaxWingDeviation;
    public float prevFlapProgress;
    public float flapSpeed = 1.0f;
    private int eatingTime;
    private boolean isFlightMoveControl;

    public PigeonEntity(EntityType<? extends PigeonEntity> entityType, World world) {
        super(entityType, world);
        this.setMoveControl(false);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0f);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0f);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, -1.0f);
        this.setPathfindingPenalty(PathNodeType.DANGER_POWDER_SNOW, -1.0f);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0f);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0f);
    }

    private void setMoveControl(boolean isFlying) {
        if (isFlying) {
            this.moveControl = new FlightMoveControl(this, 10, false);
            this.isFlightMoveControl = true;
        } else {
            this.moveControl = new MoveControl(this);
            this.isFlightMoveControl = false;
        }
    }

    @Override
    protected void initEquipment(RandomGenerator random, LocalDifficulty difficulty) {
        this.setEquipmentDropChance(EquipmentSlot.MAINHAND, 1.0f);
        this.setEquipmentDropChance(EquipmentSlot.OFFHAND, 1.0f);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(RECIPIENT, Optional.empty());
        this.dataTracker.startTracking(DELIVERING, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Delivering", this.dataTracker.get(DELIVERING));
        if (this.getRecipientUuid() != null) {
            nbt.putUuid("Recipient", this.getRecipientUuid());
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(DELIVERING, nbt.getBoolean("Delivering"));
        if (!nbt.containsUuid("Receiver")) {
            this.setRecipientUuid(null);
            return;
        }
        this.setRecipientUuid(nbt.getUuid("Receiver"));
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
        return MobEntity.createMobAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0)
            .add(EntityAttributes.GENERIC_FLYING_SPEED, 1.0f)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new EscapeDangerGoal(this, 1.8));
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new DeliverBundleGoal<>(this, 1.0, 6.0F, 128.0F, false));
        this.goalSelector.add(3, new DelivererFollowOwnerGoal(this, 1.0, 10.0F, 2.0F, false));
        this.goalSelector.add(4, new FleeEntityGoal<>(this, PlayerEntity.class, entity -> !this.isTamed(), 6.0f, 1.4, 1.8, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR::test));
        this.goalSelector.add(5, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(6, new FlyAroundGoal(this));
        this.goalSelector.add(6, new BirdWanderGoal(this, 1.0));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 20.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        if (!this.isFlying()) {
            MobNavigation mobNavigation = new MobNavigation(this, world);
            mobNavigation.setCanPathThroughDoors(false);
            mobNavigation.setCanSwim(true);
            mobNavigation.setCanEnterOpenDoors(true);
            return mobNavigation;
        }
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.45f;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (this.getStackInHand(Hand.OFF_HAND).isEmpty() && stack.getItem() instanceof BundleItem && stack.hasCustomName() && this.isTamed()) {
            if (!this.world.isClient) {
                this.setStackInHand(Hand.OFF_HAND, stack);
                player.setStackInHand(hand, ItemStack.EMPTY);
            }
            return ActionResult.success(this.world.isClient);
        }

        if (stack.isEmpty() && this.getStackInHand(Hand.OFF_HAND).getItem() instanceof BundleItem) {
            if (!this.world.isClient) {
                player.setStackInHand(hand, this.getStackInHand(Hand.OFF_HAND));
                this.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);
            }
            return ActionResult.success(this.world.isClient);
        }

//        if (!stack.isEmpty() && !this.isBreedingItem(stack)) {
//            if (!this.world.isClient) {
//                this.setStackInHand(Hand.MAIN_HAND, stack);
//                player.setStackInHand(hand, ItemStack.EMPTY);
//            }
//            return ActionResult.success(this.world.isClient);
//        }

        if (!this.isBreedingItem(stack)) {
            return super.interactMob(player, hand);
        }

        if (!this.isTamed()) {
            if (!this.world.isClient) {
                this.eat(player, hand, stack);
                if (this.random.nextInt(4) == 0) {
                    this.setOwner(player);
                    this.navigation.stop();
                    this.world.sendEntityStatus(this, (byte) 7);
                } else {
                    this.world.sendEntityStatus(this, (byte) 6);
                }
            }
            return ActionResult.success(this.world.isClient);
        }

        return super.interactMob(player, hand);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return !this.isTamed() && stack.isOf(Items.WHEAT_SEEDS);
    }

    @Override
    public boolean canEquip(ItemStack stack) {
        EquipmentSlot equipmentSlot = MobEntity.getPreferredEquipmentSlot(stack);
        if (!this.getEquippedStack(equipmentSlot).isEmpty()) {
            return false;
        }
        return equipmentSlot == EquipmentSlot.MAINHAND || equipmentSlot == EquipmentSlot.OFFHAND && super.canEquip(stack);
    }

    @Override
    protected void dropInventory() {
        super.dropInventory();

        this.dropStack(this.getEquippedStack(EquipmentSlot.MAINHAND));
        this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        this.dropStack(this.getEquippedStack(EquipmentSlot.OFFHAND));
        this.equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
    }

    @Override
    protected void mobTick() {
        super.mobTick();
        if (!this.world.isClient) {
            if (this.isFlying() != this.isFlightMoveControl) {
                this.setMoveControl(this.isFlying());
            }
        }
        if (this.getServer() == null) {
            return;
        }

        ItemStack stack = this.getEquippedStack(EquipmentSlot.OFFHAND);
        ServerPlayerEntity recipient = this.getServer().getPlayerManager().getPlayer(stack.getName().getString());

        if (!(stack.getItem() instanceof BundleItem) || !stack.hasCustomName() || recipient == null || recipient.getUuid() == null) {
            this.setRecipientUuid(null);
            this.dataTracker.set(DELIVERING, false);
            return;
        }

        this.setRecipientUuid(recipient.getUuid());
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.isFlying()) {
            this.glide();
        } else {
            this.flapWings();
        }
    }

    private void glide() {
        Vec3d vec3d = this.getVelocity();
        if (!this.onGround && vec3d.y < 0.0) {
            this.setVelocity(vec3d.multiply(1.0, 0.6, 1.0));
        }
    }

    private void flapWings() {
        this.prevFlapProgress = this.flapProgress;
        this.prevMaxWingDeviation = this.maxWingDeviation;
        this.maxWingDeviation += (float) (this.onGround || this.hasVehicle() ? -1 : 4) * 0.3f;
        this.maxWingDeviation = MathHelper.clamp(this.maxWingDeviation, 0.0f, 1.0f);
        if (!this.onGround && this.flapSpeed < 1.0f) {
            this.flapSpeed = 1.0f;
        }
        this.flapSpeed *= 0.9f;
        Vec3d vec3d = this.getVelocity();
        if (!this.onGround && vec3d.y < 0.0) {
            this.setVelocity(vec3d.multiply(1.0, 0.9, 1.0));
        }
        this.flapProgress += this.flapSpeed * 2.0f;
    }

    @Override
    protected void addFlapEffects() {
        this.playSound(SoundEvents.ENTITY_PARROT_FLY, 0.15f, 1.0f);
    }

    public UUID getRecipientUuid() {
        return this.dataTracker.get(RECIPIENT).orElse(null);
    }

    public void setRecipientUuid(@Nullable UUID uuid) {
        this.dataTracker.set(RECIPIENT, Optional.ofNullable(uuid));
    }

    @Override
    public Vec3d getLeashOffset() {
        return new Vec3d(0.0, 0.5f * this.getStandingEyeHeight(), this.getWidth() * 0.4f);
    }

    @Override
    public void playAmbientSound() {
        SoundEvent soundEvent = this.getAmbientSound();
        if (soundEvent == FowlPlaySoundEvents.ENTITY_PIGEON_CALL) {
            this.playSound(soundEvent, 2.0F, this.getSoundPitch());
        } else {
            super.playAmbientSound();
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (!this.world.isDay() && this.random.nextFloat() < 0.1F) {
            List<PlayerEntity> list = this.world
                .getEntitiesByClass(PlayerEntity.class, this.getBoundingBox().expand(16.0, 16.0, 16.0), EntityPredicates.EXCEPT_SPECTATOR);
            if (list.isEmpty()) {
                return FowlPlaySoundEvents.ENTITY_PIGEON_CALL;
            }
        }

        return FowlPlaySoundEvents.ENTITY_PIGEON_AMBIENT;
    }

    @Override
    public SoundEvent getEatSound(ItemStack stack) {
        return SoundEvents.ENTITY_GENERIC_EAT;
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

    public boolean isMoving(AnimationEvent<PigeonEntity> event) {
        float limbSwingAmount = event.getLimbSwingAmount();
        return Math.abs(limbSwingAmount) >= 0.05F;
    }

    private PlayState predicate(AnimationEvent<PigeonEntity> event) {
        if (this.isFlying()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.pigeon.flying", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        if (this.isTouchingWater()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.pigeon.swimming", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        if (!this.isOnGround()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.pigeon.flap", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        if (this.isMoving(event)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.pigeon.walk", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.pigeon.idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 4, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
