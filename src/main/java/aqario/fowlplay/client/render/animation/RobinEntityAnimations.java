package aqario.fowlplay.client.render.animation;

import net.minecraft.client.render.animation.Animation;
import net.minecraft.client.render.animation.AnimationKeyframe;
import net.minecraft.client.render.animation.Animator;
import net.minecraft.client.render.animation.PartAnimation;

public class RobinEntityAnimations {
	public static final Animation IDLE = Animation.Builder.withLength(0.0F).looping()
		.addPartAnimation("left_wing", new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
			new AnimationKeyframe(0.0F, Animator.rotate(0.2216F, -5.9524F, -2.3221F), PartAnimation.Interpolators.LINEAR)
		))
		.addPartAnimation("left_wing", new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
			new AnimationKeyframe(0.0F, Animator.translate(-0.2F, 0.0F, 0.0F), PartAnimation.Interpolators.LINEAR)
		))
		.addPartAnimation("right_wing", new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
			new AnimationKeyframe(0.0F, Animator.rotate(0.2216F, 5.9524F, 2.3221F), PartAnimation.Interpolators.LINEAR)
		))
		.addPartAnimation("right_wing", new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
			new AnimationKeyframe(0.0F, Animator.translate(0.2F, 0.0F, 0.0F), PartAnimation.Interpolators.LINEAR)
		))
		.build();


	public static final Animation WALK = Animation.Builder.withLength(0.0F).looping()
		.addPartAnimation("left_wing", new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
			new AnimationKeyframe(0.0F, Animator.rotate(0.2216F, -5.9524F, -2.3221F), PartAnimation.Interpolators.LINEAR)
		))
		.addPartAnimation("left_wing", new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
			new AnimationKeyframe(0.0F, Animator.translate(-0.2F, 0.0F, 0.0F), PartAnimation.Interpolators.LINEAR)
		))
		.addPartAnimation("right_wing", new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
			new AnimationKeyframe(0.0F, Animator.rotate(0.2216F, 5.9524F, 2.3221F), PartAnimation.Interpolators.LINEAR)
		))
		.addPartAnimation("right_wing", new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
			new AnimationKeyframe(0.0F, Animator.translate(0.2F, 0.0F, 0.0F), PartAnimation.Interpolators.LINEAR)
		))
		.build();


	public static final Animation FLOAT = Animation.Builder.withLength(0.0F).looping()
		.addPartAnimation("left_wing", new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
			new AnimationKeyframe(0.0F, Animator.rotate(0.2216F, -5.9524F, -2.3221F), PartAnimation.Interpolators.LINEAR)
		))
		.addPartAnimation("left_wing", new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
			new AnimationKeyframe(0.0F, Animator.translate(-0.2F, 0.0F, 0.0F), PartAnimation.Interpolators.LINEAR)
		))
		.addPartAnimation("right_wing", new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
			new AnimationKeyframe(0.0F, Animator.rotate(0.2216F, 5.9524F, 2.3221F), PartAnimation.Interpolators.LINEAR)
		))
		.addPartAnimation("right_wing", new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
			new AnimationKeyframe(0.0F, Animator.translate(0.2F, 0.0F, 0.0F), PartAnimation.Interpolators.LINEAR)
		))
		.build();


	public static final Animation FLY = Animation.Builder.withLength(0.0F).looping()
		.addPartAnimation("left_wing", new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
			new AnimationKeyframe(0.0F, Animator.rotate(0.2216F, -5.9524F, -2.3221F), PartAnimation.Interpolators.LINEAR)
		))
		.addPartAnimation("left_wing", new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
			new AnimationKeyframe(0.0F, Animator.translate(-0.2F, 0.0F, 0.0F), PartAnimation.Interpolators.LINEAR)
		))
		.addPartAnimation("right_wing", new PartAnimation(PartAnimation.AnimationTargets.ROTATE,
			new AnimationKeyframe(0.0F, Animator.rotate(0.2216F, 5.9524F, 2.3221F), PartAnimation.Interpolators.LINEAR)
		))
		.addPartAnimation("right_wing", new PartAnimation(PartAnimation.AnimationTargets.TRANSLATE,
			new AnimationKeyframe(0.0F, Animator.translate(0.2F, 0.0F, 0.0F), PartAnimation.Interpolators.LINEAR)
		))
		.build();
}