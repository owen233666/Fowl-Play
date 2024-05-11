package aqario.fowlplay.client.model;

import aqario.fowlplay.client.render.animation.PenguinEntityAnimations;
import aqario.fowlplay.common.entity.PenguinEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

public class PenguinEntityModel extends SinglePartEntityModel<PenguinEntity> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart torso;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart tail;

    public PenguinEntityModel(ModelPart root) {
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

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -17.0F, 0.0F));

        ModelPartData beak = head.addChild("beak", ModelPartBuilder.create().uv(12, 0).cuboid(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.5F, -5.0F));

        ModelPartData torso = body.addChild("torso", ModelPartBuilder.create().uv(0, 8).cuboid(-3.0F, -12.0F, -3.0F, 6.0F, 14.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -5.0F, 0.0F));

        ModelPartData left_wing = body.addChild("left_wing", ModelPartBuilder.create().uv(19, 0).mirrored().cuboid(0.0F, -1.0F, -2.0F, 1.0F, 10.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(3.0F, -14.0F, 0.0F));

        ModelPartData right_wing = body.addChild("right_wing", ModelPartBuilder.create().uv(19, 0).cuboid(-1.0F, -1.0F, -2.0F, 1.0F, 10.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, -14.0F, 0.0F));

        ModelPartData tail = body.addChild("tail", ModelPartBuilder.create(), ModelTransform.of(0.0F, -4.5F, 3.0F, -0.7854F, 0.0F, 0.0F));

        ModelPartData cube_r1 = tail.addChild("cube_r1", ModelPartBuilder.create().uv(30, 0).cuboid(0.0F, 0.0F, 0.0F, 3.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

        ModelPartData cube_r2 = tail.addChild("cube_r2", ModelPartBuilder.create().uv(24, 0).cuboid(-3.0F, 0.0F, 0.0F, 3.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        ModelPartData left_leg = root.addChild("left_leg", ModelPartBuilder.create().uv(24, 14).cuboid(-1.0F, -2.0F, -0.5F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F))
            .uv(24, 20).mirrored().cuboid(-1.0F, 2.0F, -2.5F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(1.5F, -3.0F, 0.0F));

        ModelPartData right_leg = root.addChild("right_leg", ModelPartBuilder.create().uv(24, 14).mirrored().cuboid(-1.0F, -2.0F, -0.5F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
            .uv(24, 20).cuboid(-1.0F, 2.0F, -2.5F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.5F, -3.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(PenguinEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.updateHeadRotation(netHeadYaw, headPitch);
        this.animateWalk(PenguinEntityAnimations.WALK, limbSwing, limbSwingAmount, 2.0F, 2.5F);
        this.animate(entity.idleState, PenguinEntityAnimations.IDLE, ageInTicks);
        this.animate(entity.flapState, PenguinEntityAnimations.FLY, ageInTicks);
        this.animate(entity.floatState, PenguinEntityAnimations.FLOAT, ageInTicks);
    }

    private void updateHeadRotation(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -90.0F, 90.0F);
        headPitch = MathHelper.clamp(headPitch, -45.0F, 45.0F);
        this.head.yaw = headYaw * (float) (Math.PI / 180.0);
        this.head.pitch = headPitch * (float) (Math.PI / 180.0);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}