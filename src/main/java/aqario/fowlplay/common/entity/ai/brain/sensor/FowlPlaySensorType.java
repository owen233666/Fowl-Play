package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.ai.brain.PenguinBrain;
import net.minecraft.entity.ai.brain.sensor.AxolotlAttackablesSensor;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.sensor.TemptationsSensor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class FowlPlaySensorType {
    public static final SensorType<TemptationsSensor> PENGUIN_TEMPTATIONS = register("penguin_temptations", () -> new TemptationsSensor(PenguinBrain.getTemptIngredient()));
    public static final SensorType<AxolotlAttackablesSensor> PENGUIN_ATTACKABLES = register("penguin_attackables", AxolotlAttackablesSensor::new);
    public static final SensorType<NearestVisiblePenguinSensor> NEAREST_PENGUINS = register("nearest_penguins", NearestVisiblePenguinSensor::new);

    private static <U extends Sensor<?>> SensorType<U> register(String id, Supplier<U> factory) {
        return Registry.register(Registry.SENSOR_TYPE, new Identifier(FowlPlay.ID, id), new SensorType<>(factory));
    }
}
