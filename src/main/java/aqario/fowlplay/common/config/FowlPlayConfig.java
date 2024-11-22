package aqario.fowlplay.common.config;

import aqario.fowlplay.common.FowlPlay;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

public class FowlPlayConfig {
    public static ConfigClassHandler<FowlPlayConfig> HANDLER = ConfigClassHandler.createBuilder(FowlPlayConfig.class)
        .id(Identifier.of(FowlPlay.ID, "config"))
        .serializer(config -> GsonConfigSerializerBuilder.create(config)
            .setPath(FabricLoader.getInstance().getConfigDir().resolve(FowlPlay.ID + ".json5"))
            .setJson5(true)
            .build())
        .build();

    // Visual

    @SerialEntry
    public static boolean customChickenModel = true;

    // Audio

    @SerialEntry
    public static int blueJayCall = 10;
    @SerialEntry
    public static int blueJaySong = 2;

    @SerialEntry
    public static int cardinalCall = 3;
    @SerialEntry
    public static int cardinalSong = 1;

    @SerialEntry
    public static int chickadeeCall = 5;
    @SerialEntry
    public static int chickadeeSong = 1;

    @SerialEntry
    public static int duckCall = 5;
    @SerialEntry
    public static int duckSong = 1;

    @SerialEntry
    public static int gullCall = 5;
    @SerialEntry
    public static int gullSong = 1;

    @SerialEntry
    public static int penguinCall = 5;
    @SerialEntry
    public static int penguinSong = 1;

    @SerialEntry
    public static int pigeonCall = 5;
    @SerialEntry
    public static int pigeonSong = 1;

    @SerialEntry
    public static int ravenCall = 5;
    @SerialEntry
    public static int ravenSong = 1;

    @SerialEntry
    public static int robinCall = 5;
    @SerialEntry
    public static int robinSong = 1;

    @SerialEntry
    public static int sparrowCall = 5;
    @SerialEntry
    public static int sparrowSong = 1;

    // Spawning

    // Blue Jay

    @SerialEntry
    public static int blueJaySpawnWeight = 25;
    @SerialEntry
    public static int blueJayMinGroupSize = 1;
    @SerialEntry
    public static int blueJayMaxGroupSize = 2;

    // Cardinal

    @SerialEntry
    public static int cardinalSpawnWeight = 35;
    @SerialEntry
    public static int cardinalMinGroupSize = 1;
    @SerialEntry
    public static int cardinalMaxGroupSize = 2;

    // Chickadee

    @SerialEntry
    public static int chickadeeSpawnWeight = 50;
    @SerialEntry
    public static int chickadeeMinGroupSize = 3;
    @SerialEntry
    public static int chickadeeMaxGroupSize = 5;

    // Duck

    @SerialEntry
    public static int duckSpawnWeight = 30;
    @SerialEntry
    public static int duckMinGroupSize = 6;
    @SerialEntry
    public static int duckMaxGroupSize = 12;

    // Gull

    @SerialEntry
    public static int gullSpawnWeight = 30;
    @SerialEntry
    public static int gullMinGroupSize = 8;
    @SerialEntry
    public static int gullMaxGroupSize = 12;

    // Penguin

    @SerialEntry
    public static int penguinSpawnWeight = 1;
    @SerialEntry
    public static int penguinMinGroupSize = 16;
    @SerialEntry
    public static int penguinMaxGroupSize = 24;

    // Pigeon

    @SerialEntry
    public static int pigeonSpawnWeight = 20;
    @SerialEntry
    public static int pigeonMinGroupSize = 4;
    @SerialEntry
    public static int pigeonMaxGroupSize = 8;

    // Raven

    @SerialEntry
    public static int ravenSpawnWeight = 20;
    @SerialEntry
    public static int ravenMinGroupSize = 1;
    @SerialEntry
    public static int ravenMaxGroupSize = 3;

    // Robin

    @SerialEntry
    public static int robinSpawnWeight = 50;
    @SerialEntry
    public static int robinMinGroupSize = 3;
    @SerialEntry
    public static int robinMaxGroupSize = 5;

    // Sparrow

    @SerialEntry
    public static int sparrowSpawnWeight = 75;
    @SerialEntry
    public static int sparrowMinGroupSize = 6;
    @SerialEntry
    public static int sparrowMaxGroupSize = 10;
}
