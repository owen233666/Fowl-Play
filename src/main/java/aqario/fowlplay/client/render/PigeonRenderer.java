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
import net.minecraft.util.math.Quaternion;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PigeonRenderer extends GeoEntityRenderer<PigeonEntity> {
    private ItemStack itemStack;
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
        this.itemStack = animatable.getEquippedStack(EquipmentSlot.MAINHAND);
        this.vertexConsumerProvider = bufferSource;
        super.renderEarly(animatable, poseStack, partialTick, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, partialTicks);
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (bone.getName().equals("left_leg")) {
            poseStack.push();
            poseStack.translate((bone.getPositionX() / -16.0 - 0.08), (bone.getPositionY() / 16.0 + 0.06), -0.02);
            poseStack.scale(0.25F, 0.25F, 0.25F);
            poseStack.multiply(new Quaternion(bone.getRotationX(), bone.getRotationY() - 1.5708F, bone.getRotationZ(), false));
            MinecraftClient.getInstance().getItemRenderer().renderItem(this.itemStack, ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND, packedLight, packedOverlay, poseStack, this.vertexConsumerProvider, 0);
            poseStack.pop();
            buffer = this.vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(this.whTexture));
        }
        super.renderRecursively(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
