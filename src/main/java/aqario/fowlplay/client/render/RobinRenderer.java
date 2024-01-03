package aqario.fowlplay.client.render;

import aqario.fowlplay.client.model.RobinModel;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.RobinEntity;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RobinRenderer extends GeoEntityRenderer<RobinEntity> {
    public RobinRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new RobinModel());
    }

    @Override
    public Identifier getTextureResource(RobinEntity instance) {
        return new Identifier(FowlPlay.ID, "textures/entity/robin/" + instance.getVariant().getId() + "_robin.png");
    }

    @Override
    public RenderLayer getRenderType(RobinEntity animatable, float partialTick, MatrixStack poseStack, @Nullable VertexConsumerProvider bufferSource, @Nullable VertexConsumer buffer, int packedLight, Identifier texture) {
//        poseStack.scale(1.2f, 1.2f, 1.2f);
        return RenderLayer.getEntityCutoutNoCull(texture);
    }
}
