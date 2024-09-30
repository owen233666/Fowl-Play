package aqario.fowlplay.common.world.gen;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.entity.FowlPlayEntityType;
import aqario.fowlplay.common.tags.FowlPlayBiomeTags;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.Heightmap;

public final class FowlPlayWorldGen {
    public static void init() {
        BiomeModifications.addSpawn(biome -> biome.hasTag(FowlPlayBiomeTags.SPAWNS_BLUE_JAYS),
            SpawnGroup.AMBIENT, FowlPlayEntityType.BLUE_JAY, 50, 1, 3
        );
        SpawnRestriction.register(FowlPlayEntityType.BLUE_JAY, SpawnLocationTypes.UNRESTRICTED,
            Heightmap.Type.MOTION_BLOCKING, FlyingBirdEntity::canSpawn
        );

        BiomeModifications.addSpawn(biome -> biome.hasTag(FowlPlayBiomeTags.SPAWNS_CARDINALS),
            SpawnGroup.AMBIENT, FowlPlayEntityType.CARDINAL, 50, 1, 3
        );
        SpawnRestriction.register(FowlPlayEntityType.CARDINAL, SpawnLocationTypes.UNRESTRICTED,
            Heightmap.Type.MOTION_BLOCKING, FlyingBirdEntity::canSpawn
        );

        BiomeModifications.addSpawn(biome -> biome.hasTag(FowlPlayBiomeTags.SPAWNS_GULLS),
            SpawnGroup.AMBIENT, FowlPlayEntityType.GULL, 50, 5, 12
        );
        SpawnRestriction.register(FowlPlayEntityType.GULL, SpawnLocationTypes.ON_GROUND,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn
        );

        BiomeModifications.addSpawn(biome -> biome.hasTag(FowlPlayBiomeTags.SPAWNS_PENGUINS),
            SpawnGroup.CREATURE, FowlPlayEntityType.PENGUIN, 6, 40, 60
        );
        SpawnRestriction.register(FowlPlayEntityType.PENGUIN, SpawnLocationTypes.ON_GROUND,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn
        );

        BiomeModifications.addSpawn(biome -> biome.hasTag(FowlPlayBiomeTags.SPAWNS_PIGEONS),
            SpawnGroup.CREATURE, FowlPlayEntityType.PIGEON, 30, 4, 8
        );
        SpawnRestriction.register(FowlPlayEntityType.PIGEON, SpawnLocationTypes.ON_GROUND,
            Heightmap.Type.MOTION_BLOCKING, AnimalEntity::isValidNaturalSpawn
        );

        BiomeModifications.addSpawn(biome -> biome.hasTag(FowlPlayBiomeTags.SPAWNS_ROBINS),
            SpawnGroup.AMBIENT, FowlPlayEntityType.ROBIN, 50, 2, 4
        );
        SpawnRestriction.register(FowlPlayEntityType.ROBIN, SpawnLocationTypes.UNRESTRICTED,
            Heightmap.Type.MOTION_BLOCKING, FlyingBirdEntity::canSpawn
        );
    }
}
