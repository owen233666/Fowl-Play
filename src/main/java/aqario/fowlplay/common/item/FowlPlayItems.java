package aqario.fowlplay.common.item;

import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.FowlPlayEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

public class FowlPlayItems {
    public static final Item PENGUIN_SPAWN_EGG = register("penguin_spawn_egg",
        new SpawnEggItem(FowlPlayEntityType.PENGUIN, 0x151419, 0xfafafa,
            new QuiltItemSettings().group(ItemGroup.MISC)));
    public static final Item PIGEON_SPAWN_EGG = register("pigeon_spawn_egg",
        new SpawnEggItem(FowlPlayEntityType.PIGEON, 0x77797A, 0x755C5F,
            new QuiltItemSettings().group(ItemGroup.MISC)));
    public static final Item ROBIN_SPAWN_EGG = register("robin_spawn_egg",
        new SpawnEggItem(FowlPlayEntityType.ROBIN, 0x6f7172, 0xcc733d,
            new QuiltItemSettings().group(ItemGroup.MISC)));
    public static final Item SEAGULL_SPAWN_EGG = register("seagull_spawn_egg",
        new SpawnEggItem(FowlPlayEntityType.SEAGULL, 0xeaedf0, 0xffd850,
            new QuiltItemSettings().group(ItemGroup.MISC)));

    private static Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(FowlPlay.ID, id), item);
    }

    public static void init() {
    }
}
