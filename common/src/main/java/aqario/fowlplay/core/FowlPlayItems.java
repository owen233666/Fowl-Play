package aqario.fowlplay.core;

import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.item.Item;

import java.util.function.Supplier;

public final class FowlPlayItems {
    //    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
//        FowlPlay.ID,
//        RegistryKeys.ITEM
//    );
    public static final Supplier<Item> BLUE_JAY_SPAWN_EGG = PlatformHelper.registerSpawnEggItem(
        "blue_jay_spawn_egg",
        FowlPlayEntityType.BLUE_JAY,
        0x598FCC,
        0xCED8E5
    );
    public static final Supplier<Item> CARDINAL_SPAWN_EGG = PlatformHelper.registerSpawnEggItem(
        "cardinal_spawn_egg",
        FowlPlayEntityType.CARDINAL,
        0xDB2929,
        0x42312F
    );
    public static final Supplier<Item> CHICKADEE_SPAWN_EGG = PlatformHelper.registerSpawnEggItem(
        "chickadee_spawn_egg",
        FowlPlayEntityType.CHICKADEE,
        0xE8E5E1,
        0x8A8B8E
    );
    public static final Supplier<Item> CROW_SPAWN_EGG = PlatformHelper.registerSpawnEggItem(
        "crow_spawn_egg",
        FowlPlayEntityType.CROW,
        0x3B3B3D,
        0x1C1C1E
    );
    public static final Supplier<Item> DUCK_SPAWN_EGG = PlatformHelper.registerSpawnEggItem(
        "duck_spawn_egg",
        FowlPlayEntityType.DUCK,
        0xA58C7C,
        0x1D7F3C
    );
    public static final Supplier<Item> GULL_SPAWN_EGG = PlatformHelper.registerSpawnEggItem(
        "gull_spawn_egg",
        FowlPlayEntityType.GULL,
        0xeaedf0,
        0xffd850
    );
    public static final Supplier<Item> HAWK_SPAWN_EGG = PlatformHelper.registerSpawnEggItem(
        "hawk_spawn_egg",
        FowlPlayEntityType.HAWK,
        0x544135,
        0xE5D8C0
    );
    public static final Supplier<Item> PENGUIN_SPAWN_EGG = PlatformHelper.registerSpawnEggItem(
        "penguin_spawn_egg",
        FowlPlayEntityType.PENGUIN,
        0x151419,
        0xfafafa
    );
    public static final Supplier<Item> PIGEON_SPAWN_EGG = PlatformHelper.registerSpawnEggItem(
        "pigeon_spawn_egg",
        FowlPlayEntityType.PIGEON,
        0xBBBDBF,
        0x467A58
    );
    public static final Supplier<Item> RAVEN_SPAWN_EGG = PlatformHelper.registerSpawnEggItem(
        "raven_spawn_egg",
        FowlPlayEntityType.RAVEN,
        0x3B3B3D,
        0x1C1C1E
    );
    public static final Supplier<Item> ROBIN_SPAWN_EGG = PlatformHelper.registerSpawnEggItem(
        "robin_spawn_egg",
        FowlPlayEntityType.ROBIN,
        0x66696A,
        0xFF823F
    );
    public static final Supplier<Item> SPARROW_SPAWN_EGG = PlatformHelper.registerSpawnEggItem(
        "sparrow_spawn_egg",
        FowlPlayEntityType.SPARROW,
        0x5B3423,
        0xBCAE91
    );

//    private static RegistrySupplier<Item> register(String id, Item item, RegistryKey<ItemGroup> group) {
//        PlatformHelper.addItemToItemGroup(item, group);
//        return ITEMS.register(id, () -> item);
//    }

    public static void init() {
//        ITEMS.register();
    }
}
