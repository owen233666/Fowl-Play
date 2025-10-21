package aqario.fowlplay.core.platform;

import aqario.fowlplay.common.entity.ChickenVariant;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.registry.entry.RegistryEntry;

public class DataAttachmentHelper {
    @ExpectPlatform
    public static RegistryEntry<ChickenVariant> getChickenVariant(ChickenEntity entity) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void setChickenVariant(ChickenEntity entity, RegistryEntry<ChickenVariant> variant) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void sendChickenVariantUpdate(ChickenEntity entity) {
        throw new AssertionError();
    }
}
