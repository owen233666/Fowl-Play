package aqario.fowlplay.common.entity;

import net.minecraft.entity.SpawnGroup;

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
