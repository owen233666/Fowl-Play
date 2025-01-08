package aqario.fowlplay.common.entity.ai.brain;

import com.google.common.collect.Iterables;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.brain.MemoryModuleType;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class VisibleMobsCache {
    private static final TargetPredicate TARGET_PREDICATE = TargetPredicate.createNonAttackable().setBaseMaxDistance(32.0);
    private static final TargetPredicate TARGET_PREDICATE_IGNORE_DISTANCE_SCALING = TargetPredicate.createNonAttackable()
        .setBaseMaxDistance(32.0)
        .ignoreDistanceScalingFactor();
    private static final VisibleMobsCache EMPTY = new VisibleMobsCache();
    private final List<LivingEntity> entities;
    private final Predicate<LivingEntity> visibilityPredicate;

    private VisibleMobsCache() {
        this.entities = List.of();
        this.visibilityPredicate = entity -> false;
    }

    public VisibleMobsCache(LivingEntity owner, List<LivingEntity> entities) {
        this.entities = entities;
        Object2BooleanOpenHashMap<LivingEntity> object2BooleanOpenHashMap = new Object2BooleanOpenHashMap<>(entities.size());
        Predicate<LivingEntity> predicate = entity -> testTargetPredicate(owner, entity);
        this.visibilityPredicate = entity -> object2BooleanOpenHashMap.computeIfAbsent(entity, predicate);
    }

    public static boolean testTargetPredicate(LivingEntity entity, LivingEntity target) {
        return entity.getBrain().hasMemoryModuleWithValue(MemoryModuleType.ATTACK_TARGET, target)
            ? TARGET_PREDICATE_IGNORE_DISTANCE_SCALING.test(entity, target)
            : TARGET_PREDICATE.test(entity, target);
    }

    public static VisibleMobsCache getEmpty() {
        return EMPTY;
    }

    public Optional<LivingEntity> getFirst(Predicate<LivingEntity> predicate) {
        for (LivingEntity livingEntity : this.entities) {
            if (predicate.test(livingEntity) && this.visibilityPredicate.test(livingEntity)) {
                return Optional.of(livingEntity);
            }
        }

        return Optional.empty();
    }

    public Iterable<LivingEntity> iterate(Predicate<LivingEntity> predicate) {
        return Iterables.filter(this.entities, entity -> predicate.test(entity) && this.visibilityPredicate.test(entity));
    }

    public Stream<LivingEntity> stream(Predicate<LivingEntity> predicate) {
        return this.entities.stream().filter(entity -> predicate.test(entity) && this.visibilityPredicate.test(entity));
    }

    public boolean contains(LivingEntity entity) {
        return this.entities.contains(entity) && this.visibilityPredicate.test(entity);
    }

    public boolean anyMatch(Predicate<LivingEntity> predicate) {
        for (LivingEntity livingEntity : this.entities) {
            if (predicate.test(livingEntity) && this.visibilityPredicate.test(livingEntity)) {
                return true;
            }
        }

        return false;
    }
}
