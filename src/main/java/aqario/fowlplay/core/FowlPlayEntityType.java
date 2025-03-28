package aqario.fowlplay.core;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.*;
import aqario.fowlplay.common.world.gen.CustomSpawnLocations;
import aqario.fowlplay.core.tags.FowlPlayBiomeTags;
import com.google.common.base.Preconditions;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;

import java.util.function.Predicate;

public final class FowlPlayEntityType {
    public static final EntityType<BlueJayEntity> BLUE_JAY = register("blue_jay",
        FabricEntityType.Builder.createMob(
                BlueJayEntity::new,
                FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup,
                builder -> builder
                    .defaultAttributes(BlueJayEntity::createFlyingBirdAttributes)
                    .spawnRestriction(
                        CustomSpawnLocations.GROUND,
                        Heightmap.Type.MOTION_BLOCKING,
                        FlyingBirdEntity::canSpawnPasserines
                    )
            )
            .dimensions(0.4f, 0.55f)
            .eyeHeight(0.475f)
    );

    public static final EntityType<CardinalEntity> CARDINAL = register("cardinal",
        FabricEntityType.Builder.createMob(
                CardinalEntity::new,
                FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup,
                builder -> builder
                    .defaultAttributes(CardinalEntity::createFlyingBirdAttributes)
                    .spawnRestriction(
                        CustomSpawnLocations.GROUND,
                        Heightmap.Type.MOTION_BLOCKING,
                        FlyingBirdEntity::canSpawnPasserines
                    )
            )
            .dimensions(0.4f, 0.55f)
            .eyeHeight(0.475f)
    );

    public static final EntityType<ChickadeeEntity> CHICKADEE = register("chickadee",
        FabricEntityType.Builder.createMob(
                ChickadeeEntity::new,
                FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup,
                builder -> builder
                    .defaultAttributes(ChickadeeEntity::createFlyingBirdAttributes)
                    .spawnRestriction(
                        CustomSpawnLocations.GROUND,
                        Heightmap.Type.MOTION_BLOCKING,
                        FlyingBirdEntity::canSpawnPasserines
                    )
            )
            .dimensions(0.3f, 0.45f)
            .eyeHeight(0.4f)
    );

    public static final EntityType<DuckEntity> DUCK = register("duck",
        FabricEntityType.Builder.createMob(
                DuckEntity::new,
                FowlPlaySpawnGroup.BIRD.spawnGroup,
                builder -> builder
                    .defaultAttributes(DuckEntity::createDuckAttributes)
                    .spawnRestriction(
                        CustomSpawnLocations.SEMIAQUATIC,
                        Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                        FlyingBirdEntity::canSpawnWaterfowl
                    )
            )
            .dimensions(0.6f, 0.8f)
            .eyeHeight(0.7f)
    );

    public static final EntityType<GullEntity> GULL = register("gull",
        FabricEntityType.Builder.createMob(
                GullEntity::new,
                FowlPlaySpawnGroup.BIRD.spawnGroup,
                builder -> builder
                    .defaultAttributes(GullEntity::createGullAttributes)
                    .spawnRestriction(
                        CustomSpawnLocations.SEMIAQUATIC,
                        Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                        FlyingBirdEntity::canSpawnShorebirds
                    )
            )
            .dimensions(0.6f, 0.8f)
            .eyeHeight(0.7f)
    );

    public static final EntityType<HawkEntity> HAWK = register("hawk",
        FabricEntityType.Builder.createMob(
                HawkEntity::new,
                FowlPlaySpawnGroup.BIRD.spawnGroup,
                builder -> builder
                    .defaultAttributes(HawkEntity::createHawkAttributes)
                    .spawnRestriction(
                        CustomSpawnLocations.GROUND,
                        Heightmap.Type.MOTION_BLOCKING,
                        FlyingBirdEntity::canSpawnPasserines
                    )
            )
            .dimensions(0.6f, 0.8f)
            .eyeHeight(0.7f)
    );

    public static final EntityType<PenguinEntity> PENGUIN = register("penguin",
        FabricEntityType.Builder.createMob(
                PenguinEntity::new,
                SpawnGroup.CREATURE,
                builder -> builder
                    .defaultAttributes(PenguinEntity::createPenguinAttributes)
                    .spawnRestriction(
                        CustomSpawnLocations.SEMIAQUATIC,
                        Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                        PenguinEntity::canSpawnPenguins
                    )
            )
            .dimensions(0.5f, 1.4f)
            .eyeHeight(1.35f)
            .passengerAttachments(new Vec3d(0, 0.75, -0.1))
    );

    public static final EntityType<PigeonEntity> PIGEON = register("pigeon",
        FabricEntityType.Builder.createMob(
                PigeonEntity::new,
                FowlPlaySpawnGroup.BIRD.spawnGroup,
                builder -> builder
                    .defaultAttributes(PigeonEntity::createPigeonAttributes)
                    .spawnRestriction(
                        CustomSpawnLocations.GROUND,
                        Heightmap.Type.MOTION_BLOCKING,
                        FlyingBirdEntity::canSpawnShorebirds
                    )
            )
            .dimensions(0.5f, 0.6f)
            .eyeHeight(0.5f)
    );

    public static final EntityType<RavenEntity> RAVEN = register("raven",
        FabricEntityType.Builder.createMob(
                RavenEntity::new,
                FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup,
                builder -> builder
                    .defaultAttributes(RavenEntity::createRavenAttributes)
                    .spawnRestriction(
                        CustomSpawnLocations.GROUND,
                        Heightmap.Type.MOTION_BLOCKING,
                        FlyingBirdEntity::canSpawnPasserines
                    )
            )
            .dimensions(0.6f, 0.8f)
            .eyeHeight(0.7f)
    );

    public static final EntityType<RobinEntity> ROBIN = register("robin",
        FabricEntityType.Builder.createMob(
                RobinEntity::new,
                FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup,
                builder -> builder
                    .defaultAttributes(RobinEntity::createFlyingBirdAttributes)
                    .spawnRestriction(
                        CustomSpawnLocations.GROUND,
                        Heightmap.Type.MOTION_BLOCKING,
                        FlyingBirdEntity::canSpawnPasserines
                    )
            )
            .dimensions(0.4f, 0.55f)
            .eyeHeight(0.475f)
    );

    public static final EntityType<SparrowEntity> SPARROW = register("sparrow",
        FabricEntityType.Builder.createMob(
                SparrowEntity::new,
                FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup,
                builder -> builder
                    .defaultAttributes(SparrowEntity::createFlyingBirdAttributes)
                    .spawnRestriction(
                        CustomSpawnLocations.GROUND,
                        Heightmap.Type.MOTION_BLOCKING,
                        FlyingBirdEntity::canSpawnPasserines
                    )
            )
            .dimensions(0.4f, 0.55f)
            .eyeHeight(0.475f)
    );

    private static <T extends Entity> EntityType<T> register(String id, FabricEntityType.Builder<T> builder) {
        return Registry.register(Registries.ENTITY_TYPE, Identifier.of(FowlPlay.ID, id), builder.build());
    }

    public static void init() {
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_BLUE_JAYS),
            FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup,
            FowlPlayEntityType.BLUE_JAY,
            FowlPlayConfig.getInstance().blueJaySpawnWeight,
            FowlPlayConfig.getInstance().blueJayMinGroupSize,
            FowlPlayConfig.getInstance().blueJayMaxGroupSize
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_CARDINALS),
            FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup,
            FowlPlayEntityType.CARDINAL,
            FowlPlayConfig.getInstance().cardinalSpawnWeight,
            FowlPlayConfig.getInstance().cardinalMinGroupSize,
            FowlPlayConfig.getInstance().cardinalMaxGroupSize
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_CHICKADEES),
            FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup,
            FowlPlayEntityType.CHICKADEE,
            FowlPlayConfig.getInstance().chickadeeSpawnWeight,
            FowlPlayConfig.getInstance().chickadeeMinGroupSize,
            FowlPlayConfig.getInstance().chickadeeMaxGroupSize
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_DUCKS),
            FowlPlaySpawnGroup.BIRD.spawnGroup,
            FowlPlayEntityType.DUCK,
            FowlPlayConfig.getInstance().duckSpawnWeight,
            FowlPlayConfig.getInstance().duckMinGroupSize,
            FowlPlayConfig.getInstance().duckMaxGroupSize
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_GULLS),
            FowlPlaySpawnGroup.BIRD.spawnGroup,
            FowlPlayEntityType.GULL,
            FowlPlayConfig.getInstance().gullSpawnWeight,
            FowlPlayConfig.getInstance().gullMinGroupSize,
            FowlPlayConfig.getInstance().gullMaxGroupSize
        );
