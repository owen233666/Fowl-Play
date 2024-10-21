package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.entity.ai.control.BirdBodyControl;
import aqario.fowlplay.common.entity.ai.control.BirdLookControl;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.BodyControl;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class BirdEntity extends AnimalEntity {
    private int eatingTime;

    protected BirdEntity(EntityType<? extends BirdEntity> entityType, World world) {
        super(entityType, world);
        this.setCanPickUpLoot(true);
        this.lookControl = new BirdLookControl(this, 85);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0f)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f);
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
        return this.getFood().test(stack)/* && !this.isSleeping()*/;
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

    @Override
    protected MoveEffect getMoveEffect() {
        return MoveEffect.SOUNDS;
    }

    @Override
    public float getSoundPitch() {
        return (this.random.nextFloat() - this.random.nextFloat()) * 0.05F + 1.0F;
    }
}
