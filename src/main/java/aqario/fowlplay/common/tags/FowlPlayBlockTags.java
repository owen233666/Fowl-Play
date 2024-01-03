package aqario.fowlplay.common.tags;

import aqario.fowlplay.common.FowlPlay;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public final class FowlPlayBlockTags {
    public static final TagKey<Block> PENGUINS_SLIDE_ON = FowlPlayBlockTags.of("penguins_slide_on");

    private static TagKey<Block> of(String id) {
        return TagKey.of(Registry.BLOCK_KEY, new Identifier(FowlPlay.ID, id));
    }
}

