package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.core.FowlPlayRegistries;
import aqario.fowlplay.core.FowlPlayRegistryKeys;
import aqario.fowlplay.core.FowlPlaySoundEvents;
import aqario.fowlplay.core.FowlPlayTrackedDataHandlerRegistry;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import aqario.fowlplay.core.tags.FowlPlayItemTags;
import com.mojang.serialization.Dynamic;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
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
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PigeonEntity extends TameableBirdEntity implements VariantHolder<RegistryEntry<PigeonVariant>>, Flocking {
    private static final TrackedData<Optional<UUID>> RECIPIENT = DataTracker.registerData(
        PigeonEntity.class,
        TrackedDataHandlerRegistry.OPTIONAL_UUID
    );
    private static final TrackedData<RegistryEntry<PigeonVariant>> VARIANT = DataTracker.registerData(
        PigeonEntity.class,
        FowlPlayTrackedDataHandlerRegistry.PIGEON_VARIANT
    );
    public final AnimationState standingState = new AnimationState();
    public final AnimationState glidingState = new AnimationState();
    public final AnimationState flappingState = new AnimationState();
    public final AnimationState floatingState = new AnimationState();
    public final AnimationState sittingState = new AnimationState();

    public PigeonEntity(EntityType<? extends PigeonEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0f);
        this.setPathfindingPenalty(PathNodeType.WATER, -3.0f);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 12.0f);
        this.setPathfindingPenalty(PathNodeType.DANGER_POWDER_SNOW, -1.0f);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0f);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0f);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        FowlPlayRegistries.PIGEON_VARIANT.getRandom(world.getRandom()).ifPresent(this::setVariant);
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty difficulty) {
        this.setEquipmentDropChance(EquipmentSlot.MAINHAND, 1.0f);
        this.setEquipmentDropChance(EquipmentSlot.OFFHAND, 1.0f);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(RECIPIENT, Optional.empty());
        builder.add(VARIANT, FowlPlayRegistries.PIGEON_VARIANT.entryOf(PigeonVariant.BANDED));
    }

    @Override
    public RegistryEntry<PigeonVariant> getVariant() {
        return this.dataTracker.get(VARIANT);
    }

    @Override
    public void setVariant(RegistryEntry<PigeonVariant> variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("variant", this.getVariant().getKey().orElse(PigeonVariant.BANDED).getValue().toString());
        if (this.getRecipientUuid() != null) {
            nbt.putUuid("recipient", this.getRecipientUuid());
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        Optional.ofNullable(Identifier.tryParse(nbt.getString("variant")))
            .map(variant -> RegistryKey.of(FowlPlayRegistryKeys.PIGEON_VARIANT, variant))
            .flatMap(FowlPlayRegistries.PIGEON_VARIANT::getEntry)
            .ifPresent(this::setVariant);

        if (nbt.containsUuid("recipient")) {
            this.setRecipientUuid(nbt.getUuid("recipient"));
        }
        else {
            this.setRecipientUuid(null);
        }
    }

    @Override
    public float getWaterline() {
        return 0.45F;
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

    public static DefaultAttributeContainer.Builder createPigeonAttributes() {
        return FlyingBirdEntity.createFlyingBirdAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f)
            .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.26f);
    }

    @Override
    protected EntityNavigation getLandNavigation() {
        MobNavigation mobNavigation = new MobNavigation(this, this.getWorld());
        mobNavigation.setCanPathThroughDoors(false);
        mobNavigation.setCanEnterOpenDoors(true);
        mobNavigation.setCanSwim(false);
        return mobNavigation;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack playerStack = player.getStackInHand(hand);
        ItemStack bundleStack = this.getStackInHand(Hand.OFF_HAND);

        // Equip bundle
        if (bundleStack.isEmpty() && playerStack.getItem() instanceof BundleItem && playerStack.getComponents().contains(DataComponentTypes.CUSTOM_NAME) && this.isTamed()) {
            if (!this.getWorld().isClient) {
                this.setStackInHand(Hand.OFF_HAND, playerStack);
                player.setStackInHand(hand, ItemStack.EMPTY);
            }
            return ActionResult.success(this.getWorld().isClient);
        }

        // Unequip bundle
        if (playerStack.isEmpty() && bundleStack.getItem() instanceof BundleItem) {
            if (!this.getWorld().isClient) {
                player.setStackInHand(hand, bundleStack);
                this.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);
            }
            return ActionResult.success(this.getWorld().isClient);
        }

        // Taming
        if (this.isBreedingItem(playerStack) && !this.isTamed()) {
            if (!this.getWorld().isClient) {
                this.eat(player, hand, playerStack);
                if (this.random.nextInt(4) == 0) {
                    this.setOwner(player);
                    this.navigation.stop();
                    this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);
                }
                else {
                    this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
                }
            }
            return ActionResult.success(this.getWorld().isClient);
        }

        // Sitting
        if (this.isOnGround() && this.isTamed() && this.isOwner(player)) {
            if (!this.getWorld().isClient) {
                this.setSitting(!this.isSitting());
                this.jumping = false;
                this.navigation.stop();
                this.setTarget(null);
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
        return Ingredient.fromTag(FowlPlayItemTags.PIGEON_FOOD);
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().isIn(FowlPlayEntityTypeTags.PIGEON_AVOIDS);
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
    public void tick() {
        if (this.getWorld().isClient()) {
            this.standingState.setRunning(!this.isFlying() && !this.isInsideWaterOrBubbleColumn() && !this.isInSittingPose(), this.age);
            this.flappingState.setRunning(this.isFlying(), this.age);
            this.floatingState.setRunning(!this.isFlying() && this.isInsideWaterOrBubbleColumn(), this.age);
            this.sittingState.setRunning(this.isInSittingPose(), this.age);
        }

        super.tick();
    }

    @Override
    protected void mobTick() {
        this.getWorld().getProfiler().push("pigeonBrain");
        this.getBrain().tick((ServerWorld) this.getWorld(), this);
        this.getWorld().getProfiler().pop();
        this.getWorld().getProfiler().push("pigeonActivityUpdate");
        PigeonBrain.reset(this);
        this.getWorld().getProfiler().pop();
        super.mobTick();

        if (this.getServer() == null) {
            return;
        }

        if (!this.isTamed()) {
            return;
        }

        ItemStack stack = this.getEquippedStack(EquipmentSlot.OFFHAND);
        ServerPlayerEntity recipient = this.getServer().getPlayerManager().getPlayer(stack.getName().getString());

        if (!(stack.getItem() instanceof BundleItem) || !stack.getComponents().contains(DataComponentTypes.CUSTOM_NAME) || recipient == null || recipient.getUuid() == null) {
            this.setRecipientUuid(null);
            return;
        }

        this.setRecipientUuid(recipient.getUuid());
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
    protected boolean canSing() {
        if (this.getWorld().isDay()) {
            return false;
        }
        List<PlayerEntity> list = this.getWorld()
            .getEntitiesByClass(PlayerEntity.class, this.getAttackBox().expand(16.0, 16.0, 16.0), EntityPredicates.EXCEPT_SPECTATOR);
        if (list.isEmpty()) {
            return false;
        }
        return super.canSing();
    }

    @Nullable
    @Override
    protected SoundEvent getCallSound() {
        return FowlPlaySoundEvents.ENTITY_PIGEON_CALL;
    }

    @Nullable
    @Override
    protected SoundEvent getSongSound() {
        return FowlPlaySoundEvents.ENTITY_PIGEON_SONG;
    }

    @Override
    public int getCallDelay() {
        return 120;
    }

    @Override
    protected float getCallVolume() {
        return FowlPlayConfig.getInstance().pigeonCallVolume;
    }

    @Override
    protected float getSongVolume() {
        return FowlPlayConfig.getInstance().pigeonSongVolume;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return null;
    }

    @Override
    protected Brain.Profile<PigeonEntity> createBrainProfile() {
        return PigeonBrain.createProfile();
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return PigeonBrain.create(this.createBrainProfile().deserialize(dynamic));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Brain<PigeonEntity> getBrain() {
        return (Brain<PigeonEntity>) super.getBrain();
    }

    @Override
    public boolean isLeader() {
        return false;
    }

    @Override
    public void setLeader() {
    }
}
