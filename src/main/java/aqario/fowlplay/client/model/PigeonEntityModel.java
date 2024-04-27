package aqario.fowlplay.client.model;

import aqario.fowlplay.client.render.animation.PigeonEntityAnimations;
import aqario.fowlplay.common.entity.PigeonEntity;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class PigeonEntityModel extends SinglePartEntityModel<PigeonEntity> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart torso;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart tail;

    public PigeonEntityModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.head = this.body.getChild("head");
        this.torso = this.body.getChild("torso");
        this.leftWing = this.body.getChild("left_wing");
        this.rightWing = this.body.getChild("right_wing");
        this.leftLeg = this.root.getChild("left_leg");
        this.rightLeg = this.root.getChild("right_leg");
        this.tail = this.body.getChild("tail");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -2.75F, 0.0F));

        ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 9).cuboid(-1.0F, -5.0F, -1.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.75F, -2.5F));

        ModelPartData beak = head.addChild("beak", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.5F, -1.0F));

        ModelPartData torso = body.addChild("torso", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -2.0F, -4.0F, 3.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.6109F, 0.0F, 0.0F));

        ModelPartData left_wing = body.addChild("left_wing", ModelPartBuilder.create().uv(18, 0).cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(1.5F, -2.75F, -2.0F, -0.4363F, 0.0F, 0.0F));

        ModelPartData right_wing = body.addChild("right_wing", ModelPartBuilder.create().uv(18, 0).cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, -2.75F, -2.0F, -0.4363F, 0.0F, 0.0F));

        ModelPartData tail = body.addChild("tail", ModelPartBuilder.create().uv(12, 0).cuboid(-1.0F, -1.0F, 1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
            .uv(17, 0).cuboid(-1.0F, -1.0F, 3.0F, 2.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        ModelPartData left_leg = root.addChild("left_leg", ModelPartBuilder.create().uv(1, 4).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, -2.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

        left_leg.addChild("left_foot", ModelPartBuilder.create().uv(-1, 3).cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        ModelPartData right_leg = root.addChild("right_leg", ModelPartBuilder.create().uv(1, 4).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, -2.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

        right_leg.addChild("right_foot", ModelPartBuilder.create().uv(-1, 3).cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 0.0F, 0.2618F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(PigeonEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.updateHeadRotation(netHeadYaw, headPitch);
        float speed = Math.min((float) entity.getVelocity().lengthSquared() * 200.0F, 8.0F);
        this.updateAnimation(entity.idleAnimationState, PigeonEntityAnimations.IDLE, ageInTicks);
        this.updateAnimation(entity.walkAnimationState, PigeonEntityAnimations.WALK, ageInTicks, speed);
		this.updateAnimation(entity.flyAnimationState, PigeonEntityAnimations.FLY, ageInTicks);
		this.updateAnimation(entity.floatAnimationState, PigeonEntityAnimations.FLOAT, ageInTicks);
    }

    private void updateHeadRotation(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);
        this.head.yaw = headYaw * (float) (Math.PI / 180.0);
        this.head.pitch = headPitch * (float) (Math.PI / 180.0);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        root.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}