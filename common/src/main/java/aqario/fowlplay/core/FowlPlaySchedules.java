package aqario.fowlplay.core;

import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.entity.ai.brain.Activity;
import net.tslat.smartbrainlib.api.core.schedule.SmartBrainSchedule;

import java.util.function.Supplier;

public class FowlPlaySchedules {
    public static final Supplier<SmartBrainSchedule> FORAGER = register("forager", new SmartBrainSchedule()
        .activityAt(0, FowlPlayActivities.PERCH.get())
        .activityAt(1000, FowlPlayActivities.FORAGE.get())
        .activityAt(6000, FowlPlayActivities.PERCH.get())
        .activityAt(8000, FowlPlayActivities.FORAGE.get())
        .activityAt(11000, FowlPlayActivities.PERCH.get())
        .activityAt(12000, Activity.REST)
        .activityAt(23000, FowlPlayActivities.PERCH.get())
    );
    public static final Supplier<SmartBrainSchedule> HUNTER = register("hunter", new SmartBrainSchedule()
        .activityAt(0, FowlPlayActivities.PERCH.get())
        .activityAt(1000, FowlPlayActivities.HUNT.get())
        .activityAt(6000, FowlPlayActivities.SOAR.get())
        .activityAt(8000, FowlPlayActivities.HUNT.get())
        .activityAt(11000, FowlPlayActivities.PERCH.get())
        .activityAt(12000, Activity.REST)
        .activityAt(23000, FowlPlayActivities.PERCH.get())
    );

    private static Supplier<SmartBrainSchedule> register(String id, SmartBrainSchedule schedule) {
        return PlatformHelper.registerSchedule(id, () -> schedule);
    }

    public static void init() {
    }
}
