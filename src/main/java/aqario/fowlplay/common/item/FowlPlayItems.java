package aqario.fowlplay.common.item;

import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.FowlPlayEntityType;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

@SuppressWarnings("unused")
public class FowlPlayItems {
    public static final Item CARDINAL_SPAWN_EGG = register(
        "cardinal_spawn_egg",
        new SpawnEggItem(FowlPlayEntityType.CARDINAL, 0xDB2929, 0x42312F, new Item.Settings()),
        ItemGroups.SPAWN_EGGS
    );
    public static final Item PENGUIN_SPAWN_EGG = register(
        "penguin_spawn_egg",
        new SpawnEggItem(FowlPlayEntityType.PENGUIN, 0x151419, 0xfafafa, new Item.Settings()),
        ItemGroups.SPAWN_EGGS
    );
    public static final Item PIGEON_SPAWN_EGG = register(
        "pigeon_spawn_egg",
        new SpawnEggItem(FowlPlayEntityType.PIGEON, 0xBBBDBF, 0x467A58, new Item.Settings()),
        ItemGroups.SPAWN_EGGS
    );
    public static final Item ROBIN_SPAWN_EGG = register(
        "robin_spawn_egg",
        new SpawnEggItem(FowlPlayEntityType.ROBIN, 0x66696A, 0xFF823F, new Item.Settings()),
        ItemGroups.SPAWN_EGGS
    );
    public static final Item SEAGULL_SPAWN_EGG = register(
        "seagull_spawn_egg",
        new SpawnEggItem(FowlPlayEntityType.SEAGULL, 0xeaedf0, 0xffd850, new Item.Settings()),
        ItemGroups.SPAWN_EGGS
    );

    private static Item register(String id, Item item, RegistryKey<ItemGroup> group) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.addItem(item));
        return Registry.register(Registries.ITEM, Identifier.of(FowlPlay.ID, id), item);
    }

    public static void init() {
    }
}
