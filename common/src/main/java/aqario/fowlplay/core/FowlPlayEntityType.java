package aqario.fowlplay.core;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.*;
import aqario.fowlplay.common.util.EntityTypeBuilder;
import aqario.fowlplay.common.world.gen.CustomSpawnLocations;
import aqario.fowlplay.core.platform.PlatformHelper;
import aqario.fowlplay.core.tags.FowlPlayBiomeTags;
import dev.architectury.registry.level.biome.BiomeModifications;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;

import java.util.function.Supplier;

public final class FowlPlayEntityType {
    public static final Supplier<EntityType<BlueJayEntity>> BLUE_JAY = register("blue_jay",
        EntityTypeBuilder.create(
                BlueJayEntity::new,
                CustomSpawnGroup.AMBIENT_BIRDS.spawnGroup
            )
            .defaultAttributes(BlueJayEntity::createFlyingBirdAttributes)
            .spawnRestriction(
                CustomSpawnLocations.GROUND,
                Heightmap.Type.MOTION_BLOCKING,
                FlyingBirdEntity::canSpawnPasserines
            )
            .dimensions(0.4f, 0.55f)
            .eyeHeight(0.475f)
    );

    public static final Supplier<EntityType<CardinalEntity>> CARDINAL = register("cardinal",
        EntityTypeBuilder.create(
                CardinalEntity::new,
                CustomSpawnGroup.AMBIENT_BIRDS.spawnGroup
            )
            .defaultAttributes(CardinalEntity::createFlyingBirdAttributes)
            .spawnRestriction(
                CustomSpawnLocations.GROUND,
                Heightmap.Type.MOTION_BLOCKING,
                FlyingBirdEntity::canSpawnPasserines
            )
            .dimensions(0.4f, 0.55f)
            .eyeHeight(0.475f)
    );

    public static final Supplier<EntityType<ChickadeeEntity>> CHICKADEE = register("chickadee",
        EntityTypeBuilder.create(
                ChickadeeEntity::new,
                CustomSpawnGroup.AMBIENT_BIRDS.spawnGroup
            )
            .defaultAttributes(ChickadeeEntity::createFlyingBirdAttributes)
            .spawnRestriction(
                CustomSpawnLocations.GROUND,
                Heightmap.Type.MOTION_BLOCKING,
                FlyingBirdEntity::canSpawnPasserines
            )
            .dimensions(0.3f, 0.45f)
            .eyeHeight(0.4f)
    );

    public static final Supplier<EntityType<CrowEntity>> CROW = register("crow",
        EntityTypeBuilder.create(
                CrowEntity::new,
                CustomSpawnGroup.AMBIENT_BIRDS.spawnGroup
            )
            .defaultAttributes(CrowEntity::createCrowAttributes)
            .spawnRestriction(
                CustomSpawnLocations.GROUND,
                Heightmap.Type.MOTION_BLOCKING,
                FlyingBirdEntity::canSpawnPasserines
            )
            .dimensions(0.5f, 0.6f)
            .eyeHeight(0.55f)
    );

    public static final Supplier<EntityType<DuckEntity>> DUCK = register("duck",
        EntityTypeBuilder.create(
                DuckEntity::new,
                CustomSpawnGroup.BIRDS.spawnGroup
            )
            .defaultAttributes(DuckEntity::createDuckAttributes)
            .spawnRestriction(
                CustomSpawnLocations.AQUATIC,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                FlyingBirdEntity::canSpawnWaterfowl
            )
            .dimensions(0.6f, 0.8f)
            .eyeHeight(0.7f)
    );

    public static final Supplier<EntityType<GullEntity>> GULL = register("gull",
        EntityTypeBuilder.create(
                GullEntity::new,
                CustomSpawnGroup.BIRDS.spawnGroup
            )
            .defaultAttributes(GullEntity::createGullAttributes)
            .spawnRestriction(
                CustomSpawnLocations.SEMIAQUATIC,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                FlyingBirdEntity::canSpawnShorebirds
            )
            .dimensions(0.6f, 0.8f)
            .eyeHeight(0.7f)
    );

    public static final Supplier<EntityType<HawkEntity>> HAWK = register("hawk",
        EntityTypeBuilder.create(
                HawkEntity::new,
                CustomSpawnGroup.BIRDS.spawnGroup
            )
            .defaultAttributes(HawkEntity::createHawkAttributes)
            .spawnRestriction(
                CustomSpawnLocations.GROUND,
                Heightmap.Type.MOTION_BLOCKING,
                FlyingBirdEntity::canSpawnPasserines
            )
            .dimensions(0.6f, 0.8f)
            .eyeHeight(0.7f)
    );

