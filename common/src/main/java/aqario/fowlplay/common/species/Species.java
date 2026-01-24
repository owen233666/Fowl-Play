package aqario.fowlplay.common.species;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.common.util.BirdUtils;
import aqario.fowlplay.core.FowlPlayRegistries;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;

import java.util.List;

public abstract class Species<E extends BirdEntity> {
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<Species<?>>> STREAM_CODEC = ByteBufCodecs.holderRegistry(FowlPlayRegistries.SPECIES);

    public abstract TagKey<Item> getFood();

    public boolean canHunt(LivingEntity target) {
        return false;
    }

    public boolean shouldAttack(LivingEntity target) {
        return false;
    }

    public boolean shouldAvoid(LivingEntity target) {
        return false;
    }

    public int getFleeRangeFrom(LivingEntity target) {
        return BirdUtils.isNotFlightless(target) ? 32 : 16;
    }

    public List<? extends ExtendedSensor<? extends E>> getSensors() {
        return ObjectArrayList.of();
    }

    public BrainActivityGroup<? extends E> getCoreTasks() {
        return BrainActivityGroup.empty();
    }

    public BrainActivityGroup<? extends E> getAvoidTasks() {
        return BrainActivityGroup.empty();
    }

    public BrainActivityGroup<? extends E> getForageTasks() {
        return BrainActivityGroup.empty();
    }

    public BrainActivityGroup<? extends E> getPerchTasks() {
        return BrainActivityGroup.empty();
    }

    public BrainActivityGroup<? extends E> getPickupFoodTasks() {
        return BrainActivityGroup.empty();
    }

    public BrainActivityGroup<? extends E> getRestTasks() {
        return BrainActivityGroup.empty();
    }
}
