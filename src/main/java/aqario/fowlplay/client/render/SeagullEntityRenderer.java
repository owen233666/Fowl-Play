package aqario.fowlplay.client.render;

import aqario.fowlplay.client.model.FowlPlayEntityModelLayers;
import aqario.fowlplay.client.model.SeagullEntityModel;
import aqario.fowlplay.client.render.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.SeagullEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class SeagullEntityRenderer extends MobEntityRenderer<SeagullEntity, SeagullEntityModel> {
    public SeagullEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new SeagullEntityModel(context.getPart(FowlPlayEntityModelLayers.SEAGULL)), 0.1f);
        this.addFeature(new BirdHeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()) {
            @Override
            public Vec3d getItemOffset() {
                return new Vec3d(0.0F, -0.27F, -0.225F);
            }
        });
    }

    @Override
    public Identifier getTexture(SeagullEntity entity) {
        return new Identifier(FowlPlay.ID, "textures/entity/seagull/seagull.png");
    }
}
