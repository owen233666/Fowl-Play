package aqario.fowlplay.client.render;

import aqario.fowlplay.client.model.SeagullModel;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.SeagullEntity;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.util.RenderUtils;

public class SeagullRenderer extends GeoEntityRenderer<SeagullEntity> {
    @Nullable
    private SeagullEntity seagull;
    private ItemStack stack;
    private VertexConsumerProvider vertexConsumerProvider;

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

    @Override
    public void renderEarly(SeagullEntity animatable, MatrixStack poseStack, float partialTick, VertexConsumerProvider bufferSource, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float partialTicks) {
        this.seagull = animatable;
        this.stack = animatable.getEquippedStack(EquipmentSlot.MAINHAND);
        this.vertexConsumerProvider = bufferSource;
        super.renderEarly(animatable, poseStack, partialTick, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, partialTicks);
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (bone.getName().equals("head") && this.seagull != null) {
            poseStack.push();
            GeoBone parent = bone;
            while (parent != null) {
                RenderUtils.translateMatrixToBone(poseStack, parent);
                RenderUtils.translateToPivotPoint(poseStack, parent);
                RenderUtils.rotateMatrixAroundBone(poseStack, parent);
                RenderUtils.scaleMatrixForBone(poseStack, parent);
                RenderUtils.translateAwayFromPivotPoint(poseStack, parent);
                parent = parent.getParent();
            }

            poseStack.translate(0.0F, 0.45F, -0.41F);
            poseStack.scale(0.5F, 0.5F, 0.5F);
            poseStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0F));
            MinecraftClient.getInstance().getItemRenderer().renderItem(this.stack, ModelTransformation.Mode.GROUND, packedLight, packedOverlay, poseStack, this.vertexConsumerProvider, 0);
            poseStack.pop();
            buffer = this.vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(this.whTexture));
        }

        super.renderRecursively(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
