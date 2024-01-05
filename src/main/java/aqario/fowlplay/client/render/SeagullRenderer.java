package aqario.fowlplay.client.render;

import aqario.fowlplay.client.model.SeagullModel;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.SeagullEntity;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SeagullRenderer extends GeoEntityRenderer<SeagullEntity> {
    public SeagullRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SeagullModel());
    }

    @Override
    public Identifier getTextureResource(SeagullEntity instance) {
        return new Identifier(FowlPlay.ID, "textures/entity/seagull/seagull.png");
    }

    @Override
    public RenderLayer getRenderType(SeagullEntity animatable, float partialTick, MatrixStack poseStack, @Nullable VertexConsumerProvider bufferSource, @Nullable VertexConsumer buffer, int packedLight, Identifier texture) {
        return RenderLayer.getEntityCutoutNoCull(texture);
    }
}
