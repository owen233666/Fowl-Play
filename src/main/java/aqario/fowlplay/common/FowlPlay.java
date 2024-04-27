package aqario.fowlplay.common;

import aqario.fowlplay.common.entity.FowlPlayEntityType;
import aqario.fowlplay.common.item.FowlPlayItems;
import aqario.fowlplay.common.sound.FowlPlaySoundEvents;
import aqario.fowlplay.common.world.gen.FowlPlayWorldGen;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FowlPlay implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Fowl Play");
    public static final String ID = "fowlplay";

    @Override
    public void onInitialize(ModContainer mod) {
        LOGGER.info("Loading {} {}", mod.metadata().name(), mod.metadata().version());
        FowlPlayEntityType.init();
        FowlPlayItems.init();
        FowlPlaySoundEvents.init();
        FowlPlayWorldGen.init();
    }
}