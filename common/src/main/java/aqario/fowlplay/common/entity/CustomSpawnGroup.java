package aqario.fowlplay.common.entity;

import net.minecraft.entity.SpawnGroup;

//public final class CustomSpawnGroup {
//    public static SpawnGroup AMBIENT_BIRDS;
//    public static SpawnGroup BIRDS;
//
//    public static final String AMBIENT_BIRDS_ENUM_NAME = "AMBIENT_BIRDS";
//    public static final String AMBIENT_BIRDS_NAME = "ambient_birds";
//    public static final int AMBIENT_BIRDS_SPAWN_CAP = 15;
//    public static final boolean AMBIENT_BIRDS_PEACEFUL = true;
//    public static final boolean AMBIENT_BIRDS_RARE = false;
//    public static final int AMBIENT_BIRDS_IMMEDIATE_DESPAWN_RANGE = 96;
//
//    public static final String BIRDS_ENUM_NAME = "BIRDS";
//    public static final String BIRDS_NAME = "birds";
//    public static final int BIRDS_SPAWN_CAP = 20;
//    public static final boolean BIRDS_PEACEFUL = true;
//    public static final boolean BIRDS_RARE = false;
//    public static final int BIRDS_IMMEDIATE_DESPAWN_RANGE = 96;
//
//    public static SpawnGroup getAmbientBirdsCategory() {
//        return AMBIENT_BIRDS;
//    }
//
//    public static SpawnGroup getBirdsCategory() {
//        return BIRDS;
//    }
//
//    private CustomSpawnGroup() {
//    }
//}
public enum CustomSpawnGroup {
    AMBIENT_BIRDS("ambient_birds", 15, true, false, 96),
    BIRDS("birds", 20, true, false, 96);

    public SpawnGroup spawnGroup;
    public final String name;
    public final int spawnCap;
    public final boolean peaceful;
    public final boolean rare;
    public final int immediateDespawnRange;

    CustomSpawnGroup(String name, int spawnCap, boolean peaceful, boolean rare, int immediateDespawnRange) {
        this.name = name;
        this.spawnCap = spawnCap;
        this.peaceful = peaceful;
        this.rare = rare;
        this.immediateDespawnRange = immediateDespawnRange;
    }
}
