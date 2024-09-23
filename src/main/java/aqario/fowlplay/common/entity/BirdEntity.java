package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.entity.ai.control.BirdBodyControl;
import aqario.fowlplay.common.entity.ai.control.BirdFlightMoveControl;
import aqario.fowlplay.common.entity.ai.control.BirdLookControl;
import aqario.fowlplay.common.entity.ai.control.BirdMoveControl;
import aqario.fowlplay.common.entity.ai.pathing.BirdNavigation;
import aqario.fowlplay.common.tags.FowlPlayBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.BodyControl;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public abstract class BirdEntity extends AnimalEntity {
    private static final TrackedData<Boolean> FLYING = DataTracker.registerData(BirdEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private boolean isFlightMoveControl;
    public int timeFlying = 0;
    private int eatingTime;

    protected BirdEntity(EntityType<? extends BirdEntity> entityType, World world) {
        super(entityType, world);
        this.setCanPickUpLoot(true);
        this.setMoveControl(false);
        this.lookControl = new BirdLookControl(this);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0f)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f)
            .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.2f);
    }

    @Override
    public boolean canEquip(ItemStack stack) {
        EquipmentSlot equipmentSlot = this.getPreferredEquipmentSlot(stack);
        if (!this.getEquippedStack(equipmentSlot).isEmpty()) {
            return false;
        }
        return equipmentSlot == EquipmentSlot.MAINHAND && super.canEquip(stack);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    public abstract Ingredient getFood();

    @Override
    public boolean canPickupItem(ItemStack stack) {
        ItemStack heldStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
        return this.getFood().test(stack) && !this.getFood().test(heldStack);
    }

    private void dropWithoutDelay(ItemStack stack, Entity thrower) {
        ItemEntity item = new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), stack);
        if (thrower != null) {
            item.setThrower(thrower);
        }
        this.getWorld().spawnEntity(item);
    }

    @Override
    protected void loot(ItemEntity item) {
        Entity thrower = item.getOwner();
        ItemStack stack = item.getStack();
        if (this.canPickupItem(stack)) {
            int i = stack.getCount();
            if (i > 1) {
                this.dropWithoutDelay(stack.split(i - 1), thrower);
            }
            this.dropStack(this.getEquippedStack(EquipmentSlot.MAINHAND));
            this.triggerItemPickedUpByEntityCriteria(item);
            this.equipStack(EquipmentSlot.MAINHAND, stack.split(1));
            this.updateDropChances(EquipmentSlot.MAINHAND);
            this.sendPickup(item, stack.getCount());
            item.discard();
            this.eatingTime = 0;
        }
    }

    private boolean canEat(ItemStack stack) {
        return this.getFood().test(stack) && this.isOnGround()/* && !this.isSleeping()*/;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.getWorld().isClient && this.isAlive()) {
            ++this.eatingTime;
            ItemStack stack = this.getEquippedStack(EquipmentSlot.MAINHAND);
            if (this.canEat(stack)) {
                if ((this.eatingTime > 40 && this.random.nextFloat() < 0.05f) || this.eatingTime > 200) {
                    if (stack.getItem().getComponents().contains(DataComponentTypes.FOOD)) {
                        this.heal(stack.getItem().getComponents().get(DataComponentTypes.FOOD).nutrition());
                    }
                    else {
                        stack.decrement(1);
                    }
                    ItemStack usedStack = stack.finishUsing(this.getWorld(), this);
                    if (!usedStack.isEmpty()) {
                        this.equipStack(EquipmentSlot.MAINHAND, usedStack);
                    }
                    this.playSound(this.getEatSound(stack), 1.0f, 1.0f);
                    this.getWorld().sendEntityStatus(this, EntityStatuses.CREATE_EATING_PARTICLES);
                    this.eatingTime = 0;
                    return;
                }
                if (this.eatingTime > 20 && this.random.nextFloat() < 0.05f) {
                    this.playSound(this.getEatSound(stack), 1.0f, 1.0f);
                    this.getWorld().sendEntityStatus(this, EntityStatuses.CREATE_EATING_PARTICLES);
                }
            }
            else if (!stack.isEmpty() && !this.getFood().test(stack)) {
                if (this.random.nextFloat() < 0.1f) {
                    this.dropStack(this.getEquippedStack(EquipmentSlot.MAINHAND));
                    this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                }
            }
        }
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.CREATE_EATING_PARTICLES) {
            ItemStack food = this.getEquippedStack(EquipmentSlot.MAINHAND);
            if (!food.isEmpty()) {
                for (int i = 0; i < 8; i++) {
                    Vec3d vec3d = new Vec3d(((double) this.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0)
                        .rotateX(-this.getPitch() * (float) (Math.PI / 180.0))
                        .rotateY(-this.getYaw() * (float) (Math.PI / 180.0));
                    this.getWorld().addParticle(
                        new ItemStackParticleEffect(ParticleTypes.ITEM, food),
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

    @Override
    protected EntityNavigation createNavigation(World world) {
        if (!this.isFlying()) {
            MobNavigation mobNavigation = new MobNavigation(this, this.getWorld());
            mobNavigation.setCanPathThroughDoors(false);
            mobNavigation.setCanSwim(true);
            mobNavigation.setCanEnterOpenDoors(true);
            return mobNavigation;
        }
        BirdNavigation birdNavigation = new BirdNavigation(this, this.getWorld());
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanEnterOpenDoors(true);
        birdNavigation.setCanSwim(false);
        return birdNavigation;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(FLYING, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Flying", this.isFlying());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setFlying(nbt.getBoolean("Flying"));
    }

    @Override
    public int getLookYawSpeed() {
        return 100;
    }

    @Override
    public int getLookPitchSpeed() {
        return 100;
    }

    @Override
    public int getBodyYawSpeed() {
        return 270;
    }

    @Override
    protected BodyControl createBodyControl() {
        return new BirdBodyControl(this);
    }

    public abstract int getFlapFrequency();

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
            if (this.isFlying()) {
                this.timeFlying++;
                this.setNoGravity(true);
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

    private void setMoveControl(boolean isFlying) {
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

    public void flap() {
        Vec3d vec3d = this.getVelocity();
        if (!this.isOnGround() && vec3d.y < 0.0) {
            this.setUpwardSpeed(this.getMovementSpeed());
        }
    }

    @Override
    protected float getAirSpeed() {
        return this.isFlying() ? this.getMovementSpeed() : super.getAirSpeed();
    }

    public static boolean canSpawn(EntityType<? extends BirdEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
        return world.getBlockState(pos.down()).isIn(FowlPlayBlockTags.PASSERINES_SPAWNABLE_ON) && isBrightEnoughForNaturalSpawn(world, pos);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return !this.isFlying() && super.handleFallDamage(fallDistance, damageMultiplier, damageSource);
    }

    @Override
    protected int computeFallDamage(float fallDistance, float damageMultiplier) {
        return super.computeFallDamage(fallDistance, damageMultiplier) / 2;
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

    public boolean canAccessTarget(Vec3d target) {
        Vec3d vec3d = new Vec3d(this.getX(), this.getEyeY(), this.getZ());

        return this.getWorld().raycast(new RaycastContext(vec3d, target, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this)).getType() == HitResult.Type.MISS;
    }

    public Vec3d getBlockInViewAway(Vec3d fleePos, float radiusAdd) {
        final float radius = 3.15F * -3 - this.getRandom().nextInt(24) - radiusAdd;
        final float angle = getAngle();
        final double extraX = radius * Math.sin((float) (Math.PI + angle));
        final double extraZ = radius * Math.cos(angle);
        final BlockPos radialPos = new BlockPos((int) (fleePos.getX() + extraX), 0, (int) (fleePos.getZ() + extraZ));
        final BlockPos ground = getGroundPos(radialPos);
        final int distFromGround = (int) this.getY() - ground.getY();

        final BlockPos newPos;
        if (distFromGround > 8) {
            final int flightHeight = 4 + this.getRandom().nextInt(10);
            newPos = ground.up(flightHeight);
        }
        else {
            newPos = ground.up(this.getRandom().nextInt(6) + 1);
        }

        if (this.canAccessTarget(Vec3d.ofCenter(newPos)) && this.squaredDistanceTo(Vec3d.ofCenter(newPos)) > 1) {
            return Vec3d.ofCenter(newPos);
        }
        return null;
    }

    private BlockPos getGroundPos(BlockPos in) {
        BlockPos pos = new BlockPos(in.getX(), (int) this.getY(), in.getZ());
        while (pos.getY() > -64 && !getWorld().getBlockState(pos).isSolid() && getWorld().getFluidState(pos).isEmpty()) {
            pos = pos.down();
        }
        return pos;
    }

    public Vec3d getBlockGrounding(Vec3d fleePos) {
        final float radius = 3.15F * -3 - this.getRandom().nextInt(24);
        final float angle = getAngle();
        final double extraX = radius * Math.sin((float) (Math.PI + angle));
        final double extraZ = radius * Math.cos(angle);
        final BlockPos radialPos = new BlockPos((int) (fleePos.getX() + extraX), (int) getY(), (int) (fleePos.getZ() + extraZ));
        BlockPos ground = this.getGroundPos(radialPos);
        if (ground.getY() == -64) {
            return this.getPos();
        }
        else {
            ground = this.getBlockPos();
            while (ground.getY() > -64 && !getWorld().getBlockState(ground).isSolid()) {
                ground = ground.down();
            }
        }
        if (this.canAccessTarget(Vec3d.ofCenter(ground.up()))) {
            return Vec3d.ofCenter(ground);
        }
        return null;
    }

    private float getAngle() {
        final float neg = this.getRandom().nextBoolean() ? 1 : -1;
        final float renderYawOffset = this.bodyYaw;
        return (0.0174532925F * renderYawOffset) + 3.15F + (this.getRandom().nextFloat() * neg);
    }

    public boolean isOverWater() {
        BlockPos pos = this.getBlockPos();
        while (pos.getY() > -64 && this.getWorld().isAir(pos)) {
            pos = pos.down();
        }
        return !this.getWorld().getFluidState(pos).isEmpty();
    }

    @Override
    protected MoveEffect getMoveEffect() {
        return MoveEffect.SOUNDS;
    }

    @Override
    public float getSoundPitch() {
        return (this.random.nextFloat() - this.random.nextFloat()) * 0.05F + 1.0F;
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
