package aqario.fowlplay.datagen;

import aqario.fowlplay.core.tags.FowlPlayBiomeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.concurrent.CompletableFuture;

public class FowlPlayBiomeTagGen extends FabricTagProvider<Biome> {
    private static final Identifier WHITE_CLIFFS = Identifier.of("natures_spirit", "white_cliffs");
    private static final Identifier IS_ALPINE = Identifier.of("natures_spirit", "is_alpine");
    private static final Identifier IS_AUTUMN = Identifier.of("natures_spirit", "is_autumn");
    private static final Identifier IS_COAST = Identifier.of("natures_spirit", "is_coast");
    private static final Identifier IS_COLD = Identifier.of("natures_spirit", "is_cold");
    private static final Identifier IS_CYPRESS = Identifier.of("natures_spirit", "is_cypress");
    private static final Identifier IS_FIELD = Identifier.of("natures_spirit", "is_field");
    private static final Identifier IS_FRONTIER = Identifier.of("natures_spirit", "is_frontier");
    private static final Identifier IS_FREEZING = Identifier.of("natures_spirit", "is_freezing");
    private static final Identifier IS_WETLAND = Identifier.of("natures_spirit", "is_wetland");
    private static final Identifier BEACH = Identifier.of("c", "beach");
    private static final Identifier FOREST = Identifier.of("c", "forest");
    private static final Identifier RIVER = Identifier.of("c", "river");
    private static final Identifier SWAMP = Identifier.of("c", "swamp");
    private static final Identifier TREE_CONIFEROUS = Identifier.of("c", "tree_coniferous");
    private static final Identifier TREE_DECIDUOUS = Identifier.of("c", "tree_deciduous");
    private static final Identifier VEGETATION_SPARSE = Identifier.of("c", "vegetation_sparse");
    private static final Identifier IS_TREE_CONIFEROUS = Identifier.of("c", "is_tree_coniferous");
    private static final Identifier IS_TREE_DECIDUOUS = Identifier.of("c", "is_tree_deciduous");

    public FowlPlayBiomeTagGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.BIOME, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_BLUE_JAYS)
            .addOptionalTag(IS_ALPINE)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(IS_FREEZING)
            .addOptionalTag(ConventionalBiomeTags.IS_FOREST)
            .addOptionalTag(FOREST)
            .addOptionalTag(TREE_CONIFEROUS)
            .addOptionalTag(TREE_DECIDUOUS)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.IS_CONIFEROUS_TREE)
            .addOptionalTag(ConventionalBiomeTags.IS_DECIDUOUS_TREE);
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_CARDINALS)
            .addOptionalTag(IS_ALPINE)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(IS_FREEZING)
            .addOptionalTag(ConventionalBiomeTags.IS_FOREST)
            .addOptionalTag(FOREST)
            .addOptionalTag(TREE_CONIFEROUS)
            .addOptionalTag(TREE_DECIDUOUS)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.IS_CONIFEROUS_TREE)
            .addOptionalTag(ConventionalBiomeTags.IS_DECIDUOUS_TREE);
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_CHICKADEES)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(ConventionalBiomeTags.IS_FOREST)
            .addOptionalTag(FOREST)
            .addOptionalTag(TREE_DECIDUOUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.IS_DECIDUOUS_TREE);
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_CROWS)
            .addOptionalTag(IS_ALPINE)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(IS_FREEZING)
            .addOptionalTag(ConventionalBiomeTags.IS_FOREST)
            .addOptionalTag(FOREST)
            .addOptionalTag(TREE_CONIFEROUS)
            .addOptionalTag(TREE_DECIDUOUS)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.IS_CONIFEROUS_TREE)
            .addOptionalTag(ConventionalBiomeTags.IS_DECIDUOUS_TREE);
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_DUCKS)
            .addOptionalTag(IS_WETLAND)
            .addOptionalTag(RIVER)
            .addOptionalTag(SWAMP)
            .addOptionalTag(ConventionalBiomeTags.IS_RIVER)
            .addOptionalTag(ConventionalBiomeTags.IS_SWAMP)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(ConventionalBiomeTags.IS_FOREST)
            .addOptionalTag(FOREST)
            .addOptionalTag(TREE_CONIFEROUS)
            .addOptionalTag(TREE_DECIDUOUS)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.IS_CONIFEROUS_TREE)
            .addOptionalTag(ConventionalBiomeTags.IS_DECIDUOUS_TREE);
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_GULLS)
            .addOptionalTag(IS_COAST)
            .addOptionalTag(BEACH)
            .addOptionalTag(ConventionalBiomeTags.IS_BEACH)
            .addOptionalTag(ConventionalBiomeTags.IS_STONY_SHORES);
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_HAWKS)
            .addOptionalTag(IS_ALPINE)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FIELD)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(IS_FREEZING)
            .addOptionalTag(VEGETATION_SPARSE)
            .addOptionalTag(ConventionalBiomeTags.IS_VEGETATION_SPARSE)
            .addOptionalTag(ConventionalBiomeTags.IS_VEGETATION_SPARSE_OVERWORLD)
            .add(BiomeKeys.PLAINS)
            .add(BiomeKeys.SAVANNA)
            .add(BiomeKeys.SAVANNA_PLATEAU)
            .add(BiomeKeys.SPARSE_JUNGLE)
            .add(BiomeKeys.SUNFLOWER_PLAINS)
            .add(BiomeKeys.WINDSWEPT_FOREST)
            .add(BiomeKeys.WINDSWEPT_HILLS);
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_PENGUINS)
            .add(BiomeKeys.SNOWY_PLAINS)
            .add(BiomeKeys.SNOWY_BEACH);
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_PIGEONS)
            .addOptionalTag(ConventionalBiomeTags.IS_STONY_SHORES)
            .addOptional(WHITE_CLIFFS);
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_RAVENS)
            .addOptionalTag(IS_ALPINE)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(IS_FREEZING)
            .addOptionalTag(ConventionalBiomeTags.IS_FOREST)
            .addOptionalTag(FOREST)
            .addOptionalTag(TREE_CONIFEROUS)
            .addOptionalTag(TREE_DECIDUOUS)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.IS_CONIFEROUS_TREE)
            .addOptionalTag(ConventionalBiomeTags.IS_DECIDUOUS_TREE);
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_ROBINS)
            .addOptionalTag(IS_ALPINE)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(IS_FREEZING)
            .addOptionalTag(ConventionalBiomeTags.IS_FOREST)
            .addOptionalTag(FOREST)
            .addOptionalTag(TREE_CONIFEROUS)
            .addOptionalTag(TREE_DECIDUOUS)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.IS_CONIFEROUS_TREE)
            .addOptionalTag(ConventionalBiomeTags.IS_DECIDUOUS_TREE);
        this.getOrCreateTagBuilder(FowlPlayBiomeTags.SPAWNS_SPARROWS)
            .addOptionalTag(IS_ALPINE)
            .addOptionalTag(IS_AUTUMN)
            .addOptionalTag(IS_COLD)
            .addOptionalTag(IS_CYPRESS)
            .addOptionalTag(IS_FIELD)
            .addOptionalTag(IS_FRONTIER)
            .addOptionalTag(IS_FREEZING)
            .addOptionalTag(IS_WETLAND)
            .addOptionalTag(ConventionalBiomeTags.IS_FOREST)
            .addOptionalTag(FOREST)
            .addOptionalTag(TREE_CONIFEROUS)
            .addOptionalTag(TREE_DECIDUOUS)
            .addOptionalTag(IS_TREE_CONIFEROUS)
            .addOptionalTag(IS_TREE_DECIDUOUS)
            .addOptionalTag(ConventionalBiomeTags.IS_CONIFEROUS_TREE)
            .addOptionalTag(ConventionalBiomeTags.IS_DECIDUOUS_TREE);
    }
}
