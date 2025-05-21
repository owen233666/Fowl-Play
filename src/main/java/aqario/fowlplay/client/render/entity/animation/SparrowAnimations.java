package aqario.fowlplay.client.render.entity.animation;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class SparrowAnimations {
    public static final Animation STANDING = Animation.Builder.create(0f).looping()
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
                new Keyframe(0f, AnimationHelper.createRotationalVector(5f, 0f, 0f), Transformation.Interpolations.LINEAR)
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
                new Keyframe(0f, AnimationHelper.createRotationalVector(-2.5f, 0f, 0f), Transformation.Interpolations.LINEAR)
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

    public static final Animation WALKING = Animation.Builder.create(1f).looping()
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

    public static final Animation FLOATING = Animation.Builder.create(0f).looping()
        .build();

    public static final Animation GLIDING = Animation.Builder.create(0f).looping()
        .addBoneAnimation(
            "root",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.06f, 1f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "root",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(25f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.25f, -0.45f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(62.5f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-10f, 0f, 0f), Transformation.Interpolations.CUBIC)
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
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.6f, -1f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "head",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-85f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing_open",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_wing_open",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(8.47f, 2.31f, -13.2f), Transformation.Interpolations.CUBIC)
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
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_wing_open",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(8.47f, -2.31f, 13.2f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_wing_outer",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-5.2f, 17.15f, -15.58f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(-0.25f, -0.15f, -0.25f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-4.8f, -3.46f, -6.66f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_wing",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0.25f, -0.15f, -0.25f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_wing",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-4.8f, 3.46f, 6.66f), Transformation.Interpolations.LINEAR)
            )
        )
        .build();

    public static final Animation FLAPPING = Animation.Builder.create(0.16667f).looping()
        .addBoneAnimation(
            "root",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.06f, 1f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.06667f, AnimationHelper.createTranslationalVector(0f, 0.4f, 1.01f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.16667f, AnimationHelper.createTranslationalVector(0f, 0.06f, 1f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "root",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(25f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.06667f, AnimationHelper.createRotationalVector(30f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.16667f, AnimationHelper.createRotationalVector(25f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.25f, -0.45f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(62.5f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.16667f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-10f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.16667f, AnimationHelper.createRotationalVector(-10f, 0f, 0f), Transformation.Interpolations.CUBIC)
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
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.6f, -1f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "head",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-85f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing_open",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.08333f, AnimationHelper.createTranslationalVector(0.75f, -0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.16667f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_wing_open",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(16.2f, -12.78f, -47.55f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.01667f, AnimationHelper.createRotationalVector(15.76f, -7.27f, -36.54f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.04167f, AnimationHelper.createRotationalVector(13.48f, 8.63f, 4.57f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.08333f, AnimationHelper.createRotationalVector(22.02f, 25.18f, 62.21f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.11667f, AnimationHelper.createRotationalVector(6.02f, 10.52f, 11.47f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.13333f, AnimationHelper.createRotationalVector(2.55f, 1.76f, -6.06f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.15f, AnimationHelper.createRotationalVector(5.44f, -11.14f, -27.82f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.16667f, AnimationHelper.createRotationalVector(16.2f, -12.78f, -47.55f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_wing_outer",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(2.78f, -17.69f, -10.64f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.01667f, AnimationHelper.createRotationalVector(2.54f, -11.68f, -14.38f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.04167f, AnimationHelper.createRotationalVector(2.95f, -5.53f, -5.82f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.06667f, AnimationHelper.createRotationalVector(1.27f, -5.68f, -3.47f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.10833f, AnimationHelper.createRotationalVector(-11.11f, -19.08f, 52.48f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.13333f, AnimationHelper.createRotationalVector(-13.77f, -24.38f, 59.11f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.16667f, AnimationHelper.createRotationalVector(2.78f, -17.69f, -10.64f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_wing_open",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.08333f, AnimationHelper.createTranslationalVector(-0.75f, -0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.16667f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_wing_open",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(16.2f, 12.78f, 47.55f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.01667f, AnimationHelper.createRotationalVector(15.76f, 7.27f, 36.54f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.04167f, AnimationHelper.createRotationalVector(13.48f, -8.63f, -4.57f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.08333f, AnimationHelper.createRotationalVector(22.02f, -25.18f, -62.21f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.11667f, AnimationHelper.createRotationalVector(6.02f, -10.52f, -11.47f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.13333f, AnimationHelper.createRotationalVector(2.55f, -1.76f, 6.06f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.15f, AnimationHelper.createRotationalVector(5.44f, 11.14f, 27.82f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.16667f, AnimationHelper.createRotationalVector(16.2f, 12.78f, 47.55f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_wing_outer",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(2.78f, 17.69f, 10.64f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.01667f, AnimationHelper.createRotationalVector(2.54f, 11.68f, 14.38f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.04167f, AnimationHelper.createRotationalVector(5.42f, 5.2f, 5.8f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.06667f, AnimationHelper.createRotationalVector(1.27f, 5.68f, 3.47f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.10833f, AnimationHelper.createRotationalVector(-11.11f, 19.08f, -52.48f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.13333f, AnimationHelper.createRotationalVector(-13.77f, 24.38f, -59.11f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.16667f, AnimationHelper.createRotationalVector(2.78f, 17.69f, 10.64f), Transformation.Interpolations.CUBIC)
            )
        )
        .build();

    public static final Animation PREENING = Animation.Builder.create(1f)
        .addBoneAnimation(
            "left_wing",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(-0.2f, -0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1.5f, AnimationHelper.createTranslationalVector(-0.2f, -0.25f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_wing",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-7.28f, -5.95f, -2.32f), Transformation.Interpolations.CUBIC),
                new Keyframe(1.5f, AnimationHelper.createRotationalVector(-7.28f, -5.95f, -2.32f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_wing",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0.2f, -0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1.5f, AnimationHelper.createTranslationalVector(0.2f, -0.25f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_wing",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-7.28f, 5.95f, 2.32f), Transformation.Interpolations.CUBIC),
                new Keyframe(1.5f, AnimationHelper.createRotationalVector(-7.28f, 5.95f, 2.32f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "body",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "body",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(5f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.125f, AnimationHelper.createRotationalVector(-2.5f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.33333f, AnimationHelper.createRotationalVector(-9.79f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.625f, AnimationHelper.createRotationalVector(-0.83f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1.375f, AnimationHelper.createRotationalVector(-2.5f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1.5f, AnimationHelper.createRotationalVector(5f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, -0.1f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.125f, AnimationHelper.createTranslationalVector(0f, 0.5f, -0.09f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.625f, AnimationHelper.createTranslationalVector(0f, 0.38f, -0.27f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.875f, AnimationHelper.createTranslationalVector(0f, 0.25f, -0.35f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createTranslationalVector(0f, 0.2f, -0.07f), Transformation.Interpolations.LINEAR),
                new Keyframe(1.375f, AnimationHelper.createTranslationalVector(0f, 0.2f, -0.07f), Transformation.Interpolations.LINEAR),
                new Keyframe(1.5f, AnimationHelper.createTranslationalVector(0f, 0f, -0.1f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-2.5f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.125f, AnimationHelper.createRotationalVector(15.86f, -175f, -10.39f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.20833f, AnimationHelper.createRotationalVector(32.13f, -187.15f, -11.12f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.29167f, AnimationHelper.createRotationalVector(26.61f, -183f, -11.17f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.375f, AnimationHelper.createRotationalVector(35.32f, -181.15f, -11.19f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.45833f, AnimationHelper.createRotationalVector(30.98f, -187.58f, -6.22f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.54167f, AnimationHelper.createRotationalVector(23.41f, -184.18f, -4.31f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.625f, AnimationHelper.createRotationalVector(29.07f, -181.81f, -10.95f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.70833f, AnimationHelper.createRotationalVector(30.39f, -179.98f, 3.28f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.79167f, AnimationHelper.createRotationalVector(24.35f, -183.62f, -6.44f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.875f, AnimationHelper.createRotationalVector(15.86f, -175f, -10.39f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createRotationalVector(-2.48f, -147.85f, -1.57f), Transformation.Interpolations.LINEAR),
                new Keyframe(1.375f, AnimationHelper.createRotationalVector(-2.48f, -147.85f, -1.57f), Transformation.Interpolations.LINEAR),
                new Keyframe(1.5f, AnimationHelper.createRotationalVector(-2.5f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-0.05f, -4.83f, -3.7f), Transformation.Interpolations.CUBIC),
                new Keyframe(1.5f, AnimationHelper.createRotationalVector(-0.05f, -4.83f, -3.7f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-0.05f, 4.83f, 3.7f), Transformation.Interpolations.CUBIC),
                new Keyframe(1.5f, AnimationHelper.createRotationalVector(-0.05f, 4.83f, 3.7f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "root",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.16667f, AnimationHelper.createRotationalVector(-25f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.54167f, AnimationHelper.createRotationalVector(-21.12f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.875f, AnimationHelper.createRotationalVector(-25f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1.375f, AnimationHelper.createRotationalVector(-26.68f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "head",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "head",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .build();

    public static final Animation SCRATCHING = Animation.Builder.create(1f)
        .addBoneAnimation(
            "left_wing",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(-0.2f, -0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createTranslationalVector(-0.2f, -0.25f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_wing",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-7.28f, -5.95f, -2.32f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createRotationalVector(-7.28f, -5.95f, -2.32f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_wing",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0.2f, -0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createTranslationalVector(0.2f, -0.25f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_wing",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-7.28f, 5.95f, 2.32f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createRotationalVector(-7.28f, 5.95f, 2.32f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "body",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.125f, AnimationHelper.createTranslationalVector(-0.25f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.875f, AnimationHelper.createTranslationalVector(-0.25f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "body",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(5f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.125f, AnimationHelper.createRotationalVector(3.25f, -10.69f, 9.26f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.875f, AnimationHelper.createRotationalVector(3.25f, -10.69f, 9.26f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createRotationalVector(5f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, -0.1f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createTranslationalVector(0f, 0f, -0.1f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-2.5f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.125f, AnimationHelper.createRotationalVector(-2.78f, -38.72f, 31.76f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.875f, AnimationHelper.createRotationalVector(-2.78f, -38.72f, 31.76f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createRotationalVector(-2.5f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.125f, AnimationHelper.createTranslationalVector(0.5f, 0.5f, -0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.20834334f, AnimationHelper.createTranslationalVector(0.47f, 0.47f, -0.44f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2916767f, AnimationHelper.createTranslationalVector(0.4f, 0.4f, -0.34f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0.47f, 0.47f, -0.44f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.4583433f, AnimationHelper.createTranslationalVector(0.24f, 0.24f, -0.06f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5416766f, AnimationHelper.createTranslationalVector(0.35f, 0.35f, -0.78f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.625f, AnimationHelper.createTranslationalVector(0.24f, 0.24f, -0.56f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.7083434f, AnimationHelper.createTranslationalVector(0.35f, 0.35f, -0.78f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.7916766f, AnimationHelper.createTranslationalVector(0.24f, 0.24f, -0.56f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.875f, AnimationHelper.createTranslationalVector(0.35f, 0.35f, -0.78f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-0.05f, -4.83f, -3.7f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.125f, AnimationHelper.createRotationalVector(-119.44f, -15.74f, 6.78f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-81.98f, -15.06f, 6.12f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(-117.13f, -13.46f, 4.59f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.375f, AnimationHelper.createRotationalVector(-81.98f, -15.06f, 6.12f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.4583433f, AnimationHelper.createRotationalVector(-110.62f, -18.23f, 7.18f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(-77.57f, -12.33f, 3.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.625f, AnimationHelper.createRotationalVector(-122.02f, -19.24f, 11.08f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.7083434f, AnimationHelper.createRotationalVector(-77.57f, -12.33f, 3.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.7916766f, AnimationHelper.createRotationalVector(-122.02f, -19.24f, 11.08f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.875f, AnimationHelper.createRotationalVector(-77.57f, -12.33f, 3.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createRotationalVector(-0.05f, -4.83f, -3.7f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-0.05f, 4.83f, 3.7f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.125f, AnimationHelper.createRotationalVector(-0.65f, 2.18f, -5.96f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.875f, AnimationHelper.createRotationalVector(-0.65f, 2.18f, -5.96f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createRotationalVector(-0.05f, 4.83f, 3.7f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "root",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.125f, AnimationHelper.createTranslationalVector(-0.25f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.875f, AnimationHelper.createTranslationalVector(-0.25f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.125f, AnimationHelper.createTranslationalVector(0f, 0.75f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.875f, AnimationHelper.createTranslationalVector(0f, 0.75f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.125f, AnimationHelper.createRotationalVector(-47.5f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createRotationalVector(-47.5f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.3433333f, AnimationHelper.createRotationalVector(-38.14f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.875f, AnimationHelper.createRotationalVector(-47.5f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .build();
}