package aqario.fowlplay.core.platform.neoforge;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class PlatformHelperImpl {
    public static final Object2ObjectOpenHashMap<Item, RegistryKey<ItemGroup>> ITEM_TO_GROUPS = new Object2ObjectOpenHashMap<>();
    public static final ObjectArrayList<Registry<?>> REGISTRIES = new ObjectArrayList<>();

    public static void addItemToItemGroup(Item item, RegistryKey<ItemGroup> itemGroup) {
        ITEM_TO_GROUPS.put(item, itemGroup);
    }

    public static <T> Registry<T> registerRegistry(RegistryKey<Registry<T>> registryKey, boolean sync) {
        System.out.println("SOMETHING GREAT");
        RegistryBuilder<T> builder = new RegistryBuilder<>(registryKey);
        if(sync) {
            builder.sync(true);
        }
        Registry<T> registry = builder.create();
        REGISTRIES.add(registry);
        return registry;
    }
}
