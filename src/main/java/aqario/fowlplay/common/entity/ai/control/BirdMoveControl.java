package aqario.fowlplay.common.entity.ai.control;

import aqario.fowlplay.common.entity.BirdEntity;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.mob.MobEntity;

public class BirdMoveControl extends MoveControl {
    public BirdMoveControl(BirdEntity bird) {
        super(bird);
    }
}
