package aqario.fowlplay.client.render;

import aqario.fowlplay.client.model.PigeonModel;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.PigeonEntity;
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

public class PigeonRenderer extends GeoEntityRenderer<PigeonEntity> {
    @Nullable
    private PigeonEntity pigeon;
    private ItemStack beakStack;
    private ItemStack bundleStack;
    private VertexConsumerProvider vertexConsumerProvider;

    public PigeonRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PigeonModel());
    }

    @Override
    public Identifier getTextureResource(PigeonEntity instance) {
        return new Identifier(FowlPlay.ID, "textures/entity/pigeon/pigeon.png");
    }

    @Override
    public RenderLayer getRenderType(PigeonEntity animatable, float partialTick, MatrixStack poseStack, @Nullable VertexConsumerProvider bufferSource, @Nullable VertexConsumer buffer, int packedLight, Identifier texture) {
        return RenderLayer.getEntityCutoutNoCull(texture);
    }

    @Override
    public void renderEarly(PigeonEntity animatable, MatrixStack poseStack, float partialTick, VertexConsumerProvider bufferSource, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float partialTicks) {
        this.pigeon = animatable;
        this.beakStack = animatable.getEquippedStack(EquipmentSlot.MAINHAND);
        this.bundleStack = animatable.getEquippedStack(EquipmentSlot.OFFHAND);
        this.vertexConsumerProvider = bufferSource;
        super.renderEarly(animatable, poseStack, partialTick, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, partialTicks);
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (bone.getName().equals("head") && pigeon != null) {
            poseStack.push();
            GeoBone parent = bone;
            while (parent != null) {
                RenderUtils.translateMatrixToBone(poseStack, parent);
//              if (SWIMMING) {
//                  poseStack.translate(0, 0.5d, 0.25d);
//              } else if (FLYING) {
//                  poseStack.translate(0, 0.6d, 0.5d);
//              }
                RenderUtils.translateToPivotPoint(poseStack, parent);
                RenderUtils.rotateMatrixAroundBone(poseStack, parent);
                RenderUtils.scaleMatrixForBone(poseStack, parent);
                RenderUtils.translateAwayFromPivotPoint(poseStack, parent);
                parent = parent.getParent();
            }

            poseStack.translate(0.0F, 0.45F, -0.41F);
            poseStack.scale(0.5F, 0.5F, 0.5F);
            poseStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0F));
            MinecraftClient.getInstance().getItemRenderer().renderItem(this.beakStack, ModelTransformation.Mode.GROUND, packedLight, packedOverlay, poseStack, this.vertexConsumerProvider, 0);
            poseStack.pop();
            buffer = this.vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(this.whTexture));
        }

        if (bone.getName().equals("left_leg")) {
            poseStack.push();
            GeoBone parent = bone;
            while (parent != null) {
                RenderUtils.translateMatrixToBone(poseStack, parent);
//              if (SWIMMING) {
//                  poseStack.translate(0, 0.5d, 0.25d);
//              } else if (FLYING) {
//                  poseStack.translate(0, 0.6d, 0.5d);
//              }
                RenderUtils.translateToPivotPoint(poseStack, parent);
                RenderUtils.rotateMatrixAroundBone(poseStack, parent);
                RenderUtils.scaleMatrixForBone(poseStack, parent);
                RenderUtils.translateAwayFromPivotPoint(poseStack, parent);
                parent = parent.getParent();
            }

            poseStack.translate(-0.1, 0.06, -0.02);
            poseStack.scale(0.25F, 0.25F, 0.25F);
            poseStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
            MinecraftClient.getInstance().getItemRenderer().renderItem(this.bundleStack, ModelTransformation.Mode.GROUND, packedLight, packedOverlay, poseStack, this.vertexConsumerProvider, 0);
            poseStack.pop();
            buffer = this.vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(this.whTexture));
        }

        super.renderRecursively(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
