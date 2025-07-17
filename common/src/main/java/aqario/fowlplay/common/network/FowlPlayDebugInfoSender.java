package aqario.fowlplay.common.network;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.TrustingBirdEntity;
import aqario.fowlplay.common.network.s2c.DebugBirdCustomPayload;
import aqario.fowlplay.common.util.Birds;
import aqario.fowlplay.core.FowlPlay;
import com.google.common.collect.Lists;
import dev.architectury.networking.NetworkManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.InventoryOwner;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.NameGenerator;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import org.jetbrains.annotations.Nullable;

import java.util.*;

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

        List<String> activities = brain.getPossibleActivities().stream().map(Activity::getId).toList();
        List<String> behaviors = brain.getRunningTasks().stream().map(Task::getName).toList();
        List<String> memories = getMemoryDescriptions(bird, bird.getWorld().getTime());
        Set<BlockPos> pois = Set.of();
        Set<BlockPos> potentialPois = Set.of();

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
            bird.isAmbient(),
            Birds.isPerched(bird),
            activities,
            behaviors,
            memories,
            pois,
            potentialPois
        );
        DebugBirdCustomPayload payload = new DebugBirdCustomPayload(data);
        sendToAll((ServerWorld) bird.getWorld(), payload);
    }

    @SuppressWarnings("deprecation")
    private static List<String> getMemoryDescriptions(LivingEntity entity, long gameTime) {
        Map<MemoryModuleType<?>, Optional<? extends Memory<?>>> map = entity.getBrain().getMemories();
        List<String> list = Lists.newArrayList();
        for (Map.Entry<MemoryModuleType<?>, Optional<? extends Memory<?>>> entry : map.entrySet()) {
            MemoryModuleType<?> memoryModuleType = entry.getKey();
            Optional<? extends Memory<?>> optional = entry.getValue();
            String string;
            if (optional.isPresent()) {
                Memory<?> expirableValue = optional.get();
                Object object = expirableValue.getValue();
                if (memoryModuleType == MemoryModuleType.HEARD_BELL_TIME) {
                    long l = gameTime - (Long) object;
                    string = l + " ticks ago";
                } else if (expirableValue.isTimed()) {
                    String desc = getShortDescription((ServerWorld) entity.getWorld(), object);
                    string = desc + " (ttl: " + expirableValue.getExpiry() + ")";
                } else {
                    string = getShortDescription((ServerWorld) entity.getWorld(), object);
                }
            } else {
                string = "-";
            }
            String id = Registries.MEMORY_MODULE_TYPE.getId(memoryModuleType).getPath();
            list.add(id + ": " + string);
        }
        list.sort(String::compareTo);
        return list;
    }

    private static String getShortDescription(ServerWorld level, @Nullable Object object) {
        if (object == null) {
            return "-";
        } else if (object instanceof UUID) {
            return getShortDescription(level, level.getEntity((UUID) object));
        } else {
            Entity entity;
            if (object instanceof LivingEntity) {
                entity = (Entity) object;
                return NameGenerator.name(entity);
            } else if (object instanceof Nameable) {
                return ((Nameable) object).getName().getString();
            } else if (object instanceof WalkTarget) {
                return getShortDescription(level, ((WalkTarget) object).getLookTarget());
            } else if (object instanceof EntityLookTarget) {
                return getShortDescription(level, ((EntityLookTarget) object).getEntity());
            } else if (object instanceof GlobalPos) {
                return getShortDescription(level, ((GlobalPos) object).pos());
            } else if (object instanceof BlockPosLookTarget) {
                return getShortDescription(level, ((BlockPosLookTarget) object).getBlockPos());
            } else if (object instanceof DamageSource) {
                entity = ((DamageSource) object).getAttacker();
                return entity == null ? object.toString() : getShortDescription(level, entity);
            } else if (!(object instanceof Collection<?> iterable)) {
                return object.toString();
            } else {
                List<String> list = Lists.newArrayList();
                iterable.forEach(o -> list.add(getShortDescription(level, o)));
                return list.toString();
            }
        }
    }

    private static void sendToAll(ServerWorld world, CustomPayload payload) {
        NetworkManager.sendToPlayers(world.getPlayers(), payload);
    }
}
