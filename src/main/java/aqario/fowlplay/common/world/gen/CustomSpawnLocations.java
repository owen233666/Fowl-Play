package aqario.fowlplay.common.world.gen;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnLocation;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public interface CustomSpawnLocations {
    SpawnLocation GROUND = new SpawnLocation() {
        @Override
        public boolean isSpawnPositionOk(WorldView worldView, BlockPos spawnPos, @Nullable EntityType<?> entityType) {
            return spawnsOnGround(worldView, spawnPos, entityType);
        }

        @Override
        public BlockPos adjustPosition(WorldView world, BlockPos pos) {
            BlockPos blockPos = pos.down();
            return world.getBlockState(blockPos).canPathfindThrough(NavigationType.LAND) ? blockPos : pos;
        }
    };
    SpawnLocation SEMIAQUATIC = new SpawnLocation() {
        @Override
        public boolean isSpawnPositionOk(WorldView worldView, BlockPos spawnPos, @Nullable EntityType<?> entityType) {
            return spawnsOnWater(worldView, spawnPos, entityType) || spawnsOnGround(worldView, spawnPos, entityType);
        }

        @Override
        public BlockPos adjustPosition(WorldView world, BlockPos pos) {
            BlockPos groundPos = pos.down();
            return world.getBlockState(groundPos).canPathfindThrough(NavigationType.LAND) ? groundPos : pos;
        }
    };
    SpawnLocation AQUATIC = CustomSpawnLocations::spawnsOnWater;

    private static boolean isClearForSpawn(WorldView world, BlockPos pos, EntityType<?> entityType) {
        BlockState blockState = world.getBlockState(pos);
        return SpawnHelper.isClearForSpawn(world, pos, blockState, blockState.getFluidState(), entityType);
    }

    private static boolean spawnsOnGround(WorldView world, BlockPos spawnPos, EntityType<?> entityType) {
        if (entityType != null && world.getWorldBorder().contains(spawnPos)) {
            BlockPos headPos = spawnPos.up();
            return isClearForSpawn(world, spawnPos, entityType) && (entityType.getHeight() <= 1 || isClearForSpawn(world, headPos, entityType));
        }
        return false;
    }

    private static boolean spawnsOnWater(WorldView world, BlockPos spawnPos, EntityType<?> entityType) {
        if (entityType != null && world.getWorldBorder().contains(spawnPos)) {
            BlockPos headPos = spawnPos.up();
            return world.getFluidState(spawnPos.down()).isIn(FluidTags.WATER)
                && (entityType.getHeight() <= 1 || isClearForSpawn(world, headPos, entityType));
        }
        return false;
    }
}
