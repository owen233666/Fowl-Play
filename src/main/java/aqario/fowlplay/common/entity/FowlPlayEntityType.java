package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.FowlPlay;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder;

public class FowlPlayEntityType {
    public static final EntityType<PenguinEntity> PENGUIN = register("penguin",
        QuiltEntityTypeBuilder.createMob()
            .entityFactory(PenguinEntity::new)
            .defaultAttributes(PenguinEntity.createPenguinAttributes())
            .setDimensions(EntityDimensions.changing(0.5f, 1.4f))
            .spawnGroup(SpawnGroup.CREATURE)
    );

    public static final EntityType<PigeonEntity> PIGEON = register("pigeon",
        QuiltEntityTypeBuilder.createMob()
            .entityFactory(PigeonEntity::new)
            .defaultAttributes(PigeonEntity.createPigeonAttributes())
            .setDimensions(EntityDimensions.changing(0.5f, 0.6f))
            .spawnGroup(SpawnGroup.CREATURE)
    );

    public static final EntityType<RobinEntity> ROBIN = register("robin",
        QuiltEntityTypeBuilder.createMob()
            .entityFactory(RobinEntity::new)
            .defaultAttributes(RobinEntity.createAttributes())
            .setDimensions(EntityDimensions.changing(0.4f, 0.5f))
            .spawnGroup(SpawnGroup.CREATURE)
    );

    public static final EntityType<SeagullEntity> SEAGULL = register("seagull",
        QuiltEntityTypeBuilder.createMob()
            .entityFactory(SeagullEntity::new)
            .defaultAttributes(SeagullEntity.createAttributes())
            .setDimensions(EntityDimensions.fixed(0.6f, 0.8f))
            .spawnGroup(SpawnGroup.AMBIENT)
    );

    private static <T extends Entity> EntityType<T> register(String id, QuiltEntityTypeBuilder<T> builder) {
        return Registry.register(Registries.ENTITY_TYPE, new Identifier(FowlPlay.ID, id), builder.build());
    }

    public static void init() {
    }
}
