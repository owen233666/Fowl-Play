package aqario.fowlplay.core.neoforge;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistries;
import aqario.fowlplay.core.platform.neoforge.PlatformHelperImpl;
import net.minecraft.item.ItemGroup;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@Mod(FowlPlay.ID)
public final class FowlPlayNeoForge {
    public FowlPlayNeoForge(IEventBus bus) {
        bus.addListener(this::registerRegistries);
        FowlPlay.init();
        bus.addListener(this::addSpawnEggsToCreativeMenu);
    }

    private void addSpawnEggsToCreativeMenu(BuildCreativeModeTabContentsEvent event) {
        PlatformHelperImpl.ITEM_TO_GROUPS.forEach(((item, group) -> {
            if(event.getTabKey().equals(group)) {
                event.add(item, ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
            }
        }));
    }

    void registerRegistries(NewRegistryEvent event) {
        System.out.println("REGISTERING REGISTRIES");
        event.register(FowlPlayRegistries.CHICKEN_VARIANT);
        event.register(FowlPlayRegistries.DUCK_VARIANT);
        event.register(FowlPlayRegistries.GULL_VARIANT);
        event.register(FowlPlayRegistries.PIGEON_VARIANT);
        event.register(FowlPlayRegistries.SPARROW_VARIANT);
    }
}
