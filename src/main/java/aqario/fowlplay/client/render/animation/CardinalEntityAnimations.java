package aqario.fowlplay.client.render.animation;

import net.minecraft.client.render.animation.Animation;
import net.minecraft.client.render.animation.AnimationKeyframe;
import net.minecraft.client.render.animation.Animator;
import net.minecraft.client.render.animation.PartAnimation;

public class CardinalEntityAnimations {
    public static final Animation CARDINAL_IDLE = Animation.Builder.withLength(0f).looping()
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
                new AnimationKeyframe(0f, Animator.rotate(0.22f, -5.95f, -2.32f), PartAnimation.Interpolations.LINEAR)
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
                new AnimationKeyframe(0f, Animator.rotate(0.22f, 5.95f, 2.32f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "body",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-10f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "head",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0.1f, -0.25f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "head",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(12.5f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
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

    public static final Animation CARDINAL_WALK = Animation.Builder.withLength(1f).looping()
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
            "head",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, -0.15f, -0.2f), PartAnimation.Interpolations.LINEAR)
            )
        )
        .addPartAnimation(
            "head",
            new PartAnimation(
                PartAnimation.AnimationTargets.ROTATE,
                new AnimationKeyframe(0f, Animator.rotate(-15f, 0f, 0f), PartAnimation.Interpolations.LINEAR)
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
        .addPartAnimation(
            "root",
            new PartAnimation(
                PartAnimation.AnimationTargets.TRANSLATE,
                new AnimationKeyframe(0f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.16766666f, Animator.translate(0f, 0.25f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.5f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(0.6766666f, Animator.translate(0f, 0.25f, 0f), PartAnimation.Interpolations.SPLINE),
                new AnimationKeyframe(1f, Animator.translate(0f, 0f, 0f), PartAnimation.Interpolations.SPLINE)
            )
        )
        .build();

    public static final Animation CARDINAL_FLY = Animation.Builder.withLength(0f)
        .build();

    public static final Animation CARDINAL_FLOAT = Animation.Builder.withLength(0f).looping()
        .build();
}