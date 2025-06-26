package aqario.fowlplay.datagen;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.CustomSpawnGroup;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayEntityType;
import aqario.fowlplay.core.tags.FowlPlayBiomeTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

@EventBusSubscriber(modid = FowlPlay.ID, bus = EventBusSubscriber.Bus.MOD)
public class FowlPlayNeoForgeDataGen {
    public static final RegistryBuilder BUILDER = new RegistryBuilder();

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        BUILDER.addRegistry(NeoForgeRegistries.Keys.BIOME_MODIFIERS, bootstrap -> {
            RegistryEntryLookup<Biome> biomes = bootstrap.getRegistryLookup(RegistryKeys.BIOME);
            RegistryEntryLookup<EntityType<?>> entities = bootstrap.getRegistryLookup(RegistryKeys.ENTITY_TYPE);

            // Spawn Weights
            addSpawn(
                bootstrap,
                biomes,
                FowlPlayBiomeTags.SPAWNS_BLUE_JAYS,
                CustomSpawnGroup.AMBIENT_BIRDS.spawnGroup,
                FowlPlayEntityType.BLUE_JAY,
                FowlPlayConfig.getInstance().blueJaySpawnWeight,
                FowlPlayConfig.getInstance().blueJayMinGroupSize,
                FowlPlayConfig.getInstance().blueJayMaxGroupSize
            );
            addSpawn(
                bootstrap,
                biomes,
                FowlPlayBiomeTags.SPAWNS_CARDINALS,
                CustomSpawnGroup.AMBIENT_BIRDS.spawnGroup,
                FowlPlayEntityType.CARDINAL,
                FowlPlayConfig.getInstance().cardinalSpawnWeight,
                FowlPlayConfig.getInstance().cardinalMinGroupSize,
                FowlPlayConfig.getInstance().cardinalMaxGroupSize
            );
            addSpawn(
                bootstrap,
                biomes,
                FowlPlayBiomeTags.SPAWNS_CHICKADEES,
                CustomSpawnGroup.AMBIENT_BIRDS.spawnGroup,
                FowlPlayEntityType.CHICKADEE,
                FowlPlayConfig.getInstance().chickadeeSpawnWeight,
                FowlPlayConfig.getInstance().chickadeeMinGroupSize,
                FowlPlayConfig.getInstance().chickadeeMaxGroupSize
            );
            addSpawn(
                bootstrap,
                biomes,
                FowlPlayBiomeTags.SPAWNS_CROWS,
                CustomSpawnGroup.AMBIENT_BIRDS.spawnGroup,
                FowlPlayEntityType.CROW,
                FowlPlayConfig.getInstance().crowSpawnWeight,
                FowlPlayConfig.getInstance().crowMinGroupSize,
                FowlPlayConfig.getInstance().crowMaxGroupSize
            );
            addSpawn(
                bootstrap,
                biomes,
                FowlPlayBiomeTags.SPAWNS_DUCKS,
                CustomSpawnGroup.BIRDS.spawnGroup,
                FowlPlayEntityType.DUCK,
                FowlPlayConfig.getInstance().duckSpawnWeight,
                FowlPlayConfig.getInstance().duckMinGroupSize,
                FowlPlayConfig.getInstance().duckMaxGroupSize
            );
            addSpawn(
                bootstrap,
                biomes,
                FowlPlayBiomeTags.SPAWNS_GULLS,
                CustomSpawnGroup.BIRDS.spawnGroup,
                FowlPlayEntityType.GULL,
                FowlPlayConfig.getInstance().gullSpawnWeight,
                FowlPlayConfig.getInstance().gullMinGroupSize,
                FowlPlayConfig.getInstance().gullMaxGroupSize
            );
//            addSpawn(
//                bootstrap,
//                biomes,
//                FowlPlayBiomeTags.SPAWNS_HAWKS,
//                CustomSpawnGroup.BIRDS.spawnGroup,
//                FowlPlayEntityType.HAWK,
//                FowlPlayConfig.getInstance().hawkSpawnWeight,
//                FowlPlayConfig.getInstance().hawkMinGroupSize,
//                FowlPlayConfig.getInstance().hawkMaxGroupSize
//            );
            addSpawn(
                bootstrap,
                biomes,
                FowlPlayBiomeTags.SPAWNS_PENGUINS,
                SpawnGroup.CREATURE,
                FowlPlayEntityType.PENGUIN,
                FowlPlayConfig.getInstance().penguinSpawnWeight,
                FowlPlayConfig.getInstance().penguinMinGroupSize,
                FowlPlayConfig.getInstance().penguinMaxGroupSize
            );
            addSpawn(
                bootstrap,
                biomes,
                FowlPlayBiomeTags.SPAWNS_PIGEONS,
                CustomSpawnGroup.BIRDS.spawnGroup,
                FowlPlayEntityType.PIGEON,
                FowlPlayConfig.getInstance().pigeonSpawnWeight,
                FowlPlayConfig.getInstance().pigeonMinGroupSize,
                FowlPlayConfig.getInstance().pigeonMaxGroupSize
            );
            addSpawn(
                bootstrap,
                biomes,
                FowlPlayBiomeTags.SPAWNS_RAVENS,
                CustomSpawnGroup.AMBIENT_BIRDS.spawnGroup,
                FowlPlayEntityType.RAVEN,
                FowlPlayConfig.getInstance().ravenSpawnWeight,
                FowlPlayConfig.getInstance().ravenMinGroupSize,
                FowlPlayConfig.getInstance().ravenMaxGroupSize
            );
            addSpawn(
                bootstrap,
                biomes,
                FowlPlayBiomeTags.SPAWNS_ROBINS,
                CustomSpawnGroup.AMBIENT_BIRDS.spawnGroup,
                FowlPlayEntityType.ROBIN,
                FowlPlayConfig.getInstance().robinSpawnWeight,
                FowlPlayConfig.getInstance().robinMinGroupSize,
                FowlPlayConfig.getInstance().robinMaxGroupSize
            );
            addSpawn(
                bootstrap,
                biomes,
                FowlPlayBiomeTags.SPAWNS_SPARROWS,
                CustomSpawnGroup.AMBIENT_BIRDS.spawnGroup,
                FowlPlayEntityType.SPARROW,
                FowlPlayConfig.getInstance().sparrowSpawnWeight,
                FowlPlayConfig.getInstance().sparrowMinGroupSize,
                FowlPlayConfig.getInstance().sparrowMaxGroupSize
            );

            // Spawn Costs
//            setSpawnCost(
//                bootstrap,
//                biomes,
//                entities,
//                FowlPlayBiomeTags.SPAWNS_DUCKS,
//                FowlPlayEntityType.DUCK,
//                1,
//                0.07
//            );
            setSpawnCost(
                bootstrap,
                biomes,
                entities,
                FowlPlayBiomeTags.SPAWNS_GULLS,
                FowlPlayEntityType.GULL,
                1,
                0.07
            );
        });
    }

    public static <T extends Entity> void addSpawn(Registerable<BiomeModifier> bootstrap, RegistryEntryLookup<Biome> biomes, TagKey<Biome> tag, SpawnGroup spawnGroup, Supplier<EntityType<T>> type, int weight, int minGroupSize, int maxGroupSize) {
        bootstrap.register(
            RegistryKey.of(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Registries.ENTITY_TYPE.getId(type.get())),
            BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
                biomes.getOrThrow(tag),
                new SpawnSettings.SpawnEntry(
                    type.get(),
                    weight,
                    minGroupSize,
                    maxGroupSize
                )
            )
        );
    }

    public static <T extends Entity> void setSpawnCost(Registerable<BiomeModifier> bootstrap, RegistryEntryLookup<Biome> biomes, RegistryEntryLookup<EntityType<?>> entities, TagKey<Biome> tag, Supplier<EntityType<T>> type, double gravityLimit, double mass) {
        bootstrap.register(
            RegistryKey.of(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Registries.ENTITY_TYPE.getId(type.get())),
            new BiomeModifiers.AddSpawnCostsBiomeModifier(
                biomes.getOrThrow(tag),
                RegistryEntryList.of(entities.getOrThrow(Registries.ENTITY_TYPE.getKey(type.get()).get())),
                new SpawnSettings.SpawnDensity(
                    gravityLimit,
                    mass
                )
            )
        );
    }
}
