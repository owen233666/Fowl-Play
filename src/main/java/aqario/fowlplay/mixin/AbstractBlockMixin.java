package aqario.fowlplay.mixin;

import aqario.fowlplay.common.entity.ai.brain.Birds;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {
    @Inject(method = "getCollisionShape", at = @At("HEAD"), cancellable = true)
    private void fowlplay$changeLeavesCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        Block block = (Block) (Object) this;
        if (block instanceof LeavesBlock) {
            if (context instanceof EntityShapeContext entityContext) {
                Entity entity = entityContext.getEntity();
                if (entity != null && Birds.notFlightless(entity)) {
                    cir.setReturnValue(VoxelShapes.cuboid(0, 0, 0, 1, 0.75, 1));
                }
            }
        }
    }
}
