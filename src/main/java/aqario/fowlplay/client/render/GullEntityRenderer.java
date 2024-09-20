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
    private static final Identifier HERRING_GULL_TEXTURE = Identifier.of(FowlPlay.ID, "textures/entity/gull/herring_gull.png");
    private static final Identifier RING_BILLED_GULL_TEXTURE = Identifier.of(FowlPlay.ID, "textures/entity/gull/ring_billed_gull.png");

    public GullEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new GullEntityModel(context.getPart(FowlPlayEntityModelLayers.GULL)), 0.3f);
        this.addFeature(new BirdHeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()) {
            @Override
            public Vec3d getItemOffset() {
                return new Vec3d(0.0F, -0.3325F, -0.225F);
            }
        });
    }

    @Override
    public Identifier getTexture(GullEntity gull) {
        return HERRING_GULL_TEXTURE;
    }
}
