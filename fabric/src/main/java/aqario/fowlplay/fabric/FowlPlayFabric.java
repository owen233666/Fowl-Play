package aqario.fowlplay.fabric;

import aqario.fowlplay.common.entity.*;
import aqario.fowlplay.common.world.gen.PigeonSpawner;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayEntityType;
import aqario.fowlplay.core.FowlPlayItems;
import aqario.fowlplay.fabric.world.gen.FowlPlayWorldGen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.item.ItemGroups;

public final class FowlPlayFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        FowlPlay.init();

        FowlPlayWorldGen.init();

        FabricDefaultAttributeRegistry.register(FowlPlayEntityType.BLUE_JAY, BlueJayEntity.createFlyingBirdAttributes());
        FabricDefaultAttributeRegistry.register(FowlPlayEntityType.CARDINAL, CardinalEntity.createFlyingBirdAttributes());
        FabricDefaultAttributeRegistry.register(FowlPlayEntityType.GULL, GullEntity.createFlyingBirdAttributes());
        FabricDefaultAttributeRegistry.register(FowlPlayEntityType.PENGUIN, PenguinEntity.createPenguinAttributes());
        FabricDefaultAttributeRegistry.register(FowlPlayEntityType.PIGEON, PigeonEntity.createFlyingBirdAttributes());
        FabricDefaultAttributeRegistry.register(FowlPlayEntityType.ROBIN, RobinEntity.createFlyingBirdAttributes());
        FabricDefaultAttributeRegistry.register(FowlPlayEntityType.SPARROW, SparrowEntity.createFlyingBirdAttributes());

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> {
            entries.addItem(FowlPlayItems.BLUE_JAY_SPAWN_EGG);
            entries.addItem(FowlPlayItems.CARDINAL_SPAWN_EGG);
            entries.addItem(FowlPlayItems.GULL_SPAWN_EGG);
            entries.addItem(FowlPlayItems.PENGUIN_SPAWN_EGG);
            entries.addItem(FowlPlayItems.PIGEON_SPAWN_EGG);
            entries.addItem(FowlPlayItems.ROBIN_SPAWN_EGG);
            entries.addItem(FowlPlayItems.SPARROW_SPAWN_EGG);
        });

        PigeonSpawner spawner = new PigeonSpawner();
        ServerTickEvents.END_WORLD_TICK.register(world -> spawner.spawn(
            world,
            world.getServer().isMonsterSpawningEnabled(),
            world.getServer().shouldSpawnAnimals()
        ));
    }
}
