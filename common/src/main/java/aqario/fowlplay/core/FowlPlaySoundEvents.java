package aqario.fowlplay.core;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public final class FowlPlaySoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(
        FowlPlay.ID,
        RegistryKeys.SOUND_EVENT
    );
    public static final DeferredSupplier<SoundEvent> ENTITY_BIRD_EAT = register("entity.bird.eat");

    public static final DeferredSupplier<SoundEvent> ENTITY_BLUE_JAY_CALL = register("entity.blue_jay.call");
    public static final DeferredSupplier<SoundEvent> ENTITY_BLUE_JAY_HURT = register("entity.blue_jay.hurt");

    public static final DeferredSupplier<SoundEvent> ENTITY_CARDINAL_CALL = register("entity.cardinal.call");
    public static final DeferredSupplier<SoundEvent> ENTITY_CARDINAL_SONG = register("entity.cardinal.song");
    public static final DeferredSupplier<SoundEvent> ENTITY_CARDINAL_HURT = register("entity.cardinal.hurt");

    public static final DeferredSupplier<SoundEvent> ENTITY_CHICKADEE_CALL = register("entity.chickadee.call");
    public static final DeferredSupplier<SoundEvent> ENTITY_CHICKADEE_SONG = register("entity.chickadee.song");
    public static final DeferredSupplier<SoundEvent> ENTITY_CHICKADEE_HURT = register("entity.chickadee.hurt");

    public static final DeferredSupplier<SoundEvent> ENTITY_CROW_CALL = register("entity.crow.call");
    public static final DeferredSupplier<SoundEvent> ENTITY_CROW_HURT = register("entity.crow.hurt");

    public static final DeferredSupplier<SoundEvent> ENTITY_DUCK_CALL = register("entity.duck.call");
    public static final DeferredSupplier<SoundEvent> ENTITY_DUCK_HURT = register("entity.duck.hurt");

    public static final DeferredSupplier<SoundEvent> ENTITY_GULL_CALL = register("entity.gull.call");
    public static final DeferredSupplier<SoundEvent> ENTITY_GULL_LONG_CALL = register("entity.gull.long_call");
    public static final DeferredSupplier<SoundEvent> ENTITY_GULL_HURT = register("entity.gull.hurt");

    public static final DeferredSupplier<SoundEvent> ENTITY_HAWK_CALL = register("entity.hawk.call");
    public static final DeferredSupplier<SoundEvent> ENTITY_HAWK_HURT = register("entity.hawk.hurt");

    public static final DeferredSupplier<SoundEvent> ENTITY_PENGUIN_CALL = register("entity.penguin.call");
    public static final DeferredSupplier<SoundEvent> ENTITY_PENGUIN_BABY_CALL = register("entity.penguin_baby.call");
    public static final DeferredSupplier<SoundEvent> ENTITY_PENGUIN_SWIM = register("entity.penguin.swim");
    public static final DeferredSupplier<SoundEvent> ENTITY_PENGUIN_HURT = register("entity.penguin.hurt");

    public static final DeferredSupplier<SoundEvent> ENTITY_PIGEON_CALL = register("entity.pigeon.call");
    public static final DeferredSupplier<SoundEvent> ENTITY_PIGEON_SONG = register("entity.pigeon.song");

    public static final DeferredSupplier<SoundEvent> ENTITY_RAVEN_CALL = register("entity.raven.call");
    public static final DeferredSupplier<SoundEvent> ENTITY_RAVEN_HURT = register("entity.raven.hurt");

    public static final DeferredSupplier<SoundEvent> ENTITY_ROBIN_CALL = register("entity.robin.call");
    public static final DeferredSupplier<SoundEvent> ENTITY_ROBIN_SONG = register("entity.robin.song");
    public static final DeferredSupplier<SoundEvent> ENTITY_ROBIN_HURT = register("entity.robin.hurt");

    public static final DeferredSupplier<SoundEvent> ENTITY_SPARROW_CALL = register("entity.sparrow.call");
    public static final DeferredSupplier<SoundEvent> ENTITY_SPARROW_SONG = register("entity.sparrow.song");
    public static final DeferredSupplier<SoundEvent> ENTITY_SPARROW_HURT = register("entity.sparrow.hurt");

    private static DeferredSupplier<SoundEvent> register(String id) {
        return SOUND_EVENTS.register(id, () -> SoundEvent.of(Identifier.of(FowlPlay.ID, id)));
    }

    public static void init() {
        SOUND_EVENTS.register();
    }
}
