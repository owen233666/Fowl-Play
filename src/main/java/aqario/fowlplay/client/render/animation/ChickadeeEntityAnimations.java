package aqario.fowlplay.client.render.animation;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class ChickadeeEntityAnimations {
    public static final Animation CHICKADEE_IDLE = Animation.Builder.create(1f).looping()
        .addBoneAnimation(
            "left_wing",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(-0.2f, -0.25f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-7.28f, -5.95f, -2.32f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_wing",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0.2f, -0.25f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_wing",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-7.28f, 5.95f, 2.32f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "body",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(10f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, -0.1f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-0.05f, -4.83f, -3.7f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-0.05f, 4.83f, 3.7f), Transformation.Interpolations.LINEAR)
            )
        )
        .build();

    public static final Animation CHICKADEE_WALK = Animation.Builder.create(1f).looping()
        .addBoneAnimation(
            "body",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.16766666f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.6766666f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "body",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(17.5f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(19f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createRotationalVector(17.5f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.6766666f, AnimationHelper.createRotationalVector(19f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createRotationalVector(17.5f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -0.15f, -0.2f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-15f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-2.5f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.16766666f, AnimationHelper.createTranslationalVector(0f, 0f, 0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0.5f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.8343334f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(50f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.8343334f, AnimationHelper.createRotationalVector(-25f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.5f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.3433333f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.6766666f, AnimationHelper.createTranslationalVector(0f, 0f, 0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createTranslationalVector(0f, 0.5f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.3433333f, AnimationHelper.createRotationalVector(-25f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.6766666f, AnimationHelper.createRotationalVector(50f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "root",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.16766666f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.6766666f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .build();

    public static final Animation CHICKADEE_FLOAT = Animation.Builder.create(0f).looping()
        .build();

    public static final Animation CHICKADEE_GLIDE = Animation.Builder.create(1f).looping()
        .addBoneAnimation(
            "root",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(35f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.15f, 0.25f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(50f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-10f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2708343f, AnimationHelper.createRotationalVector(-8f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.3541677f, AnimationHelper.createRotationalVector(-8.44f, 0.2f, 0.46f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.4583433f, AnimationHelper.createRotationalVector(-9.18f, 0.41f, 0.91f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.7708344f, AnimationHelper.createRotationalVector(-7.99f, -0.78f, -1.84f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createRotationalVector(-10f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(92.99f, -5.05f, -4.59f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(92.99f, 5.05f, 4.59f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "head",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 1f, -0.85f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "head",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-80f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing_open",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_wing_open",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-1.53f, 2.31f, -13.2f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.14583434f, AnimationHelper.createRotationalVector(-1.37f, 3.59f, -9.94f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(-1.55f, 2.1f, -13.72f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.7708344f, AnimationHelper.createRotationalVector(-1.65f, -0.09f, -19.25f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createRotationalVector(-1.53f, 2.31f, -13.2f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_wing_outer",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-5.2f, -17.15f, 15.58f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_wing_open",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_wing_open",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-1.53f, -2.31f, 13.2f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.22916766f, AnimationHelper.createRotationalVector(-1.65f, -0.48f, 17.81f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.4167667f, AnimationHelper.createRotationalVector(-1.61f, -1.4f, 15.49f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5834334f, AnimationHelper.createRotationalVector(-1.61f, -1.4f, 15.49f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.7291676f, AnimationHelper.createRotationalVector(-1.47f, -2.87f, 11.76f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createRotationalVector(-1.53f, -2.31f, 13.2f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_wing_outer",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-5.2f, 17.15f, -15.58f), Transformation.Interpolations.LINEAR)
            )
        )
        .build();

    public static final Animation CHICKADEE_FLAP = Animation.Builder.create(0.3433333f).looping()
        .addBoneAnimation(
            "root",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.06f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.027777777777777776f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.06944444444444445f, AnimationHelper.createTranslationalVector(0f, 0.5f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.16766666f, AnimationHelper.createTranslationalVector(0f, 0.06f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.19444444444444445f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2361111111111111f, AnimationHelper.createTranslationalVector(0f, 0.5f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.3433333f, AnimationHelper.createTranslationalVector(0f, 0.06f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "root",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(35f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.15f, 0.25f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(50f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.16766666f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.3433333f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-10f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(-10f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.3433333f, AnimationHelper.createRotationalVector(-10f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(92.99f, -5.05f, -4.59f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(92.99f, 5.05f, 4.59f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "head",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 1f, -0.85f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "head",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-80f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing_open",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.06944444444444445f, AnimationHelper.createTranslationalVector(0.5f, 0.5f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.16766666f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2361111111111111f, AnimationHelper.createTranslationalVector(0.5f, 0.5f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.3433333f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_wing_open",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(0.03f, -8.54f, -41.26f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.013888888888888888f, AnimationHelper.createRotationalVector(5.51f, -6.05f, -34.35f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.06944444444444445f, AnimationHelper.createRotationalVector(4.65f, 15.46f, 25.14f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.09722222222222222f, AnimationHelper.createRotationalVector(-3.98f, 10.52f, 11.47f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.125f, AnimationHelper.createRotationalVector(-5.82f, 1.84f, -12.52f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(0.03f, -8.54f, -41.26f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.18055555555555555f, AnimationHelper.createRotationalVector(5.51f, -6.05f, -34.35f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2361111111111111f, AnimationHelper.createRotationalVector(4.65f, 15.46f, 25.14f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2638888888888889f, AnimationHelper.createRotationalVector(-3.98f, 10.52f, 11.47f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(-5.82f, 1.84f, -12.52f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.3433333f, AnimationHelper.createRotationalVector(0.03f, -8.54f, -41.26f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_wing_outer",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(1.18f, -17.86f, -5.39f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.013888888888888888f, AnimationHelper.createRotationalVector(0.97f, -11.92f, -6.72f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.041676664f, AnimationHelper.createRotationalVector(-2.8f, -5.86f, 1.71f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.06944444444444445f, AnimationHelper.createRotationalVector(-9.24f, -5.35f, 29.41f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(1.18f, -17.86f, -5.39f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.18055555555555555f, AnimationHelper.createRotationalVector(0.97f, -11.92f, -6.72f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-2.8f, -5.86f, 1.71f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2361111111111111f, AnimationHelper.createRotationalVector(-9.24f, -5.35f, 29.41f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.3433333f, AnimationHelper.createRotationalVector(1.18f, -17.86f, -5.39f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_wing_open",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.06944444444444445f, AnimationHelper.createTranslationalVector(-0.5f, 0.5f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.16766666f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2361111111111111f, AnimationHelper.createTranslationalVector(-0.5f, 0.5f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.3433333f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_wing_open",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(0.03f, 8.54f, 41.26f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.013888888888888888f, AnimationHelper.createRotationalVector(5.51f, 6.05f, 34.35f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.06944444444444445f, AnimationHelper.createRotationalVector(4.65f, -15.46f, -25.14f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.09722222222222222f, AnimationHelper.createRotationalVector(-3.98f, -10.52f, -11.47f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.125f, AnimationHelper.createRotationalVector(-5.82f, -1.84f, 12.52f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(0.03f, 8.54f, 41.26f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.18055555555555555f, AnimationHelper.createRotationalVector(5.51f, 6.05f, 34.35f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2361111111111111f, AnimationHelper.createRotationalVector(4.65f, -15.46f, -25.14f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2638888888888889f, AnimationHelper.createRotationalVector(-3.98f, -10.52f, -11.47f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(-5.82f, -1.84f, 12.52f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.3433333f, AnimationHelper.createRotationalVector(0.03f, 8.54f, 41.26f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_wing_outer",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(1.18f, 17.86f, 5.39f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.013888888888888888f, AnimationHelper.createRotationalVector(0.97f, 11.92f, 6.72f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.041676664f, AnimationHelper.createRotationalVector(-2.8f, 5.86f, -1.71f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.06944444444444445f, AnimationHelper.createRotationalVector(-9.24f, 5.35f, -29.41f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(1.18f, 17.86f, 5.39f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.18055555555555555f, AnimationHelper.createRotationalVector(0.97f, 11.92f, 6.72f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-2.8f, 5.86f, -1.71f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2361111111111111f, AnimationHelper.createRotationalVector(-9.24f, 5.35f, -29.41f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.3433333f, AnimationHelper.createRotationalVector(1.18f, 17.86f, 5.39f), Transformation.Interpolations.CUBIC)
            )
        )
        .build();
}