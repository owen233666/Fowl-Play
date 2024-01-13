package aqario.fowlplay.common.tags;

import aqario.fowlplay.common.FowlPlay;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class FowlPlayBiomeTags {
    public static final TagKey<Biome> SPAWNS_ROBINS = create("spawns_robins");

    private static TagKey<Biome> create(String id) {
        return TagKey.of(Registry.BIOME_KEY, new Identifier(FowlPlay.ID, id));
    }
}
