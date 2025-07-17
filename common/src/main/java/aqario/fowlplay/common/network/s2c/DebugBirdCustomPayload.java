package aqario.fowlplay.common.network.s2c;

import aqario.fowlplay.client.render.debug.BirdDebugRenderer;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record DebugBirdCustomPayload(BirdData birdData) implements CustomPayload {
    public static final PacketCodec<PacketByteBuf, DebugBirdCustomPayload> CODEC = CustomPayload.codecOf(
        DebugBirdCustomPayload::write, DebugBirdCustomPayload::new
    );
    public static final CustomPayload.Id<DebugBirdCustomPayload> ID = new CustomPayload.Id<>(Identifier.of(FowlPlay.ID, "debug/bird"));

    private DebugBirdCustomPayload(PacketByteBuf buf) {
        this(new BirdData(buf));
    }

    private void write(PacketByteBuf buf) {
        this.birdData.write(buf);
    }

    public static void onReceive(DebugBirdCustomPayload payload) {
        BirdDebugRenderer.INSTANCE.addBird(payload.birdData());
    }

    @Override
    public CustomPayload.Id<DebugBirdCustomPayload> getId() {
        return ID;
    }

    public record BirdData(
        UUID uuid,
        int entityId,
        String name,
        String moveControl,
        String navigation,
        float health,
        float maxHealth,
        Vec3d pos,
        String inventory,
        @Nullable Path path,
        List<String> trusting,
        boolean ambient,
        boolean perched,
        List<String> possibleActivities,
        List<String> runningTasks,
        List<String> memories,
        Set<BlockPos> pois,
        Set<BlockPos> potentialPois
    ) {
        public BirdData(PacketByteBuf buf) {
            this(
                buf.readUuid(),
                buf.readInt(),
                buf.readString(),
                buf.readString(),
                buf.readString(),
                buf.readFloat(),
                buf.readFloat(),
                buf.readVec3d(),
                buf.readString(),
                buf.readNullable(Path::fromBuf),
                buf.readList(PacketByteBuf::readString),
                buf.readBoolean(),
                buf.readBoolean(),
                buf.readList(PacketByteBuf::readString),
                buf.readList(PacketByteBuf::readString),
                buf.readList(PacketByteBuf::readString),
                buf.readCollection(HashSet::new, BlockPos.PACKET_CODEC),
                buf.readCollection(HashSet::new, BlockPos.PACKET_CODEC)
            );
        }

        public void write(PacketByteBuf buf) {
            buf.writeUuid(this.uuid);
            buf.writeInt(this.entityId);
            buf.writeString(this.name);
            buf.writeString(this.moveControl);
            buf.writeString(this.navigation);
            buf.writeFloat(this.health);
            buf.writeFloat(this.maxHealth);
            buf.writeVec3d(this.pos);
            buf.writeString(this.inventory);
            buf.writeNullable(this.path, (bufx, path) -> path.toBuf(bufx));
            buf.writeCollection(this.trusting, PacketByteBuf::writeString);
            buf.writeBoolean(this.ambient);
            buf.writeBoolean(this.perched);
            buf.writeCollection(this.possibleActivities, PacketByteBuf::writeString);
            buf.writeCollection(this.runningTasks, PacketByteBuf::writeString);
            buf.writeCollection(this.memories, PacketByteBuf::writeString);
            buf.writeCollection(this.pois, BlockPos.PACKET_CODEC);
            buf.writeCollection(this.potentialPois, BlockPos.PACKET_CODEC);
        }
    }
}
