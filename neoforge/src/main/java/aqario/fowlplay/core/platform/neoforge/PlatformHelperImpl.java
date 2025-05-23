package aqario.fowlplay.core.platform.neoforge;

import aqario.fowlplay.core.FowlPlay;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegistryBuilder;

@SuppressWarnings("unused")
public class PlatformHelperImpl {
    public static final Object2ObjectOpenHashMap<Item, RegistryKey<ItemGroup>> ITEM_TO_GROUPS = new Object2ObjectOpenHashMap<>();
    public static final ObjectArrayList<Registry<?>> REGISTRIES = new ObjectArrayList<>();
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(
        FowlPlay.ID
    );
    public static final DeferredRegister<TrackedDataHandler<?>> TRACKED_DATA_HANDLERS = DeferredRegister.create(
        NeoForgeRegistries.ENTITY_DATA_SERIALIZERS,
        FowlPlay.ID
    );

    public static <T> Registry<T> registerRegistry(RegistryKey<Registry<T>> registryKey, boolean sync) {
        RegistryBuilder<T> builder = new RegistryBuilder<>(registryKey);
        if(sync) {
            builder.sync(true);
        }
        Registry<T> registry = builder.create();
        REGISTRIES.add(registry);
        return registry;
    }

    public static <T> void registerTrackedDataHandler(String id, TrackedDataHandler<T> handler) {
        TRACKED_DATA_HANDLERS.register(id, () -> handler);
    }

    public static void addItemToItemGroup(Item item, RegistryKey<ItemGroup> itemGroup) {
        ITEM_TO_GROUPS.put(item, itemGroup);
    }
}
