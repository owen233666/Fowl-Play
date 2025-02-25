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
        public boolean isSpawnPositionOk(WorldView worldView, BlockPos spawnPos, @Nullable EntityType<?> entityType) {
            if (entityType != null && worldView.getWorldBorder().contains(spawnPos)) {
                BlockPos headPos = spawnPos.up();
                return this.isClearForSpawn(worldView, spawnPos, entityType) && this.isClearForSpawn(worldView, headPos, entityType);
            }
            return false;
        }

        private boolean isClearForSpawn(WorldView world, BlockPos pos, EntityType<?> entityType) {
            BlockState blockState = world.getBlockState(pos);
            return SpawnHelper.isClearForSpawn(world, pos, blockState, blockState.getFluidState(), entityType);
        }

        @Override
        public BlockPos adjustPosition(WorldView world, BlockPos pos) {
            BlockPos blockPos = pos.down();
            return world.getBlockState(blockPos).canPathfindThrough(NavigationType.LAND) ? blockPos : pos;
        }
    };
    SpawnLocation SEMIAQUATIC = new SpawnLocation() {
        @Override
        public boolean isSpawnPositionOk(WorldView worldView, BlockPos blockPos, @Nullable EntityType<?> entityType) {
            return spawnsInWater(worldView, blockPos, entityType) || spawnsOnGround(worldView, blockPos, entityType);
        }

        @Override
        public BlockPos adjustPosition(WorldView world, BlockPos pos) {
            BlockPos groundPos = pos.down();
            return world.getBlockState(groundPos).canPathfindThrough(NavigationType.LAND) ? groundPos : pos;
        }
    };

    private static boolean isClearForSpawn(WorldView world, BlockPos pos, EntityType<?> entityType) {
        BlockState blockState = world.getBlockState(pos);
        return SpawnHelper.isClearForSpawn(world, pos, blockState, blockState.getFluidState(), entityType);
    }

    private static boolean spawnsOnGround(WorldView world, BlockPos pos, EntityType<?> entityType) {
        if (entityType != null && world.getWorldBorder().contains(pos)) {
            BlockPos blockPos2 = pos.up();
            BlockPos blockPos3 = pos.down();
            BlockState blockState = world.getBlockState(blockPos3);
            if (!blockState.allowsSpawning(world, blockPos3, entityType)) {
                return false;
            }
            return isClearForSpawn(world, pos, entityType) && isClearForSpawn(world, blockPos2, entityType);

        }
        return false;
    }

    private static boolean spawnsInWater(WorldView world, BlockPos pos, EntityType<?> entityType) {
        if (entityType != null && world.getWorldBorder().contains(pos)) {
            BlockPos headPos = pos.up();
            return world.getFluidState(pos).isIn(FluidTags.WATER) && !world.getBlockState(headPos).isSolidBlock(world, headPos);
        }
        return false;
    }
}
