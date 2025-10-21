package aqario.fowlplay.core.platform.fabric;

import aqario.fowlplay.common.entity.ChickenVariant;
import aqario.fowlplay.core.FowlPlayRegistries;
import aqario.fowlplay.core.fabric.FowlPlayDataAttachments;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.Optional;

@SuppressWarnings("UnstableApiUsage")
public class DataAttachmentHelperImpl {
    public static RegistryEntry<ChickenVariant> getChickenVariant(ChickenEntity entity) {
        return Optional.ofNullable(entity.getAttached(FowlPlayDataAttachments.CHICKEN_VARIANT))
            .orElse(FowlPlayRegistries.CHICKEN_VARIANT.entryOf(ChickenVariant.WHITE));
    }

    public static void setChickenVariant(ChickenEntity entity, RegistryEntry<ChickenVariant> variant) {
        entity.setAttached(FowlPlayDataAttachments.CHICKEN_VARIANT, variant);
    }

    public static void sendChickenVariantUpdate(ChickenEntity entity) {
    }
}
