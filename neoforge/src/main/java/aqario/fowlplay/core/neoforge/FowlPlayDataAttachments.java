package aqario.fowlplay.core.neoforge;

import aqario.fowlplay.common.entity.ChickenVariant;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistries;
import net.minecraft.registry.entry.RegistryEntry;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class FowlPlayDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(
        NeoForgeRegistries.ATTACHMENT_TYPES,
        FowlPlay.ID
    );

    public static final Supplier<AttachmentType<RegistryEntry<ChickenVariant>>> CHICKEN_VARIANT = register(
        "chicken_variant",
        AttachmentType.builder(
                () -> FowlPlayRegistries.CHICKEN_VARIANT.entryOf(ChickenVariant.WHITE).getDelegate()
            )
            .serialize(FowlPlayRegistries.CHICKEN_VARIANT.getEntryCodec())

    );

    private static <T> Supplier<AttachmentType<T>> register(String id, AttachmentType.Builder<T> builder) {
        return ATTACHMENT_TYPES.register(
            id,
            builder::build
        );
    }
}
