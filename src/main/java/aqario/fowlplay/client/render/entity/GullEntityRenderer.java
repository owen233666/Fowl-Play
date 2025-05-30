package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.client.render.entity.model.GullEntityModel;
import aqario.fowlplay.common.entity.GullEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class GullEntityRenderer extends MobEntityRenderer<GullEntity, GullEntityModel> {
    public GullEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new GullEntityModel(context.getPart(GullEntityModel.MODEL_LAYER)), 0.3f);
        this.addFeature(new BirdHeldItemFeatureRenderer<>(
            this,
            context.getHeldItemRenderer(),
            new Vec3d(0.0, -0.085, -0.1475)
        ));
    }

    @Override
    public Identifier getTexture(GullEntity gull) {
        return gull.getVariant().value().texture();
    }
}
