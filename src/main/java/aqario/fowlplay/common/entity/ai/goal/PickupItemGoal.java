package aqario.fowlplay.common.entity.ai.goal;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class PickupItemGoal extends Goal {
    static final Predicate<ItemEntity> PICKABLE_DROP_FILTER = Entity::isAlive;
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
        if (!list.isEmpty()) {
            for (ItemEntity item : list) {
                if (animal.canPickupItem(item.getStack())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void tick() {
        List<ItemEntity> list = animal.getWorld()
            .getEntitiesByClass(ItemEntity.class, animal.getBoundingBox().expand(32.0, 32.0, 32.0), PICKABLE_DROP_FILTER);
        if (!list.isEmpty()) {
            for (ItemEntity item : list) {
                if (animal.canPickupItem(item.getStack())) {
                    animal.getNavigation().startMovingTo(item, 1.75F);
                }
            }
        }
    }

    @Override
    public void start() {
        List<ItemEntity> list = animal.getWorld()
            .getEntitiesByClass(ItemEntity.class, animal.getBoundingBox().expand(32.0, 32.0, 32.0), PICKABLE_DROP_FILTER);
        if (!list.isEmpty()) {
            for (ItemEntity item : list) {
                if (animal.canPickupItem(item.getStack())) {
                    animal.getNavigation().startMovingTo(item, 1.75F);
                }
            }
        }
    }
}