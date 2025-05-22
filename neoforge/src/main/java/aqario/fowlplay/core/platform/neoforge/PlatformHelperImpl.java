package aqario.fowlplay.core.platform.neoforge;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class PlatformHelperImpl {
    public static final Object2ObjectOpenHashMap<Item, RegistryKey<ItemGroup>> ITEM_TO_GROUPS = new Object2ObjectOpenHashMap<>();

    public static void addItemToItemGroup(Item item, RegistryKey<ItemGroup> itemGroup) {
        ITEM_TO_GROUPS.put(item, itemGroup);
    }

    public static <T> Registry<T> registerRegistry(RegistryKey<Registry<T>> registryKey, boolean sync) {
        RegistryBuilder<T> builder = new RegistryBuilder<>(registryKey);
        if(sync) {
            builder.sync(true);
        }
        return builder.create();
    }
}
