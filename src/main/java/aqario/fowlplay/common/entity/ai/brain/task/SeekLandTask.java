package aqario.fowlplay.common.entity.ai.brain.task;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.brain.BlockPosLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.TaskBuilder;
import net.minecraft.entity.ai.brain.task.TaskControl;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;

public class SeekLandTask {
    public static TaskControl<PathAwareEntity> create(int range, float speed) {
        return TaskBuilder.task(
            instance -> instance.group(
                    instance.presentMemory(MemoryModuleType.HAS_HUNTING_COOLDOWN),
                    instance.absentMemory(MemoryModuleType.ATTACK_TARGET),
                    instance.absentMemory(MemoryModuleType.WALK_TARGET),
                    instance.registeredMemory(MemoryModuleType.LOOK_TARGET)
                )
                .apply(instance, (huntingCooldown, attackTarget, walkTarget, lookTarget) -> (world, entity, time) -> {
                    if (!world.getFluidState(entity.getBlockPos()).isIn(FluidTags.WATER)) {
                        return false;
                    }
                    BlockPos targetPos = null;
                    BlockPos entityBlockPos = entity.getBlockPos();

                    for (BlockPos pos : BlockPos.iterateOutwards(entityBlockPos, range, range, range)) {
                        if (pos.getX() != entityBlockPos.getX() || pos.getZ() != entityBlockPos.getZ()) {
                            BlockState aboveCurrentBlock = entity.getWorld().getBlockState(pos.up());
                            BlockState currentBlock = entity.getWorld().getBlockState(pos);
                            if (!currentBlock.isOf(Blocks.WATER) && !currentBlock.isAir() && aboveCurrentBlock.isAir()) {
                                targetPos = pos.up(2).toImmutable();
                                break;
                            }
                        }
                    }

                    if (targetPos == null) {
                        return false;
                    }

                    lookTarget.remember(new BlockPosLookTarget(targetPos));
                    walkTarget.remember(new WalkTarget(new BlockPosLookTarget(targetPos), speed, 0));
                    return true;
                })
        );
    }
}
