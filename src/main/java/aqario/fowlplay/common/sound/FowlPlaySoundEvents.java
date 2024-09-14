package aqario.fowlplay.common.sound;

import aqario.fowlplay.common.FowlPlay;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class FowlPlaySoundEvents {
    public static final SoundEvent ENTITY_BLUE_JAY_CALL = register("entity.blue_jay.call");

    public static final SoundEvent ENTITY_CARDINAL_SONG = register("entity.cardinal.song");

    public static final SoundEvent ENTITY_PENGUIN_AMBIENT = register("entity.penguin.ambient");
    public static final SoundEvent ENTITY_PENGUIN_BABY_AMBIENT = register("entity.penguin_baby.ambient");
    public static final SoundEvent ENTITY_PENGUIN_SWIM = register("entity.penguin.swim");
    public static final SoundEvent ENTITY_PENGUIN_HURT = register("entity.penguin.hurt");
    public static final SoundEvent ENTITY_PENGUIN_DEATH = register("entity.penguin.death");

    public static final SoundEvent ENTITY_PIGEON_AMBIENT = register("entity.pigeon.ambient");
    public static final SoundEvent ENTITY_PIGEON_CALL = register("entity.pigeon.call");

    public static final SoundEvent ENTITY_ROBIN_AMBIENT = register("entity.robin.ambient");
    public static final SoundEvent ENTITY_ROBIN_SONG = register("entity.robin.song");
    public static final SoundEvent ENTITY_ROBIN_HURT = register("entity.robin.hurt");

    public static final SoundEvent ENTITY_SEAGULL_AMBIENT = register("entity.seagull.ambient");

    private static SoundEvent register(String id) {
        Identifier identifier = Identifier.of(FowlPlay.ID, id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.createVariableRangeEvent(identifier));
    }

    public static void init() {
    }
}
