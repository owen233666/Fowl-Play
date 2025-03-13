package aqario.fowlplay.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.biome.Biome;

import java.util.concurrent.CompletableFuture;

public class FowlPlayBiomeTagGen extends FabricTagProvider<Biome> {
    public FowlPlayBiomeTagGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.BIOME, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
//        getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_RAVENS)
//            .addTag(BiomeTags.IS_FOREST)
//            .addOptionalTag(ConventionalBiomeTags.IS_FOREST);
    }
}
