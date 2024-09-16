package aqario.fowlplay.client.render;

import aqario.fowlplay.client.model.FowlPlayEntityModelLayers;
import aqario.fowlplay.client.model.GullEntityModel;
import aqario.fowlplay.client.render.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.GullEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class GullEntityRenderer extends MobEntityRenderer<GullEntity, GullEntityModel> {
    public GullEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new GullEntityModel(context.getPart(FowlPlayEntityModelLayers.GULL)), 0.1f);
        this.addFeature(new BirdHeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()) {
            @Override
            public Vec3d getItemOffset() {
                return new Vec3d(0.0F, -0.27F, -0.225F);
            }
        });
    }

    @Override
    public Identifier getTexture(GullEntity entity) {
        return Identifier.of(FowlPlay.ID, "textures/entity/gull/herring_gull.png");
    }
}
