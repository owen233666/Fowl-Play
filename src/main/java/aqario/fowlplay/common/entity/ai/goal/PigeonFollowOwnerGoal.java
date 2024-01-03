package aqario.fowlplay.common.entity.ai.goal;

import aqario.fowlplay.common.entity.PigeonEntity;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.passive.TameableEntity;

public class PigeonFollowOwnerGoal extends FollowOwnerGoal {
    private final TameableEntity tameable;

    public PigeonFollowOwnerGoal(TameableEntity tameable, double speed, float minDistance, float maxDistance, boolean leavesAllowed) {
        super(tameable, speed, minDistance, maxDistance, leavesAllowed);
        this.tameable = tameable;
    }

    @Override
    public boolean canStart() {
        return (!((Boolean)this.tameable.getDataTracker().get(PigeonEntity.DELIVERING)) && super.canStart());
    }

    @Override
    public boolean shouldContinue() {
        if (this.tameable.getDataTracker().get(PigeonEntity.DELIVERING))
            return false;
        return super.shouldContinue();
    }
}
