package aqario.fowlplay.common.tags;

import aqario.fowlplay.common.FowlPlay;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class FowlPlayItemTags {
    public static final TagKey<Item> PENGUIN_FOOD = create("penguin_food");
    public static final TagKey<Item> PIGEON_FOOD = create("pigeon_food");
    public static final TagKey<Item> SEAGULL_FOOD = create("seagull_food");

    private static TagKey<Item> create(String id) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier(FowlPlay.ID, id));
    }
}
