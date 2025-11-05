package aqario.fowlplay.common.network.neoforge;

import aqario.fowlplay.common.entity.ChickenVariant;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistryKeys;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.entry.RegistryEntry;

public record ChickenVariantPayload(int entityId, RegistryEntry<ChickenVariant> variant) implements CustomPayload {
    public static final PacketCodec<RegistryByteBuf, ChickenVariantPayload> CODEC = CustomPayload.codecOf(
        ChickenVariantPayload::write,
        ChickenVariantPayload::new
    );
    public static final CustomPayload.Id<ChickenVariantPayload> ID = new CustomPayload.Id<>(
        FowlPlay.id("chicken_variant")
    );

    private ChickenVariantPayload(RegistryByteBuf buf) {
        this(
            buf.readInt(),
            PacketCodecs.registryEntry(FowlPlayRegistryKeys.CHICKEN_VARIANT).decode(buf)
        );
    }

    private void write(RegistryByteBuf buf) {
        buf.writeInt(this.entityId);
        PacketCodecs.registryEntry(FowlPlayRegistryKeys.CHICKEN_VARIANT).encode(buf, this.variant);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
