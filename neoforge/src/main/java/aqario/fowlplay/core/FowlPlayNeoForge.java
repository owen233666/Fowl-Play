package aqario.fowlplay.core;

import aqario.fowlplay.core.platform.neoforge.PlatformHelperImpl;
import net.minecraft.item.ItemGroup;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(FowlPlay.ID)
public final class FowlPlayNeoForge {
    public FowlPlayNeoForge(IEventBus bus) {
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
}
