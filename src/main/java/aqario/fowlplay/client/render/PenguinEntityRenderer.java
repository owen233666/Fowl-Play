package aqario.fowlplay.client.render;

import aqario.fowlplay.client.model.FowlPlayEntityModelLayers;
import aqario.fowlplay.client.model.PenguinEntityModel;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.PenguinEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class PenguinEntityRenderer extends MobEntityRenderer<PenguinEntity, PenguinEntityModel> {
    public PenguinEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new PenguinEntityModel(context.getPart(FowlPlayEntityModelLayers.PENGUIN)), 0.3f);
    }

    @Override
    public Identifier getTexture(PenguinEntity entity) {
        return entity.isBaby() ? new Identifier(FowlPlay.ID, "textures/entity/penguin/penguin_baby.png") : new Identifier(FowlPlay.ID, "textures/entity/penguin/penguin.png");
    }
}
