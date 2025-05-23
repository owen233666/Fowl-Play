package aqario.fowlplay.core.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class PlatformHelper {
    @ExpectPlatform
    public static <T> Registry<T> registerRegistry(RegistryKey<Registry<T>> registryKey, boolean sync) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T> void registerTrackedDataHandler(String id, TrackedDataHandler<T> handler) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void addItemToItemGroup(Item item, RegistryKey<ItemGroup> itemGroup) {
        throw new AssertionError();
    }
}
