package aqario.fowlplay.client.render;

import aqario.fowlplay.client.model.CardinalEntityModel;
import aqario.fowlplay.client.model.FowlPlayEntityModelLayers;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.CardinalEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class CardinalEntityRenderer extends MobEntityRenderer<CardinalEntity, CardinalEntityModel> {
    public CardinalEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new CardinalEntityModel(context.getPart(FowlPlayEntityModelLayers.CARDINAL)), 0.1f);
    }

    @Override
    public Identifier getTexture(CardinalEntity entity) {
        return new Identifier(FowlPlay.ID, "textures/entity/cardinal/cardinal.png");
    }
}
