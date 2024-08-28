package aqario.fowlplay.common.world.gen;

import aqario.fowlplay.common.entity.FowlPlayEntityType;
import aqario.fowlplay.common.entity.RobinEntity;
import aqario.fowlplay.common.tags.FowlPlayBiomeTags;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.Heightmap;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;

public class FowlPlayEntitySpawn {
    public static void addEntitySpawn() {
        BiomeModifications.addSpawn(biome -> biome.isIn(FowlPlayBiomeTags.SPAWNS_CARDINALS),
            SpawnGroup.CREATURE, FowlPlayEntityType.CARDINAL, 75, 1, 3
        );
        SpawnRestriction.register(FowlPlayEntityType.CARDINAL, SpawnRestriction.Location.NO_RESTRICTIONS,
            Heightmap.Type.MOTION_BLOCKING, RobinEntity::canSpawn
        );

        BiomeModifications.addSpawn(biome -> biome.isIn(FowlPlayBiomeTags.SPAWNS_PENGUINS),
            SpawnGroup.CREATURE, FowlPlayEntityType.PENGUIN, 6, 40, 60
        );
        SpawnRestriction.register(FowlPlayEntityType.PENGUIN, SpawnRestriction.Location.ON_GROUND,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn
        );

        BiomeModifications.addSpawn(biome -> biome.isIn(FowlPlayBiomeTags.SPAWNS_PIGEONS),
            SpawnGroup.CREATURE, FowlPlayEntityType.PIGEON, 30, 4, 8
        );
        SpawnRestriction.register(FowlPlayEntityType.PIGEON, SpawnRestriction.Location.ON_GROUND,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn
        );

        BiomeModifications.addSpawn(biome -> biome.isIn(FowlPlayBiomeTags.SPAWNS_ROBINS),
            SpawnGroup.CREATURE, FowlPlayEntityType.ROBIN, 75, 2, 4
        );
        SpawnRestriction.register(FowlPlayEntityType.ROBIN, SpawnRestriction.Location.NO_RESTRICTIONS,
            Heightmap.Type.MOTION_BLOCKING, RobinEntity::canSpawn
        );

        BiomeModifications.addSpawn(biome -> biome.isIn(FowlPlayBiomeTags.SPAWNS_SEAGULLS),
            SpawnGroup.AMBIENT, FowlPlayEntityType.SEAGULL, 75, 5, 12
        );
        SpawnRestriction.register(FowlPlayEntityType.SEAGULL, SpawnRestriction.Location.ON_GROUND,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn
        );
    }
}
