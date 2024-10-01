package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.entity.ai.control.BirdFlightMoveControl;
import aqario.fowlplay.common.entity.ai.control.BirdMoveControl;
import aqario.fowlplay.common.entity.ai.pathing.BirdNavigation;
import aqario.fowlplay.common.tags.FowlPlayBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public abstract class FlyingBirdEntity extends BirdEntity {
    private static final TrackedData<Boolean> FLYING = DataTracker.registerData(FlyingBirdEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private boolean isFlightMoveControl;
    public int timeFlying = 0;

    protected FlyingBirdEntity(EntityType<? extends BirdEntity> entityType, World world) {
        super(entityType, world);
        this.setMoveControl(false);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0f)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f)
            .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.2f);
    }

    @SuppressWarnings("unused")
    public static boolean canSpawnPasserines(EntityType<? extends BirdEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
        return world.getBlockState(pos.down()).isIn(FowlPlayBlockTags.PASSERINES_SPAWNABLE_ON)/* && isBrightEnoughForNaturalSpawn(world, pos)*/;
    }

    @SuppressWarnings("unused")
    public static boolean canSpawnShorebirds(EntityType<? extends BirdEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
        return world.getBlockState(pos.down()).isIn(FowlPlayBlockTags.SHOREBIRDS_SPAWNABLE_ON);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        setMoveControl(this.isFlying());
        return this.navigation;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(FLYING, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("flying", this.isFlying());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setFlying(nbt.getBoolean("flying"));
    }

    public abstract int getFlapFrequency();

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
            if (this.isFlying()) {
                this.timeFlying++;
                this.setNoGravity(true);
                this.fallDistance = 0.0F;
                if (this.isOnGround() || this.isTouchingWater()) {
                    this.setFlying(false);
                }
            }
            else {
                this.timeFlying = 0;
                this.setNoGravity(false);
            }
            if (this.isFlying() != this.isFlightMoveControl) {
                this.setMoveControl(this.isFlying());
            }
        }
    }

    protected void setMoveControl(boolean isFlying) {
        if (isFlying) {
            this.moveControl = new BirdFlightMoveControl(this, 40);
            BirdNavigation birdNavigation = new BirdNavigation(this, getWorld());
            birdNavigation.setCanPathThroughDoors(false);
            birdNavigation.setCanEnterOpenDoors(true);
            birdNavigation.setCanSwim(false);
            this.navigation = birdNavigation;
            this.isFlightMoveControl = true;
        }
        else {
            this.moveControl = new BirdMoveControl(this);
            this.navigation = new MobNavigation(this, getWorld());
            this.isFlightMoveControl = false;
        }
    }

    @Override
    protected float getAirSpeed() {
        return this.isFlying() ? this.getMovementSpeed() : super.getAirSpeed();
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return !this.isFlying() && super.handleFallDamage(fallDistance, damageMultiplier, damageSource);
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
        if (!this.isFlying()) {
            super.fall(heightDifference, onGround, landedState, landedPosition);
        }
    }

    public boolean isFlying() {
        return this.dataTracker.get(FLYING);
    }

    public void setFlying(boolean flying) {
        this.dataTracker.set(FLYING, flying);
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (!this.isFlying()) {
            super.travel(movementInput);
            return;
        }

        if (this.isLogicalSideForUpdatingMovement()) {
            if (this.isTouchingWater()) {
                this.updateVelocity(0.02F, movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                this.setVelocity(this.getVelocity().multiply(0.8F));
            }
            else if (this.isInLava()) {
                this.updateVelocity(0.02F, movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                this.setVelocity(this.getVelocity().multiply(0.5));
            }
            else {
                float friction = 0.75F;

                this.updateVelocity(this.getMovementSpeed(), movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                this.setVelocity(this.getVelocity().multiply(friction));
            }
        }
    }
}
