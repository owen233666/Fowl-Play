package aqario.fowlplay.core;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.registry.RegistryKeys;

public final class FowlPlayActivities {
    public static final DeferredRegister<Activity> ACTIVITIES = DeferredRegister.create(
        FowlPlay.ID,
        RegistryKeys.ACTIVITY
    );

    public static final RegistrySupplier<Activity> DELIVER = register("deliver");
    public static final RegistrySupplier<Activity> FLY = register("fly");
    public static final RegistrySupplier<Activity> FORAGE = register("forage");
    public static final RegistrySupplier<Activity> PERCH = register("perch");
    public static final RegistrySupplier<Activity> PICK_UP = register("pick_up");
    public static final RegistrySupplier<Activity> SOAR = register("soar");

    private static RegistrySupplier<Activity> register(String id) {
        return ACTIVITIES.register(id, () -> new Activity(id));
    }

    public static void init() {
        ACTIVITIES.register();
    }
}
