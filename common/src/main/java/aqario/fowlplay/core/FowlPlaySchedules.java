package aqario.fowlplay.core;

import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Schedule;
import net.minecraft.entity.ai.brain.ScheduleBuilder;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class FowlPlaySchedules {
    public static final Schedule FORAGER = register("forager")
        .withActivity(0, FowlPlayActivities.PERCH.get())
        .withActivity(1000, FowlPlayActivities.FORAGE.get())
        .withActivity(6000, FowlPlayActivities.PERCH.get())
        .withActivity(8000, FowlPlayActivities.FORAGE.get())
        .withActivity(11000, FowlPlayActivities.PERCH.get())
        .withActivity(12000, Activity.REST)
        .build();
    public static final Schedule HUNTER = register("hunter")
        .withActivity(0, FowlPlayActivities.PERCH.get())
        .withActivity(1000, FowlPlayActivities.HUNT.get())
        .withActivity(6000, FowlPlayActivities.SOAR.get())
        .withActivity(8000, FowlPlayActivities.HUNT.get())
        .withActivity(11000, FowlPlayActivities.PERCH.get())
        .withActivity(12000, Activity.REST)
        .build();

    private static ScheduleBuilder register(String id) {
        Schedule schedule = Registry.register(Registries.SCHEDULE, Identifier.of(FowlPlay.ID, id), new Schedule());
        return new ScheduleBuilder(schedule);
    }
}
