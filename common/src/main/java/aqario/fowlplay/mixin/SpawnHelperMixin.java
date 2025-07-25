package aqario.fowlplay.mixin;

import aqario.fowlplay.common.entity.CustomSpawnGroup;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpawnHelper.class)
public class SpawnHelperMixin {
    @Inject(
        method = "spawnEntitiesInChunk(Lnet/minecraft/entity/SpawnGroup;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/world/chunk/WorldChunk;Lnet/minecraft/world/SpawnHelper$Checker;Lnet/minecraft/world/SpawnHelper$Runner;)V",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void fowlplay$reduceSpawningFrequency(SpawnGroup group, ServerWorld world, WorldChunk chunk, SpawnHelper.Checker checker, SpawnHelper.Runner runner, CallbackInfo ci) {
        if(group == CustomSpawnGroup.BIRDS.spawnGroup && world.getLevelProperties().getTime() % 20L != 0L) {
            ci.cancel();
        }
    }

    @Redirect(
        method = "spawnEntitiesInChunk(Lnet/minecraft/entity/SpawnGroup;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/world/chunk/Chunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/SpawnHelper$Checker;Lnet/minecraft/world/SpawnHelper$Runner;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/util/math/BlockPos;getY()I"
        )
    )
    private static int fowlplay$midairSpawning(BlockPos pos, SpawnGroup group, ServerWorld world) {
        if((group == CustomSpawnGroup.BIRDS.spawnGroup || group == CustomSpawnGroup.AMBIENT_BIRDS.spawnGroup) && world.getRandom().nextFloat() < 0.01F) {
            return pos.getY() + world.getRandom().nextBetween(32, 64);
        }
        return pos.getY();
    }
}
