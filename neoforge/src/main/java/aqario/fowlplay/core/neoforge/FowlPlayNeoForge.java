package aqario.fowlplay.core.neoforge;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.platform.neoforge.PlatformHelperImpl;
import net.minecraft.item.ItemGroup;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@Mod(FowlPlay.ID)
public final class FowlPlayNeoForge {
    public FowlPlayNeoForge(IEventBus bus) {
        bus.addListener(this::registerRegistries);
        bus.addListener(this::setup);
        bus.addListener(this::addSpawnEggsToCreativeMenu);
        PlatformHelperImpl.ITEMS.register(bus);
        PlatformHelperImpl.TRACKED_DATA_HANDLERS.register(bus);
    }

    private void registerRegistries(NewRegistryEvent event) {
        PlatformHelperImpl.REGISTRIES.forEach(event::register);
    }

    private void setup(FMLCommonSetupEvent event) {
        FowlPlay.init();
    }

    private void addSpawnEggsToCreativeMenu(BuildCreativeModeTabContentsEvent event) {
        PlatformHelperImpl.ITEM_TO_GROUPS.forEach(((item, group) -> {
            if(event.getTabKey().equals(group)) {
                event.add(item, ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
            }
        }));
    }
}
