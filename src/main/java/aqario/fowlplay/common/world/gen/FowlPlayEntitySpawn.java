package aqario.fowlplay.common.world.gen;

import aqario.fowlplay.common.entity.FowlPlayEntityType;
import aqario.fowlplay.common.entity.RobinEntity;
import aqario.fowlplay.common.tags.FowlPlayBiomeTags;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors;

public class FowlPlayEntitySpawn {
    public static void addEntitySpawn() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SNOWY_PLAINS, BiomeKeys.SNOWY_BEACH),
            SpawnGroup.CREATURE, FowlPlayEntityType.PENGUIN, 6, 40, 60
        );
        SpawnRestriction.register(FowlPlayEntityType.PENGUIN, SpawnRestriction.Location.ON_GROUND,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn
        );

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.BEACH, BiomeKeys.STONY_SHORE),
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

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.BEACH, BiomeKeys.STONY_SHORE),
            SpawnGroup.AMBIENT, FowlPlayEntityType.SEAGULL, 75, 5, 12
        );
        SpawnRestriction.register(FowlPlayEntityType.SEAGULL, SpawnRestriction.Location.ON_GROUND,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn
        );
    }
}
