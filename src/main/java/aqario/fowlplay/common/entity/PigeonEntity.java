package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.entity.ai.goal.*;
import aqario.fowlplay.common.sound.FowlPlaySoundEvents;
import aqario.fowlplay.common.tags.FowlPlayItemTags;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.VariantProvider;
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
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.EntityView;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PigeonEntity extends TameableBirdEntity implements VariantProvider<PigeonEntity.Variant> {
    private static final TrackedData<Optional<UUID>> RECIPIENT = DataTracker.registerData(PigeonEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    public static final TrackedData<Boolean> DELIVERING = DataTracker.registerData(PigeonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<String> VARIANT = DataTracker.registerData(PigeonEntity.class, TrackedDataHandlerRegistry.STRING);
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState flyAnimationState = new AnimationState();
    public final AnimationState floatAnimationState = new AnimationState();
    public float flapProgress;
    public float maxWingDeviation;
    public float prevMaxWingDeviation;
    public float prevFlapProgress;
    public float flapSpeed = 1.0f;

    public PigeonEntity(EntityType<? extends PigeonEntity> entityType, World world) {
        super(entityType, world);
        this.addPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0f);
        this.addPathfindingPenalty(PathNodeType.WATER, -1.0f);
        this.addPathfindingPenalty(PathNodeType.WATER_BORDER, 12.0f);
        this.addPathfindingPenalty(PathNodeType.DANGER_POWDER_SNOW, -1.0f);
        this.addPathfindingPenalty(PathNodeType.COCOA, -1.0f);
        this.addPathfindingPenalty(PathNodeType.FENCE, -1.0f);
    }

    @Override
    protected void initEquipment(RandomGenerator random, LocalDifficulty difficulty) {
        this.setDropChance(EquipmentSlot.MAINHAND, 1.0f);
        this.setDropChance(EquipmentSlot.OFFHAND, 1.0f);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(RECIPIENT, Optional.empty());
        builder.add(DELIVERING, false);
        builder.add(VARIANT, Util.getRandom(Variant.VARIANTS, random).toString());
    }

    @Override
    public PigeonEntity.Variant getVariant() {
        return PigeonEntity.Variant.valueOf(this.dataTracker.get(VARIANT));
    }

    @Override
    public void setVariant(PigeonEntity.Variant variant) {
        this.dataTracker.set(VARIANT, variant.toString());
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("variant", this.getVariant().toString());
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
        if (nbt.contains("variant")) {
            this.setVariant(PigeonEntity.Variant.valueOf(nbt.getString("variant")));
        }
    }

    @Override
    public int getFlapFrequency() {
        return 7;
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
        return BirdEntity.createAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0)
            .add(EntityAttributes.GENERIC_FLYING_SPEED, 1.0f)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new EscapeDangerGoal(this, 1.8));
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new DeliverBundleGoal(this, 1.0, 6.0F, 128.0F, false));
        this.goalSelector.add(2, new DelivererFollowOwnerGoal(this, 1.0, 10.0F, 2.0F, false));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, PlayerEntity.class, entity -> !this.isTamed(), 6.0f, 1.4, 1.8, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR::test));
        this.goalSelector.add(3, new PickupItemGoal(this));
        this.goalSelector.add(4, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(5, new FlyAroundGoal(this));
        this.goalSelector.add(6, new BirdWanderGoal(this, 1.0));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 20.0f));
        this.goalSelector.add(8, new LookAroundGoal(this));
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        if (this.isFlying()) {
            BirdNavigation birdNavigation = new BirdNavigation(this, world);
            birdNavigation.setCanPathThroughDoors(false);
            birdNavigation.setCanEnterOpenDoors(true);
            birdNavigation.setCanSwim(false);
            return birdNavigation;
        }
        MobNavigation mobNavigation = new MobNavigation(this, world);
        mobNavigation.setCanPathThroughDoors(false);
        mobNavigation.setCanEnterOpenDoors(true);
        mobNavigation.setCanSwim(false);
        return mobNavigation;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack playerStack = player.getStackInHand(hand);

        if (this.getStackInHand(Hand.OFF_HAND).isEmpty() && playerStack.getItem() instanceof BundleItem && playerStack.getComponents().contains(DataComponentTypes.CUSTOM_NAME) && this.isTamed()) {
            if (!this.getWorld().isClient) {
                this.setStackInHand(Hand.OFF_HAND, playerStack);
                player.setStackInHand(hand, ItemStack.EMPTY);
            }
            return ActionResult.success(this.getWorld().isClient);
        }

        if (playerStack.isEmpty() && this.getStackInHand(Hand.OFF_HAND).getItem() instanceof BundleItem) {
            if (!this.getWorld().isClient) {
                player.setStackInHand(hand, this.getStackInHand(Hand.OFF_HAND));
                this.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);
            }
            return ActionResult.success(this.getWorld().isClient);
        }

        if (!this.isBreedingItem(playerStack)) {
            return super.interactMob(player, hand);
        }

        if (!this.isTamed()) {
            if (!this.getWorld().isClient) {
                this.eat(player, hand, playerStack);
                if (this.random.nextInt(4) == 0) {
                    this.setOwner(player);
                    this.navigation.stop();
                    this.getWorld().sendEntityStatus(this, (byte) 7);
                }
                else {
                    this.getWorld().sendEntityStatus(this, (byte) 6);
                }
            }
            return ActionResult.success(this.getWorld().isClient);
        }

        return super.interactMob(player, hand);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return !this.isTamed() && this.getFood().test(stack);
    }

    @Override
    public boolean canEquip(ItemStack stack) {
        EquipmentSlot equipmentSlot = this.getPreferredEquipmentSlot(stack);
        if (!this.getEquippedStack(equipmentSlot).isEmpty()) {
            return false;
        }
        return equipmentSlot == EquipmentSlot.MAINHAND || equipmentSlot == EquipmentSlot.OFFHAND && super.canEquip(stack);
    }

    @Override
    public Ingredient getFood() {
        return Ingredient.ofTag(FowlPlayItemTags.PIGEON_FOOD);
    }

    @Override
    protected void dropInventory() {
        super.dropInventory();

        this.dropStack(this.getEquippedStack(EquipmentSlot.MAINHAND));
        this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        this.dropStack(this.getEquippedStack(EquipmentSlot.OFFHAND));
        this.equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
    }

    private boolean isWalking() {
        return this.isOnGround() && this.getVelocity().horizontalLengthSquared() > 1.0E-6 && !this.isInsideWaterOrBubbleColumn();
    }

    @Override
    public void tick() {
        if (this.getWorld().isClient()) {
            if (this.isOnGround() && !this.isWalking()) {
                this.idleAnimationState.start(this.age);
            }
            else {
                this.idleAnimationState.stop();
            }

            if (!this.isOnGround()) {
                this.flyAnimationState.start(this.age);
            }
            else {
                this.flyAnimationState.stop();
            }

            if (this.isWalking()) {
                this.walkAnimationState.start(this.age);
            }
            else {
                this.walkAnimationState.stop();
            }

            if (this.isInsideWaterOrBubbleColumn()) {
                this.floatAnimationState.start(this.age);
            }
            else {
                this.floatAnimationState.stop();
            }
        }

        super.tick();
    }

    @Override
    protected void mobTick() {
        super.mobTick();

        if (this.getServer() == null) {
            return;
        }

        ItemStack stack = this.getEquippedStack(EquipmentSlot.OFFHAND);
        ServerPlayerEntity recipient = this.getServer().getPlayerManager().getPlayer(stack.getName().getString());

        if (!(stack.getItem() instanceof BundleItem) || !stack.getComponents().contains(DataComponentTypes.CUSTOM_NAME) || recipient == null || recipient.getUuid() == null) {
            this.setRecipientUuid(null);
            this.dataTracker.set(DELIVERING, false);
            return;
        }

        this.setRecipientUuid(recipient.getUuid());
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
            this.setVelocity(vec3d.add(0.0f, 1.5f, 0.0f));
        }
        this.flapProgress += this.flapSpeed * 2.0f;
    }

