package aqario.fowlplay.common.entity;

import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public abstract class TrustingBirdEntity extends BirdEntity {
    protected static final TrackedData<Optional<UUID>> TRUSTED = DataTracker.registerData(TrustingBirdEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);

    protected TrustingBirdEntity(EntityType<? extends BirdEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TRUSTED, Optional.empty());
    }

    protected NbtList toNbtList(UUID... values) {
        NbtList nbtList = new NbtList();

        for (UUID uuid : values) {
            nbtList.add(NbtString.of(uuid.toString()));
        }

        return nbtList;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.getTrustedUuid() != null) {
            nbt.putUuid("Trusted", this.getTrustedUuid());
            nbt.put("Trusted", this.toNbtList(this.getTrustedUuid()));
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        UUID uuid;
        if (nbt.containsUuid("Trusted")) {
            uuid = nbt.getUuid("Trusted");
        }
        else {
            String string = nbt.getString("Trusted");
            uuid = ServerConfigHandler.getPlayerUuidByName(this.getServer(), string);
        }

        if (uuid != null) {
            this.setTrustedUuid(uuid);
        }
    }

    @Override
    protected void loot(ItemEntity item) {
        super.loot(item);
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
}
