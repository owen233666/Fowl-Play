package aqario.fowlplay.core.fabric;

import aqario.fowlplay.common.entity.ChickenVariant;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistries;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

@SuppressWarnings("UnstableApiUsage")
public class FowlPlayDataAttachments {
    public static final AttachmentType<RegistryEntry<ChickenVariant>> CHICKEN_VARIANT = register(
        "chicken_variant",
        builder -> builder
            .initializer(() -> FowlPlayRegistries.CHICKEN_VARIANT.entryOf(ChickenVariant.WHITE))
            .persistent(FowlPlayRegistries.CHICKEN_VARIANT.getEntryCodec())
            .syncWith(
                ChickenVariant.PACKET_CODEC,
                AttachmentSyncPredicate.all()
            )
    );

    private static <T> AttachmentType<T> register(String id, Consumer<AttachmentRegistry.Builder<T>> builder) {
        return AttachmentRegistry.create(
            Identifier.of(FowlPlay.ID, id),
            builder
        );
    }

    public static void init() {
    }
}
