package aqario.fowlplay.client.render.feature;

import aqario.fowlplay.client.model.SeagullEntityModel;
import aqario.fowlplay.common.entity.SeagullEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Axis;

public class SeagullHeldItemFeatureRenderer extends FeatureRenderer<SeagullEntity, SeagullEntityModel> {
    private final HeldItemRenderer heldItemRenderer;

    public SeagullHeldItemFeatureRenderer(FeatureRendererContext<SeagullEntity, SeagullEntityModel> context, HeldItemRenderer heldItemRenderer) {
        super(context);
        this.heldItemRenderer = heldItemRenderer;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, SeagullEntity seagull, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        matrices.push();

        matrices.translate(
            this.getContextModel().root.pivotX / 16.0F,
            this.getContextModel().root.pivotY / 16.0F,
            this.getContextModel().root.pivotZ / 16.0F
        );
        matrices.multiply(Axis.Z_POSITIVE.rotation(this.getContextModel().root.getTransform().roll));
        matrices.multiply(Axis.Y_POSITIVE.rotation(this.getContextModel().root.getTransform().yaw));
        matrices.multiply(Axis.X_POSITIVE.rotation(this.getContextModel().root.getTransform().pitch));

        matrices.translate(
            this.getContextModel().body.pivotX / 16.0F,
            this.getContextModel().body.pivotY / 16.0F,
            this.getContextModel().body.pivotZ / 16.0F
        );
        matrices.multiply(Axis.Z_POSITIVE.rotation(this.getContextModel().body.getTransform().roll));
        matrices.multiply(Axis.Y_POSITIVE.rotation(this.getContextModel().body.getTransform().yaw));
        matrices.multiply(Axis.X_POSITIVE.rotation(this.getContextModel().body.getTransform().pitch));

        matrices.translate(
            this.getContextModel().head.pivotX / 16.0F,
            this.getContextModel().head.pivotY / 16.0F,
            this.getContextModel().head.pivotZ / 16.0F
        );
        matrices.multiply(Axis.Z_POSITIVE.rotation(this.getContextModel().head.getTransform().roll));
        matrices.multiply(Axis.Y_POSITIVE.rotation(this.getContextModel().head.getTransform().yaw));
        matrices.multiply(Axis.X_POSITIVE.rotation(this.getContextModel().head.getTransform().pitch));

        matrices.translate(0.0F, -0.27F, -0.225F);

        matrices.multiply(Axis.X_POSITIVE.rotationDegrees(90.0F));

        matrices.scale(0.5F, 0.5F, 0.5F);

        ItemStack stack = seagull.getEquippedStack(EquipmentSlot.MAINHAND);
        this.heldItemRenderer.renderItem(seagull, stack, ModelTransformationMode.GROUND, false, matrices, vertexConsumers, light);
        matrices.pop();
    }
}
