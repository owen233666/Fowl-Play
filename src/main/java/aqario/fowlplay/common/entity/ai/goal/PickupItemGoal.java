package aqario.fowlplay.common.entity.ai.goal;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;

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
        if (animal.getTarget() != null || animal.getAttacker() != null) {
            return false;
        }
        List<ItemEntity> list = animal.getWorld()
            .getEntitiesByClass(ItemEntity.class, animal.getBoundingBox().expand(32.0, 32.0, 32.0), PICKABLE_DROP_FILTER);
        return !list.isEmpty() && animal.canPickupItem(list.get(0).getStack());
    }

    @Override
    public void tick() {
        List<ItemEntity> list = animal.getWorld()
            .getEntitiesByClass(ItemEntity.class, animal.getBoundingBox().expand(32.0, 32.0, 32.0), PICKABLE_DROP_FILTER);
        if (!list.isEmpty() && animal.canPickupItem(list.get(0).getStack())) {
            animal.getNavigation().startMovingTo(list.get(0), 1.75F);
        }
    }

    @Override
    public void start() {
        List<ItemEntity> list = animal.getWorld()
            .getEntitiesByClass(ItemEntity.class, animal.getBoundingBox().expand(32.0, 32.0, 32.0), PICKABLE_DROP_FILTER);
        if (!list.isEmpty()) {
            animal.getNavigation().startMovingTo(list.get(0), 1.75F);
        }
    }
}