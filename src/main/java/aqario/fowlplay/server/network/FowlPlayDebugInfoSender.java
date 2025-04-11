package aqario.fowlplay.server.network;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.TrustingBirdEntity;
import aqario.fowlplay.common.network.s2c.DebugBirdCustomPayload;
import net.minecraft.entity.InventoryOwner;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.common.CustomPayloadS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.ArrayList;
import java.util.List;

public class FowlPlayDebugInfoSender {
    public static void sendBirdDebugData(BirdEntity bird) {
        if (bird.getWorld().isClient()) {
            return;
        }

        Brain<?> brain = bird.getBrain();
        String inventory = "";
        Path path = null;
        if (bird instanceof InventoryOwner inventoryOwner) {
            inventory = inventoryOwner.getInventory().isEmpty() ? "" : inventoryOwner.getInventory().toString();
        }
        if (brain.hasMemoryModule(MemoryModuleType.PATH)) {
            path = brain.getOptionalRegisteredMemory(MemoryModuleType.PATH).get();
        }
        List<String> trusting = new ArrayList<>();
        if (bird instanceof TrustingBirdEntity trustingBird) {
            trustingBird.getTrustedUuids().forEach(uuid -> trusting.add(uuid.toString()));
        }

        DebugBirdCustomPayload.BirdData data = new DebugBirdCustomPayload.BirdData(
            bird.getUuid(),
            bird.getId(),
            bird.getName().getString(),
            bird.getMoveControl().getClass().getSimpleName(),
            bird.getNavigation().getClass().getSimpleName(),
            bird.getHealth(),
            bird.getMaxHealth(),
            bird.getPos(),
            inventory,
            path,
            trusting
        );
        DebugBirdCustomPayload packet = new DebugBirdCustomPayload(data);
        sendToAll((ServerWorld) bird.getWorld(), packet);
    }

    private static void sendToAll(ServerWorld world, CustomPayload payload) {
        Packet<?> packet = new CustomPayloadS2CPacket(payload);

        for (ServerPlayerEntity player : world.getPlayers()) {
            player.networkHandler.sendPacket(packet);
        }
    }
}
