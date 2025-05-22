package aqario.fowlplay.core;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(FowlPlay.ID)
public final class FowlPlayNeoForge {
    public FowlPlayNeoForge(IEventBus bus) {
        // Run our common setup.
        FowlPlayItems.register(bus);

        bus.addListener(this::addSpawnEggsToCreativeMenu);

        FowlPlay.init();

        // rendering

        // world generation

        // pigeon spawning
    }

    private void addSpawnEggsToCreativeMenu(BuildCreativeModeTabContentsEvent event) {
    }
}