//        BiomeModifications.addSpawn(
//            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_HAWKS),
//            FowlPlaySpawnGroup.BIRD.spawnGroup,
//            FowlPlayEntityType.HAWK,
//            FowlPlayConfig.getInstance().hawkSpawnWeight,
//            FowlPlayConfig.getInstance().hawkMinGroupSize,
//            FowlPlayConfig.getInstance().hawkMaxGroupSize
//        );
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_PENGUINS),
            SpawnGroup.CREATURE,
            FowlPlayEntityType.PENGUIN,
            FowlPlayConfig.getInstance().penguinSpawnWeight,
            FowlPlayConfig.getInstance().penguinMinGroupSize,
            FowlPlayConfig.getInstance().penguinMaxGroupSize
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_PIGEONS),
            FowlPlaySpawnGroup.BIRD.spawnGroup,
            FowlPlayEntityType.PIGEON,
            FowlPlayConfig.getInstance().pigeonSpawnWeight,
            FowlPlayConfig.getInstance().pigeonMinGroupSize,
            FowlPlayConfig.getInstance().pigeonMaxGroupSize
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_RAVENS),
            FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup,
            FowlPlayEntityType.RAVEN,
            FowlPlayConfig.getInstance().ravenSpawnWeight,
            FowlPlayConfig.getInstance().ravenMinGroupSize,
            FowlPlayConfig.getInstance().ravenMaxGroupSize
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_ROBINS),
            FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup,
            FowlPlayEntityType.ROBIN,
            FowlPlayConfig.getInstance().robinSpawnWeight,
            FowlPlayConfig.getInstance().robinMinGroupSize,
            FowlPlayConfig.getInstance().robinMaxGroupSize
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_SPARROWS),
            FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup,
            FowlPlayEntityType.SPARROW,
            FowlPlayConfig.getInstance().sparrowSpawnWeight,
            FowlPlayConfig.getInstance().sparrowMinGroupSize,
            FowlPlayConfig.getInstance().sparrowMaxGroupSize
        );

        addSpawnCost(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_DUCKS),
            FowlPlayEntityType.DUCK,
            1,
            0.07
        );
        addSpawnCost(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_GULLS),
            FowlPlayEntityType.GULL,
            1,
            0.07
        );
    }

    public static void addSpawnCost(
        Predicate<BiomeSelectionContext> biomeSelector,
        EntityType<?> entityType,
        double mass,
        double gravityLimit
    ) {
        // See constructor of SpawnSettings.SpawnEntry for context
        Preconditions.checkArgument(entityType.getSpawnGroup() != SpawnGroup.MISC,
            "Cannot add spawns for entities with spawnGroup=MISC since they'd be replaced by pigs.");

        // We need the entity entity to be registered, or we cannot deduce an ID otherwise
        Identifier id = Registries.ENTITY_TYPE.getId(entityType);
        Preconditions.checkState(Registries.ENTITY_TYPE.getKey(entityType).isPresent(), "Unregistered entity entity: %s", entityType);

        // Add a new spawn cost to the chosen biome
        BiomeModifications.create(id).add(ModificationPhase.ADDITIONS, biomeSelector, context ->
            context.getSpawnSettings().setSpawnCost(entityType, mass, gravityLimit)
        );
    }
}