    public static final Supplier<EntityType<PenguinEntity>> PENGUIN = register("penguin",
        EntityTypeBuilder.create(
                PenguinEntity::new,
                SpawnGroup.CREATURE
            )
            .defaultAttributes(PenguinEntity::createPenguinAttributes)
            .spawnRestriction(
                CustomSpawnLocations.SEMIAQUATIC,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                PenguinEntity::canSpawnPenguins
            )
            .dimensions(0.5f, 1.4f)
            .eyeHeight(1.35f)
            .passengerAttachments(new Vec3d(0, 0.75, -0.1))
    );

    public static final Supplier<EntityType<PigeonEntity>> PIGEON = register("pigeon",
        EntityTypeBuilder.create(
                PigeonEntity::new,
                CustomSpawnGroup.BIRDS.spawnGroup
            )
            .defaultAttributes(PigeonEntity::createPigeonAttributes)
            .spawnRestriction(
                CustomSpawnLocations.GROUND,
                Heightmap.Type.MOTION_BLOCKING,
                FlyingBirdEntity::canSpawnShorebirds
            )
            .dimensions(0.5f, 0.6f)
            .eyeHeight(0.5f)
    );

    public static final Supplier<EntityType<RavenEntity>> RAVEN = register("raven",
        EntityTypeBuilder.create(
                RavenEntity::new,
                CustomSpawnGroup.AMBIENT_BIRDS.spawnGroup
            )
            .defaultAttributes(RavenEntity::createRavenAttributes)
            .spawnRestriction(
                CustomSpawnLocations.GROUND,
                Heightmap.Type.MOTION_BLOCKING,
                FlyingBirdEntity::canSpawnPasserines
            )
            .dimensions(0.6f, 0.8f)
            .eyeHeight(0.7f)
    );

    public static final Supplier<EntityType<RobinEntity>> ROBIN = register("robin",
        EntityTypeBuilder.create(
                RobinEntity::new,
                CustomSpawnGroup.AMBIENT_BIRDS.spawnGroup
            )
            .defaultAttributes(RobinEntity::createFlyingBirdAttributes)
            .spawnRestriction(
                CustomSpawnLocations.GROUND,
                Heightmap.Type.MOTION_BLOCKING,
                FlyingBirdEntity::canSpawnPasserines
            )
            .dimensions(0.4f, 0.55f)
            .eyeHeight(0.475f)
    );

    public static final Supplier<EntityType<SparrowEntity>> SPARROW = register("sparrow",
        EntityTypeBuilder.create(
                SparrowEntity::new,
                CustomSpawnGroup.AMBIENT_BIRDS.spawnGroup
            )
            .defaultAttributes(SparrowEntity::createFlyingBirdAttributes)
            .spawnRestriction(
                CustomSpawnLocations.GROUND,
                Heightmap.Type.MOTION_BLOCKING,
                FlyingBirdEntity::canSpawnPasserines
            )
            .dimensions(0.3f, 0.45f)
            .eyeHeight(0.4f)
    );

    private static <T extends Entity> Supplier<EntityType<T>> register(String id, EntityTypeBuilder<T> builder) {
        return PlatformHelper.registerEntityType(id, () -> builder.build(id));
    }

