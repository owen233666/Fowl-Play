package aqario.fowlplay.datagen;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlaySoundEvents;
import aqario.fowlplay.datagen.provider.SoundDefinition;
import aqario.fowlplay.datagen.provider.SoundDefinitionsProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class FowlPlaySoundDefinitionsGen extends SoundDefinitionsProvider {
    protected FowlPlaySoundDefinitionsGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, FowlPlay.ID, registryLookup);
    }

    @Override
    public void generateSounds() {
        this.addVariousVanilla(FowlPlaySoundEvents.ENTITY_BIRD_EAT, "mob/parrot/eat", 3);

        this.addVarious(FowlPlaySoundEvents.ENTITY_BLUE_JAY_CALL, "mob/blue_jay/call", 6);
        this.addVarious(FowlPlaySoundEvents.ENTITY_BLUE_JAY_HURT, "mob/blue_jay/call", 6);

        this.addVarious(FowlPlaySoundEvents.ENTITY_CARDINAL_CALL, "mob/cardinal/call", 2);
        this.addVarious(FowlPlaySoundEvents.ENTITY_CARDINAL_SONG, "mob/cardinal/song", 8);
        this.addVarious(FowlPlaySoundEvents.ENTITY_CARDINAL_HURT, "mob/cardinal/call", 2);

        this.addVarious(FowlPlaySoundEvents.ENTITY_CHICKADEE_CALL, "mob/chickadee/call", 9);
        this.addVarious(FowlPlaySoundEvents.ENTITY_CHICKADEE_SONG, "mob/chickadee/song", 6);
        this.addVarious(FowlPlaySoundEvents.ENTITY_CHICKADEE_HURT, "mob/chickadee/call", 2);

        this.addVarious(FowlPlaySoundEvents.ENTITY_CROW_CALL, "mob/crow/call", 4);
        this.addVarious(FowlPlaySoundEvents.ENTITY_CROW_HURT, "mob/crow/hurt", 2);

        this.addVarious(FowlPlaySoundEvents.ENTITY_DUCK_CALL, "mob/duck/call", 2);
        this.addVarious(FowlPlaySoundEvents.ENTITY_DUCK_HURT, "mob/duck/call", 2);

        this.addVarious(FowlPlaySoundEvents.ENTITY_GULL_CALL, "mob/gull/call", 4);
        this.addVarious(FowlPlaySoundEvents.ENTITY_GULL_LONG_CALL, "mob/gull/long_call", 3);
        this.addVarious(FowlPlaySoundEvents.ENTITY_GULL_HURT, "mob/gull/hurt", 2);

        this.addVarious(FowlPlaySoundEvents.ENTITY_HAWK_CALL, "mob/hawk/call", 5);
        this.addVarious(FowlPlaySoundEvents.ENTITY_HAWK_HURT, "mob/hawk/call", 2);

        this.addVarious(FowlPlaySoundEvents.ENTITY_PENGUIN_CALL, "mob/penguin/call", 3);
        this.addOne(FowlPlaySoundEvents.ENTITY_PENGUIN_BABY_CALL, "mob/penguin/baby_call");
        this.addVarious(FowlPlaySoundEvents.ENTITY_PENGUIN_SWIM, "mob/penguin/swim/swim", 5);
        this.addOne(FowlPlaySoundEvents.ENTITY_PENGUIN_HURT, "mob/penguin/hurt");

        this.addVarious(FowlPlaySoundEvents.ENTITY_PIGEON_CALL, "mob/pigeon/call", 3);
        this.addVarious(FowlPlaySoundEvents.ENTITY_PIGEON_SONG, "mob/pigeon/song", 1);

        this.addVarious(FowlPlaySoundEvents.ENTITY_RAVEN_CALL, "mob/raven/call", 4);
        this.addVarious(FowlPlaySoundEvents.ENTITY_RAVEN_HURT, "mob/raven/hurt", 2);

        this.addVarious(FowlPlaySoundEvents.ENTITY_ROBIN_CALL, "mob/robin/call", 6);
        this.addVarious(FowlPlaySoundEvents.ENTITY_ROBIN_SONG, "mob/robin/song", 4);
        this.addVarious(FowlPlaySoundEvents.ENTITY_ROBIN_HURT, "mob/robin/hurt", 2);

        this.addVarious(FowlPlaySoundEvents.ENTITY_SPARROW_CALL, "mob/sparrow/call", 5);
        this.addVarious(FowlPlaySoundEvents.ENTITY_SPARROW_SONG, "mob/sparrow/song", 4);
        this.addVarious(FowlPlaySoundEvents.ENTITY_SPARROW_HURT, "mob/sparrow/call", 3, 5);
    }

    private void addVarious(SoundEvent soundEvent, String location, int start, int end) {
        SoundDefinition definition = SoundDefinition.builder();
        for (int i = start; i <= end; i++) {
            definition.with(sound(Identifier.of(FowlPlay.ID, location + i)));
        }
        definition.subtitle("subtitles." + soundEvent.getId().getPath());
        this.add(soundEvent, definition);
    }

    private void addVarious(SoundEvent soundEvent, String location, int variations) {
        this.addVarious(soundEvent, location, 1, variations);
    }

    private void addOne(SoundEvent soundEvent, String location) {
        SoundDefinition definition = SoundDefinition.builder()
            .with(sound(Identifier.of(FowlPlay.ID, location)))
            .subtitle("subtitles." + soundEvent.getId().getPath());
        this.add(soundEvent, definition);
    }

    private void addVariousVanilla(SoundEvent soundEvent, String location, int start, int end) {
        SoundDefinition definition = SoundDefinition.builder();
        for (int i = start; i <= end; i++) {
            definition.with(sound(Identifier.ofVanilla(location + i)));
        }
        definition.subtitle("subtitles." + soundEvent.getId().getPath());
        this.add(soundEvent, definition);
    }

    private void addVariousVanilla(SoundEvent soundEvent, String location, int variations) {
        this.addVariousVanilla(soundEvent, location, 1, variations);
    }

    private void addVanilla(SoundEvent soundEvent, String location) {
        SoundDefinition definition = SoundDefinition.builder()
            .with(sound(Identifier.ofVanilla(location)))
            .subtitle("subtitles." + soundEvent.getId().getPath());
        this.add(soundEvent, definition);
    }
}
