package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.util.MemoryList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.util.math.Vec3d;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.registry.SBLMemoryTypes;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class SetItemWalkTargetTask<E extends BirdEntity> extends ExtendedBehaviour<E> {
    private static final MemoryList MEMORY_REQUIREMENTS = MemoryList.create(4)
        .registered(
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS
        )
        .present(
            SBLMemoryTypes.NEARBY_ITEMS.get()
        );
    protected BiFunction<E, Vec3d, Float> speedModifier = (entity, targetPos) -> 1f;
    protected Function<E, Integer> radius = entity -> 10;

    public SetItemWalkTargetTask<E> speedModifier(float modifier) {
        return this.speedModifier((entity, targetPos) -> modifier);
    }

    public SetItemWalkTargetTask<E> speedModifier(BiFunction<E, Vec3d, Float> function) {
        this.speedModifier = function;

        return this;
    }

    public SetItemWalkTargetTask<E> radius(int radius) {
        return this.radius(entity -> radius);
    }

    public SetItemWalkTargetTask<E> radius(Function<E, Integer> function) {
        this.radius = function;

        return this;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryModuleState>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected void start(E entity) {
        Brain<?> brain = entity.getBrain();
        List<ItemEntity> wantedItems = BrainUtils.getMemory(brain, SBLMemoryTypes.NEARBY_ITEMS.get());
        // noinspection ConstantConditions
        ItemEntity targetItem = wantedItems.getFirst();
        if (!BrainUtils.hasMemory(brain, MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS)
            && targetItem.isInRange(entity, this.radius.apply(entity))
            && entity.getWorld().getWorldBorder().contains(targetItem.getBlockPos())
        ) {
            WalkTarget newWalkTarget = new WalkTarget(new EntityLookTarget(targetItem, false), this.speedModifier.apply(entity, targetItem.getPos()), 0);
            BrainUtils.setMemory(brain, MemoryModuleType.LOOK_TARGET, new EntityLookTarget(targetItem, true));
            BrainUtils.setMemory(brain, MemoryModuleType.WALK_TARGET, newWalkTarget);
        }
    }
}
