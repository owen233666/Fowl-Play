package aqario.fowlplay.core.platform.neoforge;

import aqario.fowlplay.common.entity.ChickenVariant;
import aqario.fowlplay.common.network.neoforge.ChickenVariantPayload;
import aqario.fowlplay.core.neoforge.FowlPlayDataAttachments;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.neoforged.neoforge.network.PacketDistributor;

public class DataAttachmentHelperImpl {
    public static RegistryEntry<ChickenVariant> getChickenVariant(ChickenEntity entity) {
        return entity.getData(FowlPlayDataAttachments.CHICKEN_VARIANT);
    }

    public static void setChickenVariant(ChickenEntity entity, RegistryEntry<ChickenVariant> variant) {
        entity.setData(FowlPlayDataAttachments.CHICKEN_VARIANT, variant);
        PacketDistributor.sendToPlayersTrackingEntity(entity, new ChickenVariantPayload(
            entity.getId(),
            variant
        ));
    }
}
