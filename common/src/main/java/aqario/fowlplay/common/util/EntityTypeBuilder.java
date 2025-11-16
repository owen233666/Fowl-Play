package aqario.fowlplay.common.util;

import com.google.common.collect.ImmutableSet;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.level.entity.SpawnPlacementsRegistry;
import net.minecraft.Util;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class EntityTypeBuilder<T extends Entity> {
    private final EntityType.EntityFactory<T> factory;
    private final MobCategory spawnGroup;
    private ImmutableSet<Block> canSpawnInside = ImmutableSet.of();
    private boolean saveable = true;
    private boolean summonable = true;
    private boolean fireImmune;
    private boolean spawnableFarFromPlayer;
    private int maxTrackingRange = 5;
    private int trackingTickInterval = 3;
    private EntityDimensions dimensions = EntityDimensions.scalable(0.6F, 1.8F);
    private float spawnBoxScale = 1.0F;
    private EntityAttachments.Builder attachments = EntityAttachments.builder();
    private FeatureFlagSet requiredFeatures;
    @Nullable
    private Supplier<AttributeSupplier.Builder> attributeBuilder;
    private SpawnPlacementType location;
    private Heightmap.Types heightmap;
    private SpawnPlacements.SpawnPredicate<T> spawnPredicate;

    private EntityTypeBuilder(EntityType.EntityFactory<T> factory, MobCategory spawnGroup) {
        this.requiredFeatures = FeatureFlags.VANILLA_SET;
        this.factory = factory;
        this.spawnGroup = spawnGroup;
        this.spawnableFarFromPlayer = spawnGroup == MobCategory.CREATURE || spawnGroup == MobCategory.MISC;
    }

    public static <T extends Entity> EntityTypeBuilder<T> create(EntityType.EntityFactory<T> factory, MobCategory spawnGroup) {
        return new EntityTypeBuilder<>(factory, spawnGroup);
    }

    public static <T extends Entity> EntityTypeBuilder<T> create(MobCategory spawnGroup) {
        return new EntityTypeBuilder<>((type, world) -> null, spawnGroup);
    }

    public EntityTypeBuilder<T> dimensions(float width, float height) {
        this.dimensions = EntityDimensions.scalable(width, height);
        return this;
    }

    public EntityTypeBuilder<T> spawnBoxScale(float spawnBoxScale) {
        this.spawnBoxScale = spawnBoxScale;
        return this;
    }

    public EntityTypeBuilder<T> eyeHeight(float eyeHeight) {
        this.dimensions = this.dimensions.withEyeHeight(eyeHeight);
        return this;
    }

    public EntityTypeBuilder<T> passengerAttachments(float... offsetYs) {
        for(float f : offsetYs) {
            this.attachments = this.attachments.attach(EntityAttachment.PASSENGER, 0.0F, f, 0.0F);
        }

        return this;
    }

    public EntityTypeBuilder<T> passengerAttachments(Vec3... passengerAttachments) {
        for(Vec3 vec3d : passengerAttachments) {
            this.attachments = this.attachments.attach(EntityAttachment.PASSENGER, vec3d);
        }

        return this;
    }

    public EntityTypeBuilder<T> vehicleAttachment(Vec3 vehicleAttachment) {
        return this.attachment(EntityAttachment.VEHICLE, vehicleAttachment);
    }

    public EntityTypeBuilder<T> vehicleAttachment(float offsetY) {
        return this.attachment(EntityAttachment.VEHICLE, 0.0F, -offsetY, 0.0F);
    }

    public EntityTypeBuilder<T> nameTagAttachment(float offsetY) {
        return this.attachment(EntityAttachment.NAME_TAG, 0.0F, offsetY, 0.0F);
    }

    public EntityTypeBuilder<T> attachment(EntityAttachment type, float offsetX, float offsetY, float offsetZ) {
        this.attachments = this.attachments.attach(type, offsetX, offsetY, offsetZ);
        return this;
    }

    public EntityTypeBuilder<T> attachment(EntityAttachment type, Vec3 offset) {
        this.attachments = this.attachments.attach(type, offset);
        return this;
    }

    public EntityTypeBuilder<T> disableSummon() {
        this.summonable = false;
        return this;
    }

    public EntityTypeBuilder<T> disableSaving() {
        this.saveable = false;
        return this;
    }

    public EntityTypeBuilder<T> makeFireImmune() {
        this.fireImmune = true;
        return this;
    }

    public EntityTypeBuilder<T> allowSpawningInside(Block... blocks) {
        this.canSpawnInside = ImmutableSet.copyOf(blocks);
        return this;
    }

    public EntityTypeBuilder<T> spawnableFarFromPlayer() {
        this.spawnableFarFromPlayer = true;
        return this;
    }

    public EntityTypeBuilder<T> maxTrackingRange(int maxTrackingRange) {
        this.maxTrackingRange = maxTrackingRange;
        return this;
    }

    public EntityTypeBuilder<T> trackingTickInterval(int trackingTickInterval) {
        this.trackingTickInterval = trackingTickInterval;
        return this;
    }

    public EntityTypeBuilder<T> requires(FeatureFlag... features) {
        this.requiredFeatures = FeatureFlags.REGISTRY.subset(features);
        return this;
    }

    public EntityTypeBuilder<T> attributes(Supplier<AttributeSupplier.Builder> attributeBuilder) {
        this.attributeBuilder = attributeBuilder;
        return this;
    }

    public EntityTypeBuilder<T> spawnRestriction(SpawnPlacementType location, Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<T> spawnPredicate) {
        this.location = location;
        this.heightmap = heightmap;
        this.spawnPredicate = spawnPredicate;
        return this;
    }

    public EntityType<T> build() {
        return this.build(null);
    }

    @SuppressWarnings("unchecked")
    public EntityType<T> build(String id) {
        if(this.saveable) {
            Util.fetchChoiceType(References.ENTITY_TREE, id);
        }

        EntityType<T> type = new EntityType<>(
            this.factory,
            this.spawnGroup,
            this.saveable,
            this.summonable,
            this.fireImmune,
            this.spawnableFarFromPlayer,
            this.canSpawnInside,
            this.dimensions.withAttachments(this.attachments),
            this.spawnBoxScale,
            this.maxTrackingRange,
            this.trackingTickInterval,
            this.requiredFeatures
        );

        if(type.getBaseClass().isAssignableFrom(LivingEntity.class)) {
            if(this.attributeBuilder != null) {
                EntityAttributeRegistry.register(() -> (EntityType<? extends LivingEntity>) type, this.attributeBuilder);
            }
        }

        if(type.getBaseClass().isAssignableFrom(Mob.class)) {
            if(this.spawnPredicate != null) {
                SpawnPlacementsRegistry.register(() -> (EntityType<Mob>) type, this.location, this.heightmap, (SpawnPlacements.SpawnPredicate<Mob>) this.spawnPredicate);
            }
        }

        return type;
    }
}
