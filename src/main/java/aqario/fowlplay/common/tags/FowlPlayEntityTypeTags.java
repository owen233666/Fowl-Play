package aqario.fowlplay.common.tags;

import aqario.fowlplay.common.FowlPlay;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class FowlPlayEntityTypeTags {
    public static final TagKey<EntityType<?>> BIRDS = create("birds");

    private static TagKey<EntityType<?>> create(String id) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(FowlPlay.ID, id));
    }
}
