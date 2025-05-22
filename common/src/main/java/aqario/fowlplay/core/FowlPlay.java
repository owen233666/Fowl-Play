package aqario.fowlplay.core;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.*;
import aqario.fowlplay.common.world.gen.GullSpawner;
import aqario.fowlplay.common.world.gen.HawkSpawner;
import aqario.fowlplay.common.world.gen.PigeonSpawner;
import aqario.fowlplay.common.world.gen.SparrowSpawner;
import aqario.fowlplay.core.platform.PlatformHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FowlPlay {
    public static final Logger LOGGER = LoggerFactory.getLogger("Fowl Play");
    public static final String ID = "fowlplay";

    public static boolean isYACLLoaded() {
        return PlatformHelper.isModLoaded("yet_another_config_lib_v3");
    }

    public static boolean isDebugUtilsLoaded() {
        return PlatformHelper.isModLoaded("debugutils");
    }

    public static void init() {
        LOGGER.info("Loading Fowl Play");
        if(isYACLLoaded()) {
            FowlPlayConfig.load();
        }
        FowlPlayActivities.init();
        FowlPlayEntityType.init();
        ChickenVariant.init();
        DuckVariant.init();
        GullVariant.init();
        PigeonVariant.init();
        SparrowVariant.init();
        FowlPlayItems.init();
        FowlPlayMemoryModuleType.init();
        FowlPlayRegistries.init();
        FowlPlayRegistryKeys.init();
        FowlPlaySensorType.init();
        FowlPlaySoundEvents.init();
        FowlPlayTrackedDataHandlerRegistry.init();

        GullSpawner gullSpawner = new GullSpawner();
        HawkSpawner hawkSpawner = new HawkSpawner();
        PigeonSpawner pigeonSpawner = new PigeonSpawner();
        SparrowSpawner sparrowSpawner = new SparrowSpawner();

        ServerTickEvents.END_WORLD_TICK.register(world -> {
            gullSpawner.spawn(
                world,
                world.getServer().isMonsterSpawningEnabled(),
                world.getServer().shouldSpawnAnimals()
            );
            hawkSpawner.spawn(
                world,
                world.getServer().isMonsterSpawningEnabled(),
                world.getServer().shouldSpawnAnimals()
            );
            pigeonSpawner.spawn(
                world,
                world.getServer().isMonsterSpawningEnabled(),
                world.getServer().shouldSpawnAnimals()
            );
            sparrowSpawner.spawn(
                world,
                world.getServer().isMonsterSpawningEnabled(),
                world.getServer().shouldSpawnAnimals()
            );
        });
    }
}