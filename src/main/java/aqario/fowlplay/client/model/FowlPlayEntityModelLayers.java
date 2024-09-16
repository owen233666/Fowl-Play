package aqario.fowlplay.client.model;

import aqario.fowlplay.common.FowlPlay;
import com.google.common.collect.Sets;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

import java.util.Set;
import java.util.stream.Stream;

public class FowlPlayEntityModelLayers {
    private static final String MAIN = "main";
    private static final Set<EntityModelLayer> LAYERS = Sets.newHashSet();
    public static final EntityModelLayer BLUE_JAY = registerMain("blue_jay");
    public static final EntityModelLayer CARDINAL = registerMain("cardinal");
    public static final EntityModelLayer GULL = registerMain("gull");
    public static final EntityModelLayer PENGUIN = registerMain("penguin");
    public static final EntityModelLayer PIGEON = registerMain("pigeon");
    public static final EntityModelLayer ROBIN = registerMain("robin");

    private static EntityModelLayer registerMain(String id) {
        return register(id, MAIN);
    }

    private static EntityModelLayer register(String id, String layer) {
        EntityModelLayer entityModelLayer = create(id, layer);
        if (!LAYERS.add(entityModelLayer)) {
            throw new IllegalStateException("Duplicate registration for " + entityModelLayer);
        }
        else {
            return entityModelLayer;
        }
    }

    private static EntityModelLayer create(String id, String layer) {
        return new EntityModelLayer(Identifier.of(FowlPlay.ID, id), layer);
    }

    private static EntityModelLayer createInnerArmor(String id) {
        return register(id, "inner_armor");
    }

    private static EntityModelLayer createOuterArmor(String id) {
        return register(id, "outer_armor");
    }

    public static Stream<EntityModelLayer> getLayers() {
        return LAYERS.stream();
    }
}
