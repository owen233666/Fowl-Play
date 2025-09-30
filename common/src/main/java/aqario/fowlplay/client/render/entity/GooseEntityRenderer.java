package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.client.render.entity.model.GooseEntityModel;
import aqario.fowlplay.common.entity.GooseEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class GooseEntityRenderer extends MobEntityRenderer<GooseEntity, GooseEntityModel> {
    public GooseEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new GooseEntityModel(context.getPart(GooseEntityModel.MODEL_LAYER)), 0.3f);
        this.addFeature(new BirdHeldItemFeatureRenderer<>(
            this,
            context.getHeldItemRenderer(),
            new Vec3d(0.0, -0.05375, -0.1475)
        ));
    }

    @Override
    public Identifier getTexture(GooseEntity goose) {
        return goose.getVariant().value().texture();
    }
}
