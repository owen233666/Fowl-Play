package aqario.fowlplay.client.render.animation;

import net.minecraft.client.render.animation.Animation;
import net.minecraft.client.render.animation.AnimationKeyframe;
import net.minecraft.client.render.animation.Animator;
import net.minecraft.client.render.animation.PartAnimation;

public class PigeonEntityAnimations {
    public static final Animation PIGEON_IDLE = Animation.Builder.withLength(1.0f).looping()
        .addPartAnimation(
            "neck",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0f, 0.3f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "neck",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(2.5f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_wing",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(-0.2f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_wing",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(0.31f, -3.68f, -3.37f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "right_wing",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0.2f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "right_wing",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(0.31f, 3.68f, 3.37f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "tail",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-5f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-0.05f, -4.83f, -3.7f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "right_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-0.05f, 4.83f, 3.7f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .build();

    public static final Animation PIGEON_WALK = Animation.Builder.withLength(1.0f).looping()
        .addPartAnimation(
            "body",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.16766666f, Animator.translate(0f, 0.25f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.6766666f, Animator.translate(0f, 0.25f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "body",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(17.5f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.16766666f, Animator.rotate(19f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.rotate(17.5f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.6766666f, Animator.rotate(19f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.rotate(17.5f, 0f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "neck",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, -0.25f, -0.1f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "neck",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-15f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "left_wing",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(0.69f, -9.39f, -4.68f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "right_wing",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(0.69f, 9.39f, 4.68f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "tail",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-2.5f, 0f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "left_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.16766666f, Animator.translate(0f, 0f, 0.5f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.translate(0f, 0.5f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.8343334f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "left_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.16766666f, Animator.rotate(50f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.rotate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.8343334f, Animator.rotate(-25f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.rotate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "right_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0.5f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.3433333f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.6766666f, Animator.translate(0f, 0f, 0.5f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.translate(0f, 0.5f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .addPartAnimation(
            "right_leg",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.3433333f, Animator.rotate(-25f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.rotate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.6766666f, Animator.rotate(50f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.rotate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .build();

    public static final Animation PIGEON_FLY = Animation.Builder.withLength(0f)
        .build();

    public static final Animation PIGEON_FLOAT = Animation.Builder.withLength(0f).looping()
        .build();
}