package aqario.fowlplay.core.platform.fabric;

import aqario.fowlplay.common.entity.*;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistries;
import aqario.fowlplay.core.FowlPlayRegistryKeys;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class PlatformHelperImpl {
    @SuppressWarnings("unchecked")
    public static <T> void registerVariant(String id, RegistryKey<T> key, Supplier<T> variant) {
        if(key.isOf(FowlPlayRegistryKeys.CHICKEN_VARIANT)) {
            Registry.register(FowlPlayRegistries.CHICKEN_VARIANT, (RegistryKey<ChickenVariant>) key, (ChickenVariant) variant.get());
        }
        else if(key.isOf(FowlPlayRegistryKeys.DUCK_VARIANT)) {
            Registry.register(FowlPlayRegistries.DUCK_VARIANT, (RegistryKey<DuckVariant>) key, (DuckVariant) variant.get());
        }
        else if(key.isOf(FowlPlayRegistryKeys.GULL_VARIANT)) {
            Registry.register(FowlPlayRegistries.GULL_VARIANT, (RegistryKey<GullVariant>) key, (GullVariant) variant.get());
        }
        else if(key.isOf(FowlPlayRegistryKeys.PIGEON_VARIANT)) {
            Registry.register(FowlPlayRegistries.PIGEON_VARIANT, (RegistryKey<PigeonVariant>) key, (PigeonVariant) variant.get());
        }
        else if(key.isOf(FowlPlayRegistryKeys.SPARROW_VARIANT)) {
            Registry.register(FowlPlayRegistries.SPARROW_VARIANT, (RegistryKey<SparrowVariant>) key, (SparrowVariant) variant.get());
        }
    }

    public static Supplier<Activity> registerActivity(String id, Supplier<Activity> activity) {
        Activity registry = Registry.register(Registries.ACTIVITY, Identifier.of(FowlPlay.ID, id), activity.get());
        return () -> registry;
    }

    public static <T extends Entity> Supplier<EntityType<T>> registerEntityType(String id, Supplier<EntityType<T>> entityType) {
        EntityType<T> registry = Registry.register(Registries.ENTITY_TYPE, Identifier.of(FowlPlay.ID, id), entityType.get());
        return () -> registry;
    }

    public static Supplier<Item> registerItem(String id, Supplier<Item> item, RegistryKey<ItemGroup> group) {
        addItemToItemGroup(item.get(), group);
        Item registry = Registry.register(Registries.ITEM, Identifier.of(FowlPlay.ID, id), item.get());
        return () -> registry;
    }

    public static <T extends MobEntity> Supplier<Item> registerSpawnEggItem(String id, Supplier<EntityType<T>> entityType, int backgroundColor, int highlightColor) {
        return registerItem(id, () -> new SpawnEggItem(entityType.get(), backgroundColor, highlightColor, new Item.Settings()), ItemGroups.SPAWN_EGGS);
    }

    public static <T> Supplier<MemoryModuleType<T>> registerMemoryModuleType(String id, Supplier<MemoryModuleType<T>> memoryModuleType) {
        MemoryModuleType<T> registry = Registry.register(Registries.MEMORY_MODULE_TYPE, Identifier.of(FowlPlay.ID, id), memoryModuleType.get());
        return () -> registry;
    }

    public static Supplier<SimpleParticleType> registerParticleType(String id, Supplier<SimpleParticleType> particleType) {
        SimpleParticleType registry = Registry.register(Registries.PARTICLE_TYPE, Identifier.of(FowlPlay.ID, id), particleType.get());
        return () -> registry;
    }

    public static <T extends Sensor<?>> Supplier<SensorType<T>> registerSensorType(String id, Supplier<SensorType<T>> sensorType) {
        SensorType<T> registry = Registry.register(Registries.SENSOR_TYPE, Identifier.of(FowlPlay.ID, id), sensorType.get());
        return () -> registry;
    }

    public static Supplier<SoundEvent> registerSoundEvent(String id, Supplier<SoundEvent> soundEvent) {
        SoundEvent registry = Registry.register(Registries.SOUND_EVENT, Identifier.of(FowlPlay.ID, id), soundEvent.get());
        return () -> registry;
    }

    public static <T> Registry<T> registerRegistry(RegistryKey<Registry<T>> registryKey, boolean sync) {
        FabricRegistryBuilder<T, SimpleRegistry<T>> builder = FabricRegistryBuilder.createSimple(registryKey);
        if(sync) {
            builder.attribute(RegistryAttribute.SYNCED);
        }
        return builder.buildAndRegister();
    }

    public static <T> void registerTrackedDataHandler(String id, TrackedDataHandler<T> handler) {
        TrackedDataHandlerRegistry.register(handler);
    }

    public static void addItemToItemGroup(Item item, RegistryKey<ItemGroup> itemGroup) {
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries ->
            entries.add(item)
        );
    }
}
