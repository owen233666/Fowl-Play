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
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.network.ServerPlayerEntity;
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
        } else {
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

    public abstract Ingredient getTemptItems();

    @Override
    public boolean canPickupItem(ItemStack stack) {
        ItemStack heldStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
        return this.getTemptItems().test(stack) && !this.getTemptItems().test(heldStack) && this.eatingTime > 0;
    }

    private void drop(ItemStack stack) {
        if (!stack.isEmpty() && !this.world.isClient) {
            ItemEntity itemEntity = new ItemEntity(
                this.world, this.getX() + this.getRotationVector().x, this.getY() + 1.0, this.getZ() + this.getRotationVector().z, stack
            );
            itemEntity.setPickupDelay(40);
            itemEntity.setThrower(this.getUuid());
            this.world.spawnEntity(itemEntity);
        }
    }

    private void dropItem(ItemStack stack) {
        ItemEntity itemEntity = new ItemEntity(this.world, this.getX(), this.getY(), this.getZ(), stack);
        this.world.spawnEntity(itemEntity);
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
        UUID thrower = item.getThrower();
        if (!this.isTrusted(thrower)) {
            if (this.random.nextInt(3) == 0) {
                this.setTrustedUuid(thrower);
//                PlayerEntity player = this.world.getPlayerByUuid(thrower);
//                if (player instanceof ServerPlayerEntity serverPlayer) {
//                    Criteria.TAME_ANIMAL.trigger(serverPlayer, this);
//                }
                this.world.sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);
            } else {
                this.world.sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
            }
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.world.isClient && this.isAlive() && this.canMoveVoluntarily()) {
            ++this.eatingTime;
            ItemStack stack = this.getEquippedStack(EquipmentSlot.MAINHAND);
            if (this.canEat(stack)) {
                if (this.eatingTime > 600) {
                    ItemStack usedStack = stack.finishUsing(this.world, this);
                    if (this.getHealth() < this.getMaxHealth() && stack.getItem().getFoodComponent() != null) {
                        this.heal(stack.getItem().getFoodComponent().getHunger());
                    }
                    if (!usedStack.isEmpty()) {
                        this.equipStack(EquipmentSlot.MAINHAND, usedStack);
                    }
                    this.eatingTime = 0;
                    return;
                }
                if (this.eatingTime > 560 && this.random.nextFloat() < 0.1f) {
                    this.playSound(this.getEatSound(stack), 1.0f, 1.0f);
                    this.world.sendEntityStatus(this, (byte) 45);
                }
            }
        }
    }

    private boolean canEat(ItemStack stack) {
        return stack.getItem().isFood() && this.onGround && !this.isSleeping();
    }

    protected void showEmoteParticle(boolean positive) {
        ParticleEffect particleEffect = positive ? ParticleTypes.HEART : ParticleTypes.SMOKE;

        for (int i = 0; i < 7; ++i) {
            double d = this.random.nextGaussian() * 0.02;
            double e = this.random.nextGaussian() * 0.02;
            double f = this.random.nextGaussian() * 0.02;
            this.world.addParticle(particleEffect, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), d, e, f);
        }
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES) {
            this.showEmoteParticle(true);
        } else if (status == EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES) {
            this.showEmoteParticle(false);
        } else {
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
            UUID uUID = this.getTrustedUuid();
            return uUID == null ? null : this.world.getPlayerByUuid(uUID);
        } catch (IllegalArgumentException var2) {
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
