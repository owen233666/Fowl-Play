package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.tags.FowlPlayEntityTypeTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.NearestVisibleLivingEntitySensor;
import net.minecraft.entity.ai.brain.sensor.Sensor;

public class PenguinAttackablesSensor extends NearestVisibleLivingEntitySensor {
    public static final float TARGET_DETECTION_DISTANCE = 32.0F;

    @Override
    protected boolean matches(LivingEntity penguin, LivingEntity target) {
        return this.isInRange(penguin, target)
            && target.isInsideWaterOrBubbleColumn()
            && this.canHunt(penguin, target)
            && Sensor.testAttackableTargetPredicate(penguin, target);
    }

    private boolean canHunt(LivingEntity penguin, LivingEntity target) {
        return !penguin.getBrain().hasMemoryModule(MemoryModuleType.HAS_HUNTING_COOLDOWN) && target.getType().isIn(FowlPlayEntityTypeTags.PENGUIN_HUNT_TARGETS);
    }

    private boolean isInRange(LivingEntity penguin, LivingEntity target) {
        return target.squaredDistanceTo(penguin) <= TARGET_DETECTION_DISTANCE * TARGET_DETECTION_DISTANCE;
    }

    @Override
    protected MemoryModuleType<LivingEntity> getOutputMemoryModule() {
        return MemoryModuleType.NEAREST_ATTACKABLE;
    }
}