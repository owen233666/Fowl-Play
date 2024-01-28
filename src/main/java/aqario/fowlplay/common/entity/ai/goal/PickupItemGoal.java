package aqario.fowlplay.common.entity.ai.goal;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class PickupItemGoal extends Goal {
    static final Predicate<ItemEntity> PICKABLE_DROP_FILTER = item -> !item.cannotPickup() && item.isAlive();
    private final AnimalEntity animal;

    public PickupItemGoal(AnimalEntity animal) {
        this.setControls(EnumSet.of(Goal.Control.MOVE));
        this.animal = animal;
    }

    @Override
    public boolean canStart() {
        if (!animal.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
            return false;
        }
        if (animal.getTarget() != null || animal.getAttacker() != null) {
            return false;
        }
        if (animal.getRandom().nextInt(toGoalTicks(10)) != 0) {
            return false;
        }
        List<ItemEntity> list = animal.world
            .getEntitiesByClass(ItemEntity.class, animal.getBoundingBox().expand(8.0, 8.0, 8.0), PICKABLE_DROP_FILTER);
        return !list.isEmpty() && animal.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty();
    }

    @Override
    public void tick() {
        List<ItemEntity> list = animal.world
            .getEntitiesByClass(ItemEntity.class, animal.getBoundingBox().expand(8.0, 8.0, 8.0), PICKABLE_DROP_FILTER);
        ItemStack itemStack = animal.getEquippedStack(EquipmentSlot.MAINHAND);
        if (itemStack.isEmpty() && !list.isEmpty()) {
            animal.getNavigation().startMovingTo(list.get(0), 1.2F);
        }
    }

    @Override
    public void start() {
        List<ItemEntity> list = animal.world
            .getEntitiesByClass(ItemEntity.class, animal.getBoundingBox().expand(8.0, 8.0, 8.0), PICKABLE_DROP_FILTER);
        if (!list.isEmpty()) {
            animal.getNavigation().startMovingTo(list.get(0), 1.2F);
        }
    }
}