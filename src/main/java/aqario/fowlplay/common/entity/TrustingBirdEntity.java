package aqario.fowlplay.common.entity;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public abstract class TrustingBirdEntity extends BirdEntity {
    protected static final TrackedData<Optional<UUID>> TRUSTED = DataTracker.registerData(TrustingBirdEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    private int eatingTime;

    protected TrustingBirdEntity(EntityType<? extends BirdEntity> entityType, World world) {
        super(entityType, world);
        this.setCanPickUpLoot(true);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TRUSTED, Optional.empty());
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.getTrustedUuid() != null) {
            nbt.putUuid("Trusted", this.getTrustedUuid());
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        UUID uUID;
        if (nbt.containsUuid("Trusted")) {
            uUID = nbt.getUuid("Trusted");
        }
        else {
            String string = nbt.getString("Trusted");
            uUID = ServerConfigHandler.getPlayerUuidByName(this.getServer(), string);
        }

        if (uUID != null) {
            this.setTrustedUuid(uUID);
        }
    }

    @Override
    public boolean canEquip(ItemStack stack) {
        EquipmentSlot equipmentSlot = MobEntity.getPreferredEquipmentSlot(stack);
        if (!this.getEquippedStack(equipmentSlot).isEmpty()) {
            return false;
        }
        return equipmentSlot == EquipmentSlot.MAINHAND && super.canEquip(stack);
    }

    public abstract Ingredient getFood();

    @Override
    public boolean canPickupItem(ItemStack stack) {
        ItemStack heldStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
        return this.getFood().test(stack) && !this.getFood().test(heldStack);
    }

    private void drop(ItemStack stack) {
        if (!stack.isEmpty() && !this.getWorld().isClient) {
            ItemEntity itemEntity = new ItemEntity(
                this.getWorld(), this.getX() + this.getRotationVector().x, this.getY() + 1.0, this.getZ() + this.getRotationVector().z, stack
            );
            itemEntity.setPickupDelay(40);
            itemEntity.setThrower(this.getUuid());
            this.getWorld().spawnEntity(itemEntity);
            this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }
    }

    private void dropItem(ItemStack stack) {
        ItemEntity itemEntity = new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), stack);
        this.getWorld().spawnEntity(itemEntity);
    }

    @Override
    protected void loot(ItemEntity item) {
        ItemStack stack = item.getStack();
        if (this.canPickupItem(stack)) {
            int i = stack.getCount();
            if (i > 1) {
                this.dropItem(stack.split(i - 1));
            }

            this.drop(this.getEquippedStack(EquipmentSlot.MAINHAND));
            this.triggerItemPickedUpByEntityCriteria(item);
            this.equipStack(EquipmentSlot.MAINHAND, stack.split(1));
            this.updateDropChances(EquipmentSlot.MAINHAND);
            this.sendPickup(item, stack.getCount());
            item.discard();
            this.eatingTime = 0;
        }
        UUID thrower = item.getOwner() != null ? item.getOwner().getUuid() : null;
        if (thrower != null && !this.isTrusted(thrower)) {
            if (this.random.nextInt(3) == 0) {
                this.setTrustedUuid(thrower);
                this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);
            }
            else {
                this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
            }
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.getWorld().isClient && this.isAlive()) {
            ++this.eatingTime;
            ItemStack stack = this.getEquippedStack(EquipmentSlot.MAINHAND);
            if (this.canEat(stack)) {
                if ((this.eatingTime > 140 && this.random.nextFloat() < 0.1f) || this.eatingTime > 200) {
                    if (stack.getItem().isFood()) {
                        this.heal(stack.getItem().getFoodComponent().getHunger());
                    }
                    else {
                        stack.decrement(1);
                    }
                    ItemStack usedStack = stack.finishUsing(this.getWorld(), this);
                    if (!usedStack.isEmpty()) {
                        this.equipStack(EquipmentSlot.MAINHAND, usedStack);
                    }
                    this.eatingTime = 0;
                    return;
                }
                if (this.eatingTime > 100 && this.random.nextFloat() < 0.1f) {
                    this.playSound(this.getEatSound(stack), 1.0f, 1.0f);
                    this.getWorld().sendEntityStatus(this, EntityStatuses.CREATE_EATING_PARTICLES);
                }
            }
            else if (!stack.isEmpty()) {
                if (this.random.nextFloat() < 0.1f) {
                    this.drop(this.getEquippedStack(EquipmentSlot.MAINHAND));
                }
            }
        }
    }

    private boolean canEat(ItemStack stack) {
        return this.getFood().test(stack) && this.isOnGround()/* && !this.isSleeping()*/;
    }

    protected void showEmoteParticle(boolean positive) {
        ParticleEffect particleEffect = positive ? ParticleTypes.HEART : ParticleTypes.SMOKE;

        for (int i = 0; i < 7; ++i) {
            double d = this.random.nextGaussian() * 0.02;
            double e = this.random.nextGaussian() * 0.02;
            double f = this.random.nextGaussian() * 0.02;
            this.getWorld().addParticle(particleEffect, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), d, e, f);
        }
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES) {
            this.showEmoteParticle(true);
        }
        else if (status == EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES) {
            this.showEmoteParticle(false);
        }
        else if (status == EntityStatuses.CREATE_EATING_PARTICLES) {
            ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
            if (!itemStack.isEmpty()) {
                for (int i = 0; i < 8; i++) {
                    Vec3d vec3d = new Vec3d(((double) this.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0)
                        .rotateX(-this.getPitch() * (float) (Math.PI / 180.0))
                        .rotateY(-this.getYaw() * (float) (Math.PI / 180.0));
                    this.getWorld()
                        .addParticle(
                            new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack),
                            this.getX() + this.getRotationVector().x / 2.0,
                            this.getY(),
                            this.getZ() + this.getRotationVector().z / 2.0,
                            vec3d.x,
                            vec3d.y + 0.05,
                            vec3d.z
                        );
                }
            }
        }
        else {
            super.handleStatus(status);
        }
    }

    @Nullable
    public UUID getTrustedUuid() {
        return this.dataTracker.get(TRUSTED).orElse(null);
    }

    public void setTrustedUuid(@Nullable UUID uuid) {
        this.dataTracker.set(TRUSTED, Optional.ofNullable(uuid));
    }

    public void setTrusted(PlayerEntity player) {
        this.setTrustedUuid(player.getUuid());
        if (player instanceof ServerPlayerEntity) {
            Criteria.TAME_ANIMAL.trigger((ServerPlayerEntity) player, this);
        }
    }

    public boolean hasTrusted() {
        return this.getTrustedUuid() != null;
    }

    @Nullable
    public LivingEntity getTrusted() {
        try {
            UUID uuid = this.getTrustedUuid();
            return uuid == null ? null : this.getWorld().getPlayerByUuid(uuid);
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public boolean canTarget(LivingEntity target) {
        return !this.isTrusted(target) && super.canTarget(target);
    }

    public boolean isTrusted(LivingEntity entity) {
        return entity == this.getTrusted();
    }

    public boolean isTrusted(UUID uuid) {
        return uuid == this.getTrustedUuid();
    }

    @Override
    public AbstractTeam getScoreboardTeam() {
        LivingEntity livingEntity = this.getTrusted();
        if (livingEntity != null) {
            return livingEntity.getScoreboardTeam();
        }

        return super.getScoreboardTeam();
    }

    @Override
    public boolean isTeammate(Entity other) {
        LivingEntity livingEntity = this.getTrusted();
        if (other == livingEntity) {
            return true;
        }

        if (livingEntity != null) {
            return livingEntity.isTeammate(other);
        }

        return super.isTeammate(other);
    }
}
