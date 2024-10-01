package aqario.fowlplay.common.world.gen;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.entity.FowlPlayEntityType;
import aqario.fowlplay.common.entity.PenguinEntity;
import aqario.fowlplay.common.tags.FowlPlayBiomeTags;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;

public final class FowlPlayWorldGen {
    public static void init() {
        SpawnRestriction.register(FowlPlayEntityType.BLUE_JAY, SpawnLocationTypes.UNRESTRICTED,
            Heightmap.Type.MOTION_BLOCKING, FlyingBirdEntity::canSpawnPasserines
        );
        BiomeModifications.addSpawn(biome -> biome.getBiomeRegistryEntry().isIn(FowlPlayBiomeTags.SPAWNS_BLUE_JAYS),
            SpawnGroup.AMBIENT, FowlPlayEntityType.BLUE_JAY, 10, 1, 3
        );

        SpawnRestriction.register(FowlPlayEntityType.CARDINAL, SpawnLocationTypes.UNRESTRICTED,
            Heightmap.Type.MOTION_BLOCKING, FlyingBirdEntity::canSpawnPasserines
        );
        BiomeModifications.addSpawn(biome -> biome.getBiomeRegistryEntry().isIn(FowlPlayBiomeTags.SPAWNS_CARDINALS),
            SpawnGroup.AMBIENT, FowlPlayEntityType.CARDINAL, 10, 1, 3
        );

        SpawnRestriction.register(FowlPlayEntityType.GULL, SpawnLocationTypes.UNRESTRICTED,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FlyingBirdEntity::canSpawnShorebirds
        );
        BiomeModifications.addSpawn(biome -> biome.getBiomeRegistryEntry().isIn(FowlPlayBiomeTags.SPAWNS_GULLS),
            SpawnGroup.CREATURE, FowlPlayEntityType.GULL, 10, 5, 12
        );

        SpawnRestriction.register(FowlPlayEntityType.PENGUIN, SpawnLocationTypes.UNRESTRICTED,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PenguinEntity::canSpawn
        );
        BiomeModifications.addSpawn(biome -> biome.getBiomeRegistryEntry().isIn(FowlPlayBiomeTags.SPAWNS_PENGUINS),
            SpawnGroup.CREATURE, FowlPlayEntityType.PENGUIN, 1, 1, 2
        );

        SpawnRestriction.register(FowlPlayEntityType.PIGEON, SpawnLocationTypes.ON_GROUND,
            Heightmap.Type.MOTION_BLOCKING, FlyingBirdEntity::canSpawnShorebirds
        );
        BiomeModifications.addSpawn(biome -> biome.getBiomeRegistryEntry().isIn(FowlPlayBiomeTags.SPAWNS_PIGEONS),
            SpawnGroup.CREATURE, FowlPlayEntityType.PIGEON, 10, 4, 8
        );

        SpawnRestriction.register(FowlPlayEntityType.ROBIN, SpawnLocationTypes.UNRESTRICTED,
            Heightmap.Type.MOTION_BLOCKING, FlyingBirdEntity::canSpawnPasserines
        );
        BiomeModifications.addSpawn(biome -> biome.getBiomeRegistryEntry().isIn(FowlPlayBiomeTags.SPAWNS_ROBINS),
            SpawnGroup.AMBIENT, FowlPlayEntityType.ROBIN, 10, 2, 4
        );
    }
}
