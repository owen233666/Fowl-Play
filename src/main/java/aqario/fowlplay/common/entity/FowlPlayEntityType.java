package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.FowlPlay;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public final class FowlPlayEntityType {
    public static final EntityType<BlueJayEntity> BLUE_JAY = register("blue_jay",
        EntityType.Builder.create(BlueJayEntity::new, FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup)
            .dimensions(0.4f, 0.55f)
            .eyeHeight(0.475f)
    );

    public static final EntityType<CardinalEntity> CARDINAL = register("cardinal",
        EntityType.Builder.create(CardinalEntity::new, FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup)
            .dimensions(0.4f, 0.55f)
            .eyeHeight(0.475f)
    );

    public static final EntityType<ChickadeeEntity> CHICKADEE = register("chickadee",
        EntityType.Builder.create(ChickadeeEntity::new, FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup)
            .dimensions(0.3f, 0.45f)
            .eyeHeight(0.4f)
    );

    public static final EntityType<DuckEntity> DUCK = register("duck",
        EntityType.Builder.create(DuckEntity::new, FowlPlaySpawnGroup.BIRD.spawnGroup)
            .dimensions(0.6f, 0.8f)
            .eyeHeight(0.7f)
    );

    public static final EntityType<GullEntity> GULL = register("gull",
        EntityType.Builder.create(GullEntity::new, FowlPlaySpawnGroup.BIRD.spawnGroup)
            .dimensions(0.6f, 0.8f)
            .eyeHeight(0.7f)
    );

    public static final EntityType<HawkEntity> HAWK = register("hawk",
        EntityType.Builder.create(HawkEntity::new, FowlPlaySpawnGroup.BIRD.spawnGroup)
            .dimensions(0.6f, 0.8f)
            .eyeHeight(0.7f)
    );

    public static final EntityType<PenguinEntity> PENGUIN = register("penguin",
        EntityType.Builder.create(PenguinEntity::new, SpawnGroup.CREATURE)
            .dimensions(0.5f, 1.4f)
            .eyeHeight(1.35f)
            .passengerAttachments(new Vec3d(0, 0.75, -0.1))
    );

    public static final EntityType<PigeonEntity> PIGEON = register("pigeon",
        EntityType.Builder.create(PigeonEntity::new, FowlPlaySpawnGroup.BIRD.spawnGroup)
            .dimensions(0.5f, 0.6f)
            .eyeHeight(0.5f)
    );

    public static final EntityType<RavenEntity> RAVEN = register("raven",
        EntityType.Builder.create(RavenEntity::new, FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup)
            .dimensions(0.6f, 0.8f)
            .eyeHeight(0.7f)
    );

    public static final EntityType<RobinEntity> ROBIN = register("robin",
        EntityType.Builder.create(RobinEntity::new, FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup)
            .dimensions(0.4f, 0.55f)
            .eyeHeight(0.475f)
    );

    public static final EntityType<SparrowEntity> SPARROW = register("sparrow",
        EntityType.Builder.create(SparrowEntity::new, FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup)
            .dimensions(0.4f, 0.55f)
            .eyeHeight(0.475f)
    );

    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
        return Registry.register(Registries.ENTITY_TYPE, Identifier.of(FowlPlay.ID, id), builder.build());
    }

    public static void init() {
        FabricDefaultAttributeRegistry.register(BLUE_JAY, BlueJayEntity.createFlyingBirdAttributes());
        FabricDefaultAttributeRegistry.register(CARDINAL, CardinalEntity.createFlyingBirdAttributes());
        FabricDefaultAttributeRegistry.register(CHICKADEE, ChickadeeEntity.createFlyingBirdAttributes());
        FabricDefaultAttributeRegistry.register(DUCK, DuckEntity.createDuckAttributes());
        FabricDefaultAttributeRegistry.register(GULL, GullEntity.createGullAttributes());
        FabricDefaultAttributeRegistry.register(HAWK, HawkEntity.createHawkAttributes());
        FabricDefaultAttributeRegistry.register(PENGUIN, PenguinEntity.createPenguinAttributes());
        FabricDefaultAttributeRegistry.register(PIGEON, PigeonEntity.createPigeonAttributes());
        FabricDefaultAttributeRegistry.register(RAVEN, RavenEntity.createRavenAttributes());
        FabricDefaultAttributeRegistry.register(ROBIN, RobinEntity.createFlyingBirdAttributes());
        FabricDefaultAttributeRegistry.register(SPARROW, SparrowEntity.createFlyingBirdAttributes());
    }
}
