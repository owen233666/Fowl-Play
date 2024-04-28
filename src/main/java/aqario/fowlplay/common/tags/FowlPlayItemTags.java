package aqario.fowlplay.common.tags;

import aqario.fowlplay.common.FowlPlay;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class FowlPlayItemTags {
    public static final TagKey<Item> PENGUIN_TEMPT_ITEMS = create("penguin_tempt_items");
    public static final TagKey<Item> PIGEON_TEMPT_ITEMS = create("pigeon_tempt_items");
    public static final TagKey<Item> SEAGULL_TEMPT_ITEMS = create("seagull_tempt_items");

    private static TagKey<Item> create(String id) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier(FowlPlay.ID, id));
    }
}
