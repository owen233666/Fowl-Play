package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.ai.brain.sensor.*;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Supplier;

public final class FowlPlaySensorType {
    public static final DeferredRegister<SensorType<?>> SENSOR_TYPES = DeferredRegister.create(
        FowlPlay.ID,
        RegistryKeys.SENSOR_TYPE
    );
    public static final RegistrySupplier<SensorType<NearbyAdultsSensor<?>>> NEARBY_ADULTS = register("nearby_adults",
        NearbyAdultsSensor::new
    );
    public static final RegistrySupplier<SensorType<TemptingPlayerSensor>> TEMPTING_PLAYER = register("tempting_player",
        TemptingPlayerSensor::new
    );
    public static final RegistrySupplier<SensorType<AttackedSensor<?>>> ATTACKED = register("attacked",
        AttackedSensor::new
    );
    public static final RegistrySupplier<SensorType<AvoidTargetSensor<?>>> AVOID_TARGETS = register("avoid_targets",
        AvoidTargetSensor::new
    );
    public static final RegistrySupplier<SensorType<AttackTargetSensor<?>>> ATTACK_TARGETS = register("attack_targets",
        AttackTargetSensor::new
    );
    public static final RegistrySupplier<SensorType<PigeonSpecificSensor>> PIGEON_SPECIFIC_SENSOR = register("pigeon_specific_sensor",
        PigeonSpecificSensor::new
    );

    private static <U extends Sensor<?>> RegistrySupplier<SensorType<U>> register(String id, Supplier<U> factory) {
        return SENSOR_TYPES.register(id, () -> new SensorType<>(factory));
    }

    public static void init() {
        SENSOR_TYPES.register();
    }
}
