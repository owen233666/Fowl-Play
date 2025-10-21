package aqario.fowlplay.mixin;

import aqario.fowlplay.common.entity.ChickenVariant;
import aqario.fowlplay.common.util.ChickenAnimationStates;
import aqario.fowlplay.core.FowlPlayRegistries;
import aqario.fowlplay.core.platform.DataAttachmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ChickenEntity.class)
public abstract class ChickenEntityMixin extends AnimalEntity implements VariantHolder<RegistryEntry<ChickenVariant>>, ChickenAnimationStates {
    //    @Unique
//    private static final TrackedData<RegistryEntry<ChickenVariant>> fowlplay$VARIANT = DataTracker.registerData(
//        ChickenEntity.class,
//        FowlPlayTrackedDataHandlerRegistry.CHICKEN_VARIANT
//    );
    @Unique
    private final AnimationState fowlplay$standingState = new AnimationState();
    @Unique
    private final AnimationState fowlplay$flappingState = new AnimationState();
    @Unique
    private final AnimationState fowlplay$floatingState = new AnimationState();

    protected ChickenEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        switch(spawnReason) {
            case BREEDING ->
                FowlPlayRegistries.CHICKEN_VARIANT.getEntry(ChickenVariant.WHITE).ifPresent(this::setVariant);
            case CHUNK_GENERATION ->
                FowlPlayRegistries.CHICKEN_VARIANT.getEntry(ChickenVariant.RED_JUNGLEFOWL).ifPresent(this::setVariant);
            default -> FowlPlayRegistries.CHICKEN_VARIANT.getRandom(world.getRandom()).ifPresent(this::setVariant);
        }
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    public RegistryEntry<ChickenVariant> getVariant() {
        return DataAttachmentHelper.getChickenVariant((ChickenEntity) (Object) this);
    }

    @Override
    public void setVariant(RegistryEntry<ChickenVariant> variant) {
        DataAttachmentHelper.setChickenVariant((ChickenEntity) (Object) this, variant);
    }

//    @Override
//    protected void initDataTracker(DataTracker.Builder builder) {
//        super.initDataTracker(builder);
//        builder.add(fowlplay$VARIANT, FowlPlayRegistries.CHICKEN_VARIANT.entryOf(ChickenVariant.WHITE));
//    }
//
//    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
//    private void fowlplay$readCustomVariant(NbtCompound nbt, CallbackInfo ci) {
//        Optional.ofNullable(Identifier.tryParse(nbt.getString("variant")))
//            .map(variant -> RegistryKey.of(FowlPlayRegistryKeys.CHICKEN_VARIANT, variant))
//            .flatMap(FowlPlayRegistries.CHICKEN_VARIANT::getEntry)
//            .ifPresent(this::setVariant);
//    }
//
//    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
//    private void fowlplay$writeCustomVariant(NbtCompound nbt, CallbackInfo ci) {
//        nbt.putString("variant", this.getVariant().getKey().orElse(ChickenVariant.WHITE).getValue().toString());
//    }

    @Override
    public void tick() {
        if(this.getWorld().isClient()) {
            this.fowlplay$standingState.setRunning(this.isOnGround() && !this.isInsideWaterOrBubbleColumn(), this.age);
            this.fowlplay$flappingState.setRunning(!this.isOnGround() && !this.isInsideWaterOrBubbleColumn(), this.age);
            this.fowlplay$floatingState.setRunning(this.isInsideWaterOrBubbleColumn(), this.age);
        }
        super.tick();
    }

    @Override
    public AnimationState fowlplay$getStandingState() {
        return this.fowlplay$standingState;
    }

    @Override
    public AnimationState fowlplay$getFlappingState() {
        return this.fowlplay$flappingState;
    }

    @Override
    public AnimationState fowlplay$getFloatingState() {
        return this.fowlplay$floatingState;
    }
}