//    @Override
//    public void travel(Vec3d movementInput) {
//        if (!this.isFlying()) {
//            super.travel(movementInput);
//            return;
//        }
//
//        if (this.canMoveVoluntarily() || this.isLogicalSideForUpdatingMovement()) {
//            double d = 0.08;
//            Vec3d vec3d4 = this.getVelocity();
//            if (vec3d4.y > -0.5) {
//                this.fallDistance = 1.0F;
//            }
//
//            Vec3d vec3d5 = this.getRotationVector();
//            float f = this.getPitch() * (float) (Math.PI / 180.0);
//            double i = Math.sqrt(vec3d5.x * vec3d5.x + vec3d5.z * vec3d5.z);
//            double j = vec3d4.horizontalLength();
//            double k = vec3d5.length();
//            double l = Math.cos(f);
//            l = l * l * Math.min(1.0, k / 0.4);
//            vec3d4 = this.getVelocity().add(0.0, d * (-1.0 + l * 0.75), 0.0);
//            if (vec3d4.y < 0.0 && i > 0.0) {
//                double m = vec3d4.y * -0.1 * l;
//                vec3d4 = vec3d4.add(vec3d5.x * m / i, m, vec3d5.z * m / i);
//            }
//
//            if (f < 0.0F && i > 0.0) {
//                double m = j * (double)(-MathHelper.sin(f)) * 0.04;
//                vec3d4 = vec3d4.add(-vec3d5.x * m / i, m * 3.2, -vec3d5.z * m / i);
//            }
//
//            if (i > 0.0) {
//                vec3d4 = vec3d4.add((vec3d5.x / i * j - vec3d4.x) * 0.1, 0.0, (vec3d5.z / i * j - vec3d4.z) * 0.1);
//            }
//
//            this.setVelocity(vec3d4.multiply(0.99F, 0.98F, 0.99F));
//            this.move(MovementType.SELF, this.getVelocity());
//            if (this.horizontalCollision && !this.getWorld().isClient) {
//                double m = this.getVelocity().horizontalLength();
//                double n = j - m;
//                float o = (float)(n * 10.0 - 3.0);
//                if (o > 0.0F) {
//                    this.damage(DamageSource.FLY_INTO_WALL, o);
//                }
//            }
//        }
//
//        this.updateLimbs(this, false);
//    }

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
            this.playSound(soundEvent, 8.0F, this.getSoundPitch());
        }
        else {
            super.playAmbientSound();
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (!this.getWorld().isDay() && this.random.nextFloat() < 0.1F) {
            List<PlayerEntity> list = this.getWorld()
                .getEntitiesByClass(PlayerEntity.class, this.getBoundingBox().expand(16.0, 16.0, 16.0), EntityPredicates.EXCEPT_SPECTATOR);
            if (list.isEmpty()) {
                return FowlPlaySoundEvents.ENTITY_PIGEON_CALL;
            }
        }

        return FowlPlaySoundEvents.ENTITY_PIGEON_AMBIENT;
    }

    @Override
    public SoundEvent getEatSound(ItemStack stack) {
        return SoundEvents.ENTITY_PARROT_EAT;
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
    public EntityView getEntityView() {
        return null;
    }

    public enum Variant {
        BANDED("banded"),
        CHECKERED("checkered"),
        GRAY("gray"),
        RUSTY("rusty"),
        WHITE("white");

        public static final List<Variant> VARIANTS = List.of(Arrays.stream(values())
            .toArray(Variant[]::new));

        private final String id;

        Variant(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}
