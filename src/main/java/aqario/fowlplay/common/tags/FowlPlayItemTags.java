package aqario.fowlplay.common.tags;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import aqario.fowlplay.common.FowlPlay;

public final class FowlPlayItemTags {
    public static final TagKey<Item> PENGUIN_TEMPT_ITEMS = FowlPlayItemTags.of("penguin_tempt_items");
    public static final TagKey<Item> PIGEON_TEMPT_ITEMS = FowlPlayItemTags.of("pigeon_tempt_items");
    public static final TagKey<Item> SEAGULL_TEMPT_ITEMS = FowlPlayItemTags.of("seagull_tempt_items");

    private static TagKey<Item> of(String id) {
        return TagKey.of(Registry.ITEM_KEY, new Identifier(FowlPlay.ID, id));
    }
}

