package aqario.fowlplay.client.render.animation;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class PigeonEntityAnimations {
    public static final Animation PIGEON_IDLE = Animation.Builder.create(1.0F).looping()
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0.3f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(2.5f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(-0.2f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-7.23f, -4.53f, -2.84f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_wing",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0.2f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_wing",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-7.23f, 4.53f, 2.84f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-5f, 0f, 0f), Transformation.Interpolations.LINEAR)
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
        ).build();

    public static final Animation PIGEON_WALK = Animation.Builder.create(1.0f).looping()
        .addBoneAnimation(
            "body",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.2f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.08343333f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2916767f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0.2f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5834334f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.7916766f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createTranslationalVector(0f, 0.2f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "body",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(10f, 1f, 0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(10f, -2.5f, 5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.4167667f, AnimationHelper.createRotationalVector(10f, -1.5f, 1f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createRotationalVector(10f, -1f, -0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.7916766f, AnimationHelper.createRotationalVector(10f, 2.5f, -5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.9167666f, AnimationHelper.createRotationalVector(10f, 1.5f, -1f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createRotationalVector(10f, 1f, 0.5f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, -0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2916767f, AnimationHelper.createTranslationalVector(-0.25f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.4167667f, AnimationHelper.createTranslationalVector(-0.12f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0f, -0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.7916766f, AnimationHelper.createTranslationalVector(0.25f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.9167666f, AnimationHelper.createTranslationalVector(0.12f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createTranslationalVector(0f, 0f, -0.5f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-5f, -1f, -0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(-10f, 2.5f, -5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.4167667f, AnimationHelper.createRotationalVector(-10f, 1.5f, -1f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createRotationalVector(-5f, 1f, 0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.7916766f, AnimationHelper.createRotationalVector(-10f, -2.5f, 5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.9167666f, AnimationHelper.createRotationalVector(-10f, -1.5f, 1f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createRotationalVector(-5f, -1f, -0.5f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-5f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, -0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.08343333f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0.43f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, 0f, 0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.4167667f, AnimationHelper.createTranslationalVector(0f, 1.5f, 0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 1.37f, 0.28f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.75f, AnimationHelper.createTranslationalVector(0f, 0.25f, -0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.8343334f, AnimationHelper.createTranslationalVector(0f, -0.7f, 0.27f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createTranslationalVector(0f, 0.1f, -0.5f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(4.38f, -10f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createRotationalVector(42.5f, -10f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createRotationalVector(10f, -10f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.75f, AnimationHelper.createRotationalVector(-32.5f, -10f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createRotationalVector(4.38f, -10f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 1.37f, 0.28f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, 0.25f, -0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.3433333f, AnimationHelper.createTranslationalVector(0f, -0.7f, 0.27f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0.1f, -0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5834334f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0.43f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.75f, AnimationHelper.createTranslationalVector(0f, 0f, 0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.9167666f, AnimationHelper.createTranslationalVector(0f, 1.5f, 0.5f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createTranslationalVector(0f, 1.37f, 0.28f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(10f, 10f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createRotationalVector(-32.5f, 10f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createRotationalVector(4.37f, 10f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.75f, AnimationHelper.createRotationalVector(42.5f, 10f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(1f, AnimationHelper.createRotationalVector(10f, 10f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .build();

    public static final Animation PIGEON_FLOAT = Animation.Builder.create(0f).looping()
        .build();

    public static final Animation PIGEON_FLAP = Animation.Builder.create(0.5f).looping()
        .addBoneAnimation(
            "root",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.06f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.041676664f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.10416767f, AnimationHelper.createTranslationalVector(0f, 0.5f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, 0.06f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2916767f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.3541677f, AnimationHelper.createTranslationalVector(0f, 0.5f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0.06f, 0f), Transformation.Interpolations.CUBIC)
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
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -0.25f, 1f), Transformation.Interpolations.LINEAR)
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
            "head",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0.15f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "head",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-82.5f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing_open",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.10416767f, AnimationHelper.createTranslationalVector(0.5f, 0.5f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.3541677f, AnimationHelper.createTranslationalVector(0.5f, 0.5f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_wing_open",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(0.03f, -8.54f, -41.26f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.020834332f, AnimationHelper.createRotationalVector(5.51f, -6.05f, -34.35f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.10416767f, AnimationHelper.createRotationalVector(4.65f, 15.46f, 25.14f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.14583434f, AnimationHelper.createRotationalVector(-3.98f, 10.52f, 11.47f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.1875f, AnimationHelper.createRotationalVector(-5.82f, 1.84f, -12.52f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createRotationalVector(0.03f, -8.54f, -41.26f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2708343f, AnimationHelper.createRotationalVector(5.51f, -6.05f, -34.35f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.3541677f, AnimationHelper.createRotationalVector(4.65f, 15.46f, 25.14f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.3958343f, AnimationHelper.createRotationalVector(-3.98f, 10.52f, 11.47f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.4375f, AnimationHelper.createRotationalVector(-5.82f, 1.84f, -12.52f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createRotationalVector(0.03f, -8.54f, -41.26f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_wing_outer",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(1.18f, -17.86f, -5.39f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.020834332f, AnimationHelper.createRotationalVector(0.97f, -11.92f, -6.72f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.0625f, AnimationHelper.createRotationalVector(-2.8f, -5.86f, 1.71f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.10416767f, AnimationHelper.createRotationalVector(-9.24f, -5.35f, 29.41f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createRotationalVector(1.18f, -17.86f, -5.39f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2708343f, AnimationHelper.createRotationalVector(0.97f, -11.92f, -6.72f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.3125f, AnimationHelper.createRotationalVector(-2.8f, -5.86f, 1.71f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.3541677f, AnimationHelper.createRotationalVector(-9.24f, -5.35f, 29.41f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createRotationalVector(1.18f, -17.86f, -5.39f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_wing_open",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.10416767f, AnimationHelper.createTranslationalVector(-0.5f, 0.5f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.3541677f, AnimationHelper.createTranslationalVector(-0.5f, 0.5f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_wing_open",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(0.03f, 8.54f, 41.26f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.020834332f, AnimationHelper.createRotationalVector(5.51f, 6.05f, 34.35f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.10416767f, AnimationHelper.createRotationalVector(4.65f, -15.46f, -25.14f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.14583434f, AnimationHelper.createRotationalVector(-3.98f, -10.52f, -11.47f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.1875f, AnimationHelper.createRotationalVector(-5.82f, -1.84f, 12.52f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createRotationalVector(0.03f, 8.54f, 41.26f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2708343f, AnimationHelper.createRotationalVector(5.51f, 6.05f, 34.35f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.3541677f, AnimationHelper.createRotationalVector(4.65f, -15.46f, -25.14f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.3958343f, AnimationHelper.createRotationalVector(-3.98f, -10.52f, -11.47f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.4375f, AnimationHelper.createRotationalVector(-5.82f, -1.84f, 12.52f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createRotationalVector(0.03f, 8.54f, 41.26f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_wing_outer",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(1.18f, 17.86f, 5.39f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.020834332f, AnimationHelper.createRotationalVector(0.97f, 11.92f, 6.72f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.0625f, AnimationHelper.createRotationalVector(-2.8f, 5.86f, -1.71f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.10416767f, AnimationHelper.createRotationalVector(-9.24f, 5.35f, -29.41f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createRotationalVector(1.18f, 17.86f, 5.39f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2708343f, AnimationHelper.createRotationalVector(0.97f, 11.92f, 6.72f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.3125f, AnimationHelper.createRotationalVector(-2.8f, 5.86f, -1.71f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.3541677f, AnimationHelper.createRotationalVector(-9.24f, 5.35f, -29.41f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createRotationalVector(1.18f, 17.86f, 5.39f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-10f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.25f, AnimationHelper.createRotationalVector(-10f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5f, AnimationHelper.createRotationalVector(-10f, 0f, 0f), Transformation.Interpolations.CUBIC)
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
        .build();

    public static final Animation PIGEON_GLIDE = Animation.Builder.create(2f).looping()
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
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -0.25f, 1f), Transformation.Interpolations.LINEAR)
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
            "head",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0.15f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "head",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-82.5f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing_open",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(2f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "left_wing_open",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-1.53f, 2.31f, -13.2f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(-1.37f, 3.59f, -9.94f), Transformation.Interpolations.CUBIC),
                new Keyframe(1.0834333f, AnimationHelper.createRotationalVector(-1.55f, 2.1f, -13.72f), Transformation.Interpolations.CUBIC),
                new Keyframe(1.5416767f, AnimationHelper.createRotationalVector(-1.65f, -0.09f, -19.25f), Transformation.Interpolations.CUBIC),
                new Keyframe(2f, AnimationHelper.createRotationalVector(-1.53f, 2.31f, -13.2f), Transformation.Interpolations.CUBIC)
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
                new Keyframe(2f, AnimationHelper.createTranslationalVector(0f, 0.25f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "right_wing_open",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-1.53f, -2.31f, 13.2f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.4583433f, AnimationHelper.createRotationalVector(-1.65f, -0.48f, 17.81f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.8343334f, AnimationHelper.createRotationalVector(-1.61f, -1.4f, 15.49f), Transformation.Interpolations.CUBIC),
                new Keyframe(1.1676667f, AnimationHelper.createRotationalVector(-1.61f, -1.4f, 15.49f), Transformation.Interpolations.CUBIC),
                new Keyframe(1.4583433f, AnimationHelper.createRotationalVector(-1.47f, -2.87f, 11.76f), Transformation.Interpolations.CUBIC),
                new Keyframe(2f, AnimationHelper.createRotationalVector(-1.53f, -2.31f, 13.2f), Transformation.Interpolations.CUBIC)
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
            "tail",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(2f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-10f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(-8f, 0f, 0f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.7083434f, AnimationHelper.createRotationalVector(-8.44f, 0.2f, 0.46f), Transformation.Interpolations.CUBIC),
                new Keyframe(0.9167666f, AnimationHelper.createRotationalVector(-9.18f, 0.41f, 0.91f), Transformation.Interpolations.CUBIC),
                new Keyframe(1.5416767f, AnimationHelper.createRotationalVector(-7.99f, -0.78f, -1.84f), Transformation.Interpolations.CUBIC),
                new Keyframe(2f, AnimationHelper.createRotationalVector(-10f, 0f, 0f), Transformation.Interpolations.CUBIC)
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
        .build();

    public static final Animation PIGEON_SIT = Animation.Builder.create(0f).looping()
        .addBoneAnimation(
            "root",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -1.7f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "body",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -0.25f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "body",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(35f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -0.25f, -0.35f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "neck",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-25f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(-0.3f, 0f, 0.4f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_wing",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-7.84f, -9.05f, -15.74f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_wing",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0.3f, -0.2f, 0.4f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_wing",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-7.69f, 6.79f, 11.82f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.TRANSLATE,
                new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0.5f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "tail",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-35f, 0f, 0f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "left_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-53.13f, -16.6f, -7.53f), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "right_leg",
            new Transformation(
                Transformation.Targets.ROTATE,
                new Keyframe(0f, AnimationHelper.createRotationalVector(-51.7f, 15.72f, 12.65f), Transformation.Interpolations.LINEAR)
            )
        )
        .build();
}