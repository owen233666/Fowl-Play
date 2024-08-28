package aqario.fowlplay.client.model;

import aqario.fowlplay.client.render.animation.SeagullEntityAnimations;
import aqario.fowlplay.common.entity.SeagullEntity;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class SeagullEntityModel extends SinglePartEntityModel<SeagullEntity> {
    public final ModelPart root;
    public final ModelPart body;
    public final ModelPart head;
    public final ModelPart torso;
    public final ModelPart leftWing;
    public final ModelPart rightWing;
    public final ModelPart leftLeg;
    public final ModelPart rightLeg;
    public final ModelPart tail;

    public SeagullEntityModel(ModelPart root) {
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

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -2.5F, 0.0F));

        ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 12).cuboid(-1.0F, -6.0F, -0.5F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.75F, -2.25F));

        ModelPartData beak = head.addChild("beak", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.5F, -1.0F));

        ModelPartData torso = body.addChild("torso", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -3.5F, -6.5F, 3.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 2.5F, -0.4363F, 0.0F, 0.0F));

        ModelPartData left_wing = body.addChild("left_wing", ModelPartBuilder.create().uv(10, 12).cuboid(-0.75F, -1.0F, -0.5F, 1.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(1.75F, -4.75F, -1.0F, -0.3491F, 0.0F, 0.0F));

        ModelPartData right_wing = body.addChild("right_wing", ModelPartBuilder.create().uv(10, 12).mirrored().cuboid(-0.25F, -1.0F, -0.5F, 1.0F, 4.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.75F, -4.75F, -1.0F, -0.3491F, 0.0F, 0.0F));

        ModelPartData tail = body.addChild("tail", ModelPartBuilder.create().uv(14, 0).cuboid(-1.5F, -1.0F, 1.0F, 3.0F, 1.0F, 3.0F, new Dilation(0.0F))
            .uv(23, 0).cuboid(-1.5F, -1.0F, 4.0F, 3.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.5F, 3.5F, -0.2618F, 0.0F, 0.0F));

        ModelPartData left_leg = root.addChild("left_leg", ModelPartBuilder.create().uv(1, 5).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(1.25F, -3.0F, 1.5F, -0.1745F, 0.0F, 0.0F));

        ModelPartData cube_r1 = left_leg.addChild("cube_r1", ModelPartBuilder.create().uv(-1, 4).cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        ModelPartData right_leg = root.addChild("right_leg", ModelPartBuilder.create().uv(1, 5).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.25F, -3.0F, 1.5F, -0.1745F, 0.0F, 0.0F));

        ModelPartData cube_r2 = right_leg.addChild("cube_r2", ModelPartBuilder.create().uv(-1, 4).cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.0F, 0.0F, 0.1745F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(SeagullEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.updateHeadRotation(netHeadYaw, headPitch);
        this.animateWalk(SeagullEntityAnimations.WALK, limbSwing, limbSwingAmount, 2.0F, 2.5F);
        this.animate(entity.idleAnimationState, SeagullEntityAnimations.IDLE, ageInTicks);
        this.animate(entity.flyAnimationState, SeagullEntityAnimations.FLY, ageInTicks);
        this.animate(entity.floatAnimationState, SeagullEntityAnimations.FLOAT, ageInTicks);
    }

    private void updateHeadRotation(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);
        this.head.yaw = headYaw * (float) (Math.PI / 180.0);
        this.head.pitch = headPitch * (float) (Math.PI / 180.0);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        this.getPart().render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}