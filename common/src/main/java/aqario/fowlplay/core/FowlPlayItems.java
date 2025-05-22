package aqario.fowlplay.core;

import aqario.fowlplay.core.platform.PlatformHelper;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public final class FowlPlayItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
        FowlPlay.ID,
        RegistryKeys.ITEM
    );

    public static final RegistrySupplier<Item> BLUE_JAY_SPAWN_EGG = register(
        "blue_jay_spawn_egg",
        new SpawnEggItem(FowlPlayEntityType.BLUE_JAY.get(), 0x598FCC, 0xCED8E5, new Item.Settings()),
        ItemGroups.SPAWN_EGGS
    );
    public static final RegistrySupplier<Item> CARDINAL_SPAWN_EGG = register(
        "cardinal_spawn_egg",
        new SpawnEggItem(FowlPlayEntityType.CARDINAL.get(), 0xDB2929, 0x42312F, new Item.Settings()),
        ItemGroups.SPAWN_EGGS
    );
    public static final RegistrySupplier<Item> CHICKADEE_SPAWN_EGG = register(
        "chickadee_spawn_egg",
        new SpawnEggItem(FowlPlayEntityType.CHICKADEE.get(), 0xE8E5E1, 0x8A8B8E, new Item.Settings()),
        ItemGroups.SPAWN_EGGS
    );
    public static final RegistrySupplier<Item> CROW_SPAWN_EGG = register(
        "crow_spawn_egg",
        new SpawnEggItem(FowlPlayEntityType.CROW.get(), 0x3B3B3D, 0x1C1C1E, new Item.Settings()),
        ItemGroups.SPAWN_EGGS
    );
    public static final RegistrySupplier<Item> DUCK_SPAWN_EGG = register(
        "duck_spawn_egg",
        new SpawnEggItem(FowlPlayEntityType.DUCK.get(), 0xA58C7C, 0x1D7F3C, new Item.Settings()),
        ItemGroups.SPAWN_EGGS
    );
    public static final RegistrySupplier<Item> GULL_SPAWN_EGG = register(
        "gull_spawn_egg",
        new SpawnEggItem(FowlPlayEntityType.GULL.get(), 0xeaedf0, 0xffd850, new Item.Settings()),
        ItemGroups.SPAWN_EGGS
    );
    public static final RegistrySupplier<Item> HAWK_SPAWN_EGG = register(
        "hawk_spawn_egg",
        new SpawnEggItem(FowlPlayEntityType.HAWK.get(), 0x544135, 0xE5D8C0, new Item.Settings()),
        ItemGroups.SPAWN_EGGS
    );
    public static final RegistrySupplier<Item> PENGUIN_SPAWN_EGG = register(
        "penguin_spawn_egg",
        new SpawnEggItem(FowlPlayEntityType.PENGUIN.get(), 0x151419, 0xfafafa, new Item.Settings()),
        ItemGroups.SPAWN_EGGS
    );
    public static final RegistrySupplier<Item> PIGEON_SPAWN_EGG = register(
        "pigeon_spawn_egg",
        new SpawnEggItem(FowlPlayEntityType.PIGEON.get(), 0xBBBDBF, 0x467A58, new Item.Settings()),
        ItemGroups.SPAWN_EGGS
    );
    public static final RegistrySupplier<Item> RAVEN_SPAWN_EGG = register(
        "raven_spawn_egg",
        new SpawnEggItem(FowlPlayEntityType.RAVEN.get(), 0x3B3B3D, 0x1C1C1E, new Item.Settings()),
        ItemGroups.SPAWN_EGGS
    );
    public static final RegistrySupplier<Item> ROBIN_SPAWN_EGG = register(
        "robin_spawn_egg",
        new SpawnEggItem(FowlPlayEntityType.ROBIN.get(), 0x66696A, 0xFF823F, new Item.Settings()),
        ItemGroups.SPAWN_EGGS
    );
    public static final RegistrySupplier<Item> SPARROW_SPAWN_EGG = register(
        "sparrow_spawn_egg",
        new SpawnEggItem(FowlPlayEntityType.SPARROW.get(), 0x5B3423, 0xBCAE91, new Item.Settings()),
        ItemGroups.SPAWN_EGGS
    );

    private static RegistrySupplier<Item> register(String id, Item item, RegistryKey<ItemGroup> group) {
        PlatformHelper.addItemToItemGroup(item, group);
        return ITEMS.register(id, () -> item);
    }

    public static void init() {
        ITEMS.register();
    }
}
