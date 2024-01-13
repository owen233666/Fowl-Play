package aqario.fowlplay.common.entity;

import net.minecraft.sound.SoundEvent;

public interface Songbird {
    default void playSongSound(BirdEntity bird) {
        SoundEvent soundEvent = this.getSongSound();
        if (soundEvent != null) {
            bird.playSound(soundEvent, 4.0F, bird.getSoundPitch());
        }
    }

    SoundEvent getSongSound();
}
