package aqario.fowlplay.client.render;

import aqario.fowlplay.client.model.FowlPlayEntityModelLayers;
import aqario.fowlplay.client.model.RobinEntityModel;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.RobinEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class RobinEntityRenderer extends MobEntityRenderer<RobinEntity, RobinEntityModel> {
    public RobinEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new RobinEntityModel(context.getPart(FowlPlayEntityModelLayers.ROBIN)), 0.1f);
    }

    @Override
    public Identifier getTexture(RobinEntity entity) {
        return new Identifier(FowlPlay.ID, "textures/entity/robin/" + entity.getVariant().getId() + "_robin.png");
    }
}
