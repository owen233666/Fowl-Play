package aqario.fowlplay.common.world.gen;

import aqario.fowlplay.common.entity.FowlPlayEntityType;
import aqario.fowlplay.common.entity.HawkEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.spawner.SpecialSpawner;

public class HawkSpawner implements SpecialSpawner {
    private int cooldown;

    @Override
    public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
        if (!spawnAnimals) {
            return 0;
        }
        Random random = world.random;
        this.cooldown--;
        if (this.cooldown > 0) {
            return 0;
        }
        this.cooldown = this.cooldown + (60 + random.nextInt(60)) * 20;
        if (world.getAmbientDarkness() < 5 && world.getDimension().hasSkyLight()) {
            return 0;
        }
        int i = 0;

        for (ServerPlayerEntity player : world.getPlayers()) {
            if (player.isSpectator()) {
                continue;
            }
            BlockPos pos = player.getBlockPos();
            if (!world.getDimension().hasSkyLight() || pos.getY() >= world.getSeaLevel() && world.isSkyVisible(pos)) {
                LocalDifficulty difficulty = world.getLocalDifficulty(pos);
                if (difficulty.isHarderThan(random.nextFloat() * 3.0F)) {
                    ServerStatHandler serverStatHandler = player.getStatHandler();
                    int j = MathHelper.clamp(serverStatHandler.getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE);
                    if (random.nextInt(j) >= 72000) {
                        BlockPos pos2 = pos
                            .up(20 + random.nextInt(15))
                            .east(-10 + random.nextInt(21))
                            .south(-10 + random.nextInt(21));
                        BlockState block = world.getBlockState(pos2);
                        FluidState fluid = world.getFluidState(pos2);
                        if (SpawnHelper.isClearForSpawn(world, pos2, block, fluid, FowlPlayEntityType.HAWK)) {
                            EntityData entityData = null;
                            int l = 1 + random.nextInt(difficulty.getGlobalDifficulty().getId() + 1);

                            for (int m = 0; m < l; m++) {
                                HawkEntity hawk = FowlPlayEntityType.HAWK.create(world);
                                if (hawk != null) {
                                    hawk.refreshPositionAndAngles(pos2, 0.0F, 0.0F);
                                    entityData = hawk.initialize(world, difficulty, SpawnReason.NATURAL, entityData);
                                    world.spawnEntityAndPassengers(hawk);
                                    i++;
                                }
                            }
                        }
                    }
                }
            }
        }

        return i;
    }
}
