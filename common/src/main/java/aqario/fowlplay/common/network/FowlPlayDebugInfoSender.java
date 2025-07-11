package aqario.fowlplay.common.network;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.TrustingBirdEntity;
import aqario.fowlplay.common.network.s2c.DebugBirdCustomPayload;
import aqario.fowlplay.core.FowlPlay;
import dev.architectury.networking.NetworkManager;
import net.minecraft.entity.InventoryOwner;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.NameGenerator;

import java.util.ArrayList;
import java.util.List;

public class FowlPlayDebugInfoSender {
    public static void sendBirdDebugData(BirdEntity bird) {
        if(!FowlPlay.isDebugUtilsLoaded()) {
            return;
        }
        if(bird.getWorld().isClient()) {
            return;
        }

        Brain<?> brain = bird.getBrain();
        String name = NameGenerator.name(bird);
        String inventory = "";
        Path path = null;
        if(bird instanceof InventoryOwner inventoryOwner) {
            inventory = inventoryOwner.getInventory().isEmpty() ? "" : inventoryOwner.getInventory().toString();
        }
        if(brain.hasMemoryModule(MemoryModuleType.PATH)) {
            path = brain.getOptionalRegisteredMemory(MemoryModuleType.PATH).get();
        }
        List<String> trusting = new ArrayList<>();
        if(bird instanceof TrustingBirdEntity trustingBird) {
            trustingBird.getTrustedUuids().forEach(uuid -> {
                PlayerEntity player = bird.getWorld().getPlayerByUuid(uuid);
                if(player != null) {
                    trusting.add(player.getName().getString());
                }
                else {
                    trusting.add(uuid.toString());
                }
            });
        }

        DebugBirdCustomPayload.BirdData data = new DebugBirdCustomPayload.BirdData(
            bird.getUuid(),
            bird.getId(),
            name,
            bird.getMoveControl().getClass().getSimpleName(),
            bird.getNavigation().getClass().getSimpleName(),
            bird.getHealth(),
            bird.getMaxHealth(),
            bird.getPos(),
            inventory,
            path,
            trusting,
            bird.isAmbient()
        );
        DebugBirdCustomPayload payload = new DebugBirdCustomPayload(data);
        sendToAll((ServerWorld) bird.getWorld(), payload);
    }

    private static void sendToAll(ServerWorld world, CustomPayload payload) {
        NetworkManager.sendToPlayers(world.getPlayers(), payload);
    }
}
