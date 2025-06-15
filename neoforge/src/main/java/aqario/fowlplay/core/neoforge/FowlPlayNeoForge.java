package aqario.fowlplay.core.neoforge;

import aqario.fowlplay.client.neoforge.FowlPlayNeoForgeClient;
import aqario.fowlplay.common.config.YACLIntegration;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayItems;
import aqario.fowlplay.core.platform.neoforge.PlatformHelperImpl;
import net.minecraft.item.ItemGroups;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@Mod(FowlPlay.ID)
public final class FowlPlayNeoForge {
    public FowlPlayNeoForge(ModContainer mod, IEventBus modBus) {
        IEventBus bus = NeoForge.EVENT_BUS;

        FowlPlay.init();

        if(FMLEnvironment.dist == Dist.CLIENT) {
            FowlPlayNeoForgeClient.init(modBus);
        }

        modBus.addListener(FowlPlayNeoForge::onNewRegistry);
        modBus.addListener(FowlPlayNeoForge::onSetup);
        modBus.addListener(FowlPlayNeoForge::onAddItemGroupEntries);

        PlatformHelperImpl.CHICKEN_VARIANTS.register(modBus);
        PlatformHelperImpl.DUCK_VARIANTS.register(modBus);
        PlatformHelperImpl.GULL_VARIANTS.register(modBus);
        PlatformHelperImpl.PIGEON_VARIANTS.register(modBus);
        PlatformHelperImpl.SPARROW_VARIANTS.register(modBus);
        PlatformHelperImpl.ACTIVITIES.register(modBus);
        PlatformHelperImpl.ENTITY_TYPES.register(modBus);
        PlatformHelperImpl.ITEMS.register(modBus);
        PlatformHelperImpl.MEMORY_MODULE_TYPES.register(modBus);
        PlatformHelperImpl.PARTICLE_TYPES.register(modBus);
        PlatformHelperImpl.SENSOR_TYPES.register(modBus);
        PlatformHelperImpl.SOUND_EVENTS.register(modBus);
        PlatformHelperImpl.TRACKED_DATA_HANDLERS.register(modBus);
        ModLoadingContext.get().getActiveContainer().registerExtensionPoint(
            IConfigScreenFactory.class, (client, parent) -> YACLIntegration.createScreen(parent)
        );
    }

    private static void onNewRegistry(NewRegistryEvent event) {
        FowlPlay.earlyInit();
        PlatformHelperImpl.REGISTRIES.forEach(event::register);
    }

    private static void onSetup(FMLCommonSetupEvent event) {
    }

    private static void onAddItemGroupEntries(BuildCreativeModeTabContentsEvent event) {
//        PlatformHelperImpl.ITEM_TO_GROUPS.forEach(((item, group) -> {
//            if(event.getTabKey() == group) {
//                event.add(item.get());
//            }
//        }));
        if(event.getTabKey() == ItemGroups.SPAWN_EGGS) {
            event.add(FowlPlayItems.BLUE_JAY_SPAWN_EGG.get());
            event.add(FowlPlayItems.CARDINAL_SPAWN_EGG.get());
            event.add(FowlPlayItems.CHICKADEE_SPAWN_EGG.get());
            event.add(FowlPlayItems.CROW_SPAWN_EGG.get());
            event.add(FowlPlayItems.DUCK_SPAWN_EGG.get());
            event.add(FowlPlayItems.GULL_SPAWN_EGG.get());
            event.add(FowlPlayItems.HAWK_SPAWN_EGG.get());
            event.add(FowlPlayItems.PENGUIN_SPAWN_EGG.get());
            event.add(FowlPlayItems.PIGEON_SPAWN_EGG.get());
            event.add(FowlPlayItems.RAVEN_SPAWN_EGG.get());
            event.add(FowlPlayItems.ROBIN_SPAWN_EGG.get());
            event.add(FowlPlayItems.SPARROW_SPAWN_EGG.get());
        }
    }
}
