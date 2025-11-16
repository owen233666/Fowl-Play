package aqario.fowlplay.mixin;

import aqario.fowlplay.common.entity.CustomSpawnGroup;
import net.minecraft.world.entity.MobCategory;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

// credit to hybrid aquatic for the code
@Mixin(MobCategory.class)
public class SpawnGroupMixin {
    @SuppressWarnings("unused")
    SpawnGroupMixin(String enumname, int ordinal, String name, int spawnCap, boolean peaceful, boolean rare, int immediateDespawnRange) {
        throw new AssertionError();
    }

    // Vanilla Spawn Groups array
    @Shadow
    @Mutable
    @Final
    private static MobCategory[] $VALUES;

    @Unique
    private static MobCategory fowlplay$createSpawnGroup(String enumname, int ordinal, CustomSpawnGroup spawnGroup) {
        return ((MobCategory) (Object) new SpawnGroupMixin(enumname, ordinal, spawnGroup.name, spawnGroup.spawnCap, spawnGroup.peaceful, spawnGroup.rare, spawnGroup.immediateDespawnRange));
    }

    @Inject(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/MobCategory;$VALUES:[Lnet/minecraft/world/entity/MobCategory;", shift = At.Shift.AFTER))
    private static void fowlplay$addCustomGroups(CallbackInfo ci) {
        int vanillaSpawnGroupsLength = $VALUES.length;
        CustomSpawnGroup[] groups = CustomSpawnGroup.values();
        $VALUES = Arrays.copyOf($VALUES, vanillaSpawnGroupsLength + groups.length);

        for(int i = 0; i < groups.length; i++) {
            int pos = vanillaSpawnGroupsLength + i;
            CustomSpawnGroup spawnGroup = groups[i];
            spawnGroup.spawnGroup = $VALUES[pos] = fowlplay$createSpawnGroup(spawnGroup.name(), pos, spawnGroup);
        }
    }
}