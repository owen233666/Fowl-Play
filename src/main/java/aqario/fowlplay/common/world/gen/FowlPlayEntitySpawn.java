package aqario.fowlplay.common.world.gen;

import aqario.fowlplay.common.entity.FowlPlayEntityType;
import aqario.fowlplay.common.entity.RobinEntity;
import aqario.fowlplay.common.tags.FowlPlayBiomeTags;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.Heightmap;

public class FowlPlayEntitySpawn {
    public static void addEntitySpawn() {
        BiomeModifications.addSpawn(biome -> biome.hasTag(FowlPlayBiomeTags.SPAWNS_BLUE_JAYS),
            SpawnGroup.CREATURE, FowlPlayEntityType.BLUE_JAY, 75, 1, 3
        );
        SpawnRestriction.register(FowlPlayEntityType.BLUE_JAY, SpawnLocationTypes.UNRESTRICTED,
            Heightmap.Type.MOTION_BLOCKING, RobinEntity::canSpawn
        );

        BiomeModifications.addSpawn(biome -> biome.hasTag(FowlPlayBiomeTags.SPAWNS_CARDINALS),
            SpawnGroup.CREATURE, FowlPlayEntityType.CARDINAL, 75, 1, 3
        );
        SpawnRestriction.register(FowlPlayEntityType.CARDINAL, SpawnLocationTypes.UNRESTRICTED,
            Heightmap.Type.MOTION_BLOCKING, RobinEntity::canSpawn
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
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn
        );

        BiomeModifications.addSpawn(biome -> biome.hasTag(FowlPlayBiomeTags.SPAWNS_ROBINS),
            SpawnGroup.CREATURE, FowlPlayEntityType.ROBIN, 75, 2, 4
        );
        SpawnRestriction.register(FowlPlayEntityType.ROBIN, SpawnLocationTypes.UNRESTRICTED,
            Heightmap.Type.MOTION_BLOCKING, RobinEntity::canSpawn
        );

        BiomeModifications.addSpawn(biome -> biome.hasTag(FowlPlayBiomeTags.SPAWNS_SEAGULLS),
            SpawnGroup.AMBIENT, FowlPlayEntityType.SEAGULL, 75, 5, 12
        );
        SpawnRestriction.register(FowlPlayEntityType.SEAGULL, SpawnLocationTypes.ON_GROUND,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn
        );
    }
}
