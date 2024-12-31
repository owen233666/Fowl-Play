package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.FowlPlay;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public final class FowlPlaySensorType {
    public static final SensorType<NearestVisibleAdultsSensor> NEAREST_ADULTS = register("nearest_adults",
        NearestVisibleAdultsSensor::new
    );
    public static final SensorType<TemptingPlayerSensor> TEMPTING_PLAYER = register("tempting_player",
        TemptingPlayerSensor::new
    );
    public static final SensorType<HuntTargetSensor> HUNT_TARGETS = register("hunt_targets",
        HuntTargetSensor::new
    );
    public static final SensorType<PigeonSpecificSensor> PIGEON_SPECIFIC_SENSOR = register("pigeon_specific_sensor",
        PigeonSpecificSensor::new
    );
    public static final SensorType<FlyingStateSensor> IS_FLYING = register("is_flying",
        FlyingStateSensor::new
    );

    private static <U extends Sensor<?>> SensorType<U> register(String id, Supplier<U> factory) {
        return Registry.register(Registries.SENSOR_TYPE, Identifier.of(FowlPlay.ID, id), new SensorType<>(factory));
    }

    public static void init() {
    }
}
