package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.entity.PigeonEntity;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import aqario.fowlplay.core.FowlPlaySensorType;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.server.world.ServerWorld;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.PredicateSensor;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;
import java.util.UUID;

public class PigeonSpecificSensor extends PredicateSensor<UUID, PigeonEntity> {
    private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(
        FowlPlayMemoryModuleType.RECIPIENT
    );

    public PigeonSpecificSensor() {
        super(
            (uuid, pigeon) -> pigeon.isTamed()
                && pigeon.getRecipientUuid() != null
                && pigeon.getWorld().getPlayerByUuid(pigeon.getRecipientUuid()) != null
        );
    }

    @Override
    public List<MemoryModuleType<?>> memoriesUsed() {
        return MEMORIES;
    }

    @Override
    public SensorType<? extends ExtendedSensor<?>> type() {
        return FowlPlaySensorType.PIGEON_SPECIFIC_SENSOR;
    }

    @Override
    protected void sense(ServerWorld world, PigeonEntity pigeon) {
        if (this.predicate().test(null, pigeon)) {
            BrainUtils.setMemory(pigeon, FowlPlayMemoryModuleType.RECIPIENT, pigeon.getRecipientUuid());
        }
        else {
            BrainUtils.clearMemory(pigeon, FowlPlayMemoryModuleType.RECIPIENT);
        }
    }
}
