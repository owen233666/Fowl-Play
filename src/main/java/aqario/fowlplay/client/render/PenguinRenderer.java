package aqario.fowlplay.client.render;

import aqario.fowlplay.client.model.PenguinModel;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.PenguinEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PenguinRenderer extends GeoEntityRenderer<PenguinEntity> {
    public PenguinRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PenguinModel());
    }

    @Override
    public Identifier getTextureResource(PenguinEntity instance) {
        return instance.isBaby() ? new Identifier(FowlPlay.ID, "textures/entity/penguin/penguin_baby.png") : new Identifier(FowlPlay.ID, "textures/entity/penguin/penguin.png");
    }

    @Override
    public RenderLayer getRenderType(PenguinEntity animatable, float partialTick, MatrixStack poseStack, @Nullable VertexConsumerProvider bufferSource, @Nullable VertexConsumer buffer, int packedLight, Identifier texture) {
        if (animatable.isBaby()) {
            poseStack.scale(0.8f, 0.8f, 0.8f);
        } else {
            poseStack.scale(1.1f, 1.1f, 1.1f);
        }
        return RenderLayer.getEntityCutoutNoCull(texture);
    }
}
