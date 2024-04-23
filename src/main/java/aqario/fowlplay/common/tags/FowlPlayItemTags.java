package aqario.fowlplay.common.tags;

import aqario.fowlplay.common.FowlPlay;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public final class FowlPlayItemTags {
    public static final TagKey<Item> PENGUIN_TEMPT_ITEMS = register("penguin_tempt_items");
    public static final TagKey<Item> PIGEON_TEMPT_ITEMS = register("pigeon_tempt_items");
    public static final TagKey<Item> SEAGULL_TEMPT_ITEMS = register("seagull_tempt_items");

    private static TagKey<Item> register(String id) {
        return TagKey.of(Registry.ITEM_KEY, new Identifier(FowlPlay.ID, id));
    }
}
