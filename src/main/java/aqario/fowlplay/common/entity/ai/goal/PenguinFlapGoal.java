package aqario.fowlplay.common.entity.ai.goal;

import net.minecraft.entity.ai.goal.Goal;
import aqario.fowlplay.common.entity.PenguinEntity;

import java.util.EnumSet;

public class PenguinFlapGoal extends Goal {
    private static final int ANIMATION_LENGTH = 32;
    private final PenguinEntity penguin;
    private int flapTime;
    private int nextFlapTime;

    public PenguinFlapGoal(PenguinEntity penguin) {
        this.penguin = penguin;
        this.setControls(EnumSet.of(Control.LOOK, Control.MOVE));
        nextFlapTime = penguin.age + (10 * 20 + penguin.getRandom().nextInt(10) * 20);
    }

    @Override
    public boolean canStart() {
        // Don't flap if not near player
        if (nextFlapTime > penguin.age || penguin.getDespawnCounter() >= 100 || penguin.getAnimation() != PenguinEntity.ANIMATION_IDLE) {
            return false;
        }
        return penguin.getRandom().nextInt(40) == 0;
    }

    @Override
    public void start() {
        flapTime = ANIMATION_LENGTH;
        penguin.setAnimation(PenguinEntity.ANIMATION_FLAP);
        nextFlapTime = penguin.age + (10 * 20 + penguin.getRandom().nextInt(10) * 20);
    }

    @Override
    public void stop() {
        penguin.setAnimation(PenguinEntity.ANIMATION_IDLE);
    }

    @Override
    public boolean shouldContinue() {
        return flapTime >= 0;
    }

    @Override
    public void tick() {
        flapTime--;
    }
}
