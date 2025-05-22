package aqario.fowlplay.common.util;

import aqario.fowlplay.common.entity.BirdEntity;
import com.google.common.collect.ImmutableSet;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import net.minecraft.block.Block;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class EntityTypeBuilder<T extends Entity> {
    private final EntityType.EntityFactory<T> factory;
    private final SpawnGroup spawnGroup;
    private ImmutableSet<Block> canSpawnInside = ImmutableSet.of();
    private boolean saveable = true;
    private boolean summonable = true;
    private boolean fireImmune;
    private boolean spawnableFarFromPlayer;
    private int maxTrackingRange = 5;
    private int trackingTickInterval = 3;
    private EntityDimensions dimensions = EntityDimensions.changing(0.6F, 1.8F);
    private float spawnBoxScale = 1.0F;
    private EntityAttachments.Builder attachments = EntityAttachments.builder();
    private FeatureSet requiredFeatures;
    @Nullable
    private Supplier<DefaultAttributeContainer.Builder> defaultAttributeBuilder;
    private SpawnLocation restrictionLocation;
    private Heightmap.Type restrictionHeightmap;
    private SpawnRestriction.SpawnPredicate<T> spawnPredicate;

    private EntityTypeBuilder(EntityType.EntityFactory<T> factory, SpawnGroup spawnGroup) {
        this.requiredFeatures = FeatureFlags.VANILLA_FEATURES;
        this.factory = factory;
        this.spawnGroup = spawnGroup;
        this.spawnableFarFromPlayer = spawnGroup == SpawnGroup.CREATURE || spawnGroup == SpawnGroup.MISC;
    }

    public static <T extends BirdEntity> EntityTypeBuilder<T> create(EntityType.EntityFactory<T> factory, SpawnGroup spawnGroup) {
        return new EntityTypeBuilder<>(factory, spawnGroup);
    }

    public static <T extends BirdEntity> EntityTypeBuilder<T> create(SpawnGroup spawnGroup) {
        return new EntityTypeBuilder<>((type, world) -> null, spawnGroup);
    }

    public EntityTypeBuilder<T> dimensions(float width, float height) {
        this.dimensions = EntityDimensions.changing(width, height);
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
            this.attachments = this.attachments.add(EntityAttachmentType.PASSENGER, 0.0F, f, 0.0F);
        }

        return this;
    }

    public EntityTypeBuilder<T> passengerAttachments(Vec3d... passengerAttachments) {
        for(Vec3d vec3d : passengerAttachments) {
            this.attachments = this.attachments.add(EntityAttachmentType.PASSENGER, vec3d);
        }

        return this;
    }

    public EntityTypeBuilder<T> vehicleAttachment(Vec3d vehicleAttachment) {
        return this.attachment(EntityAttachmentType.VEHICLE, vehicleAttachment);
    }

    public EntityTypeBuilder<T> vehicleAttachment(float offsetY) {
        return this.attachment(EntityAttachmentType.VEHICLE, 0.0F, -offsetY, 0.0F);
    }

    public EntityTypeBuilder<T> nameTagAttachment(float offsetY) {
        return this.attachment(EntityAttachmentType.NAME_TAG, 0.0F, offsetY, 0.0F);
    }

    public EntityTypeBuilder<T> attachment(EntityAttachmentType type, float offsetX, float offsetY, float offsetZ) {
        this.attachments = this.attachments.add(type, offsetX, offsetY, offsetZ);
        return this;
    }

    public EntityTypeBuilder<T> attachment(EntityAttachmentType type, Vec3d offset) {
        this.attachments = this.attachments.add(type, offset);
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
        this.requiredFeatures = FeatureFlags.FEATURE_MANAGER.featureSetOf(features);
        return this;
    }

    public EntityTypeBuilder<T> defaultAttributes(Supplier<DefaultAttributeContainer.Builder> defaultAttributeBuilder) {
        this.defaultAttributeBuilder = defaultAttributeBuilder;
        return this;
    }

    public EntityTypeBuilder<T> spawnRestriction(SpawnLocation location, Heightmap.Type heightmap, SpawnRestriction.SpawnPredicate<T> spawnPredicate) {
        this.restrictionLocation = location;
        this.restrictionHeightmap = heightmap;
        this.spawnPredicate = spawnPredicate;
        return this;
    }

    public EntityType<T> build() {
        return this.build(null);
    }

    @SuppressWarnings("unchecked")
    public EntityType<T> build(String id) {
        if(this.saveable) {
            Util.getChoiceType(TypeReferences.ENTITY_TREE, id);
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
            if(this.defaultAttributeBuilder != null) {
                EntityAttributeRegistry.register(() -> (EntityType<? extends LivingEntity>) type, this.defaultAttributeBuilder);
            }
        }

        if(type.getBaseClass().isAssignableFrom(MobEntity.class)) {
            if(this.spawnPredicate != null) {
                SpawnRestriction.register((EntityType<MobEntity>) type, this.restrictionLocation, this.restrictionHeightmap, (SpawnRestriction.SpawnPredicate<MobEntity>) this.spawnPredicate);
            }
        }

        return type;
    }
}
