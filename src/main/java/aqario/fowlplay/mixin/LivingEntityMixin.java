package aqario.fowlplay.mixin;

import aqario.fowlplay.common.entity.PenguinEntity;
import aqario.fowlplay.common.entity.Sliding;
import aqario.fowlplay.common.tags.FowlPlayBlockTags;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Sliding {
    public LivingEntityMixin(EntityType<?> variant, World world) {
        super(variant, world);
    }

    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getSlipperiness()F"))
    private float fowlplay$modifySlipperiness(Block instance) {
        // instanceof won't compile so I'm using this instead; no pattern variables so I'm relying on an interface
        if (this.getClass().isAssignableFrom(PenguinEntity.class) && this.isSliding()) {
            return instance.getDefaultState().isIn(FowlPlayBlockTags.PENGUINS_SLIDE_ON) || this.getBlockStateAtPos().isIn(FowlPlayBlockTags.PENGUINS_SLIDE_ON) ? 1.025F : instance.getSlipperiness();
        }
        return instance.getSlipperiness();
    }
}
