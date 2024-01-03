package aqario.fowlplay.common.sound;

import aqario.fowlplay.common.FowlPlay;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FowlPlaySoundEvents {
    public static final SoundEvent ENTITY_PENGUIN_IDLE = register("entity.penguin.idle");
    public static final SoundEvent ENTITY_PENGUIN_BABY_IDLE = register("entity.penguin_baby.death");
    public static final SoundEvent ENTITY_PENGUIN_SWIM = register("entity.penguin.swim");
    public static final SoundEvent ENTITY_PENGUIN_HURT = register("entity.penguin.hurt");
    public static final SoundEvent ENTITY_PENGUIN_DEATH = register("entity.penguin.death");

    public static final SoundEvent ENTITY_SEAGULL_IDLE = register("entity.seagull.idle");

    private static SoundEvent register(String id) {
        Identifier identifier = new Identifier(FowlPlay.ID, id);
        return Registry.register(Registry.SOUND_EVENT, identifier, new SoundEvent(identifier));
    }

    public static void init() {
    }
}