    public static void init() {
        // Spawn Weights
        addSpawn(
            FowlPlayBiomeTags.SPAWNS_BLUE_JAYS,
            CustomSpawnGroup.AMBIENT_BIRDS.spawnGroup,
            FowlPlayEntityType.BLUE_JAY,
            FowlPlayConfig.getInstance().blueJaySpawnWeight,
            FowlPlayConfig.getInstance().blueJayMinGroupSize,
            FowlPlayConfig.getInstance().blueJayMaxGroupSize
        );
        addSpawn(
            FowlPlayBiomeTags.SPAWNS_CARDINALS,
            CustomSpawnGroup.AMBIENT_BIRDS.spawnGroup,
            FowlPlayEntityType.CARDINAL,
            FowlPlayConfig.getInstance().cardinalSpawnWeight,
            FowlPlayConfig.getInstance().cardinalMinGroupSize,
            FowlPlayConfig.getInstance().cardinalMaxGroupSize
        );
        addSpawn(
            FowlPlayBiomeTags.SPAWNS_CHICKADEES,
            CustomSpawnGroup.AMBIENT_BIRDS.spawnGroup,
            FowlPlayEntityType.CHICKADEE,
            FowlPlayConfig.getInstance().chickadeeSpawnWeight,
            FowlPlayConfig.getInstance().chickadeeMinGroupSize,
            FowlPlayConfig.getInstance().chickadeeMaxGroupSize
        );
        addSpawn(
            FowlPlayBiomeTags.SPAWNS_CROWS,
            CustomSpawnGroup.AMBIENT_BIRDS.spawnGroup,
            FowlPlayEntityType.CROW,
            FowlPlayConfig.getInstance().crowSpawnWeight,
            FowlPlayConfig.getInstance().crowMinGroupSize,
            FowlPlayConfig.getInstance().crowMaxGroupSize
        );
        addSpawn(
            FowlPlayBiomeTags.SPAWNS_DUCKS,
            CustomSpawnGroup.BIRDS.spawnGroup,
            FowlPlayEntityType.DUCK,
            FowlPlayConfig.getInstance().duckSpawnWeight,
            FowlPlayConfig.getInstance().duckMinGroupSize,
            FowlPlayConfig.getInstance().duckMaxGroupSize
        );
        addSpawn(
            FowlPlayBiomeTags.SPAWNS_GULLS,
            CustomSpawnGroup.BIRDS.spawnGroup,
            FowlPlayEntityType.GULL,
            FowlPlayConfig.getInstance().gullSpawnWeight,
            FowlPlayConfig.getInstance().gullMinGroupSize,
            FowlPlayConfig.getInstance().gullMaxGroupSize
        );
//        addSpawn();(
//            FowlPlayBiomeTags.SPAWNS_HAWKS,
//            FowlPlaySpawnGroup.BIRD.spawnGroup,
//            FowlPlayEntityType.HAWK,
//            FowlPlayConfig.getInstance().hawkSpawnWeight,
//            FowlPlayConfig.getInstance().hawkMinGroupSize,
//            FowlPlayConfig.getInstance().hawkMaxGroupSize
//        );
        addSpawn(
            FowlPlayBiomeTags.SPAWNS_PENGUINS,
            SpawnGroup.CREATURE,
            FowlPlayEntityType.PENGUIN,
            FowlPlayConfig.getInstance().penguinSpawnWeight,
            FowlPlayConfig.getInstance().penguinMinGroupSize,
            FowlPlayConfig.getInstance().penguinMaxGroupSize
        );
        addSpawn(
            FowlPlayBiomeTags.SPAWNS_PIGEONS,
            CustomSpawnGroup.BIRDS.spawnGroup,
            FowlPlayEntityType.PIGEON,
            FowlPlayConfig.getInstance().pigeonSpawnWeight,
            FowlPlayConfig.getInstance().pigeonMinGroupSize,
            FowlPlayConfig.getInstance().pigeonMaxGroupSize
        );
        addSpawn(
            FowlPlayBiomeTags.SPAWNS_RAVENS,
            CustomSpawnGroup.AMBIENT_BIRDS.spawnGroup,
            FowlPlayEntityType.RAVEN,
            FowlPlayConfig.getInstance().ravenSpawnWeight,
            FowlPlayConfig.getInstance().ravenMinGroupSize,
            FowlPlayConfig.getInstance().ravenMaxGroupSize
        );
        addSpawn(
            FowlPlayBiomeTags.SPAWNS_ROBINS,
            CustomSpawnGroup.AMBIENT_BIRDS.spawnGroup,
            FowlPlayEntityType.ROBIN,
            FowlPlayConfig.getInstance().robinSpawnWeight,
            FowlPlayConfig.getInstance().robinMinGroupSize,
            FowlPlayConfig.getInstance().robinMaxGroupSize
        );
        addSpawn(
            FowlPlayBiomeTags.SPAWNS_SPARROWS,
            CustomSpawnGroup.AMBIENT_BIRDS.spawnGroup,
            FowlPlayEntityType.SPARROW,
            FowlPlayConfig.getInstance().sparrowSpawnWeight,
            FowlPlayConfig.getInstance().sparrowMinGroupSize,
            FowlPlayConfig.getInstance().sparrowMaxGroupSize
        );

        // Spawn Costs
//        setSpawnCost(
//            FowlPlayBiomeTags.SPAWNS_DUCKS,
//            FowlPlayEntityType.DUCK,
//            1,
//            0.07
//        );
        setSpawnCost(
            FowlPlayBiomeTags.SPAWNS_GULLS,
            FowlPlayEntityType.GULL,
            1,
            0.07
        );
    }

    // TODO: use biome property based spawning to more accurately reflect real life habitats
    public static <T extends Entity> void addSpawn(TagKey<Biome> tag, SpawnGroup spawnGroup, Supplier<EntityType<T>> type, int weight, int minGroupSize, int maxGroupSize) {
        BiomeModifications.addProperties(
            context -> context.hasTag(tag),
            (context, mutable) -> mutable.getSpawnProperties().addSpawn(
                spawnGroup,
                new SpawnSettings.SpawnEntry(
                    type.get(),
                    weight,
                    minGroupSize,
                    maxGroupSize
                )
            )
        );
    }

    public static <T extends Entity> void setSpawnCost(TagKey<Biome> tag, Supplier<EntityType<T>> type, double gravityLimit, double mass) {
        BiomeModifications.addProperties(
            context -> context.hasTag(tag),
            (context, mutable) -> mutable.getSpawnProperties().setSpawnCost(
                type.get(),
                new SpawnSettings.SpawnDensity(
                    gravityLimit,
                    mass
                )
            )
        );
    }
}
