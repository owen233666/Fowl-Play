package aqario.fowlplay.core.platform.fabric;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;

@SuppressWarnings("unused")
public class PlatformHelperImpl {
    public static <T> Registry<T> registerRegistry(RegistryKey<Registry<T>> registryKey, boolean sync) {
        FabricRegistryBuilder<T, SimpleRegistry<T>> builder = FabricRegistryBuilder.createSimple(registryKey);
        if(sync) {
            builder.attribute(RegistryAttribute.SYNCED);
        }
        return builder.buildAndRegister();
    }

    public static <T> void registerTrackedDataHandler(String id, TrackedDataHandler<T> handler) {
        TrackedDataHandlerRegistry.register(handler);
    }

    public static void addItemToItemGroup(Item item, RegistryKey<ItemGroup> itemGroup) {
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries ->
            entries.add(item)
        );
    }
}
