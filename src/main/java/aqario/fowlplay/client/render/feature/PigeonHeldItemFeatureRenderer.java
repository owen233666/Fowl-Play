package aqario.fowlplay.client.render.feature;

import aqario.fowlplay.client.model.PigeonEntityModel;
import aqario.fowlplay.common.entity.PigeonEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Axis;

public class PigeonHeldItemFeatureRenderer extends FeatureRenderer<PigeonEntity, PigeonEntityModel> {
    private final HeldItemRenderer heldItemRenderer;

    public PigeonHeldItemFeatureRenderer(FeatureRendererContext<PigeonEntity, PigeonEntityModel> context, HeldItemRenderer heldItemRenderer) {
        super(context);
        this.heldItemRenderer = heldItemRenderer;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, PigeonEntity pigeon, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        boolean sleeping = pigeon.isSleeping();
        boolean baby = pigeon.isBaby();
        matrices.push();
        if (baby) {
            matrices.scale(0.75F, 0.75F, 0.75F);
            matrices.translate(0.0F, 0.5F, 0.209375F);
        }

        matrices.translate(
            this.getContextModel().leftLeg.pivotX / 16.0F,
            this.getContextModel().leftLeg.pivotY / 16.0F,
            this.getContextModel().leftLeg.pivotZ / 16.0F
        );
//        float headRoll = pigeon.getHeadRoll(tickDelta);
        matrices.multiply(Axis.Z_POSITIVE.rotationDegrees(this.getContextModel().leftLeg.roll));
        matrices.multiply(Axis.Y_POSITIVE.rotationDegrees(this.getContextModel().leftLeg.yaw));
        matrices.multiply(Axis.X_POSITIVE.rotationDegrees(this.getContextModel().leftLeg.pitch));
        if (pigeon.isBaby()) {
            if (sleeping) {
                matrices.translate(0.4F, 0.26F, 0.15F);
            }
            else {
                matrices.translate(0.06F, 0.26F, -0.5F);
            }
        }
        else if (sleeping) {
            matrices.translate(0.46F, 0.26F, 0.22F);
        }
        else {
            matrices.translate(0.06F, 0.27F, -0.5F);
        }

        matrices.multiply(Axis.X_POSITIVE.rotationDegrees(90.0F));
        if (sleeping) {
            matrices.multiply(Axis.Z_POSITIVE.rotationDegrees(90.0F));
        }

        ItemStack stack = pigeon.getEquippedStack(EquipmentSlot.OFFHAND);
        this.heldItemRenderer.renderItem(pigeon, stack, ModelTransformationMode.GROUND, false, matrices, vertexConsumers, light);
        matrices.pop();
    }
}
