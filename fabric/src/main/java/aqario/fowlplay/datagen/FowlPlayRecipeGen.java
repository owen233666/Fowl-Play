package aqario.fowlplay.datagen;

import aqario.fowlplay.core.FowlPlayItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class FowlPlayRecipeGen extends FabricRecipeProvider {
    public FowlPlayRecipeGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, FowlPlayItems.SCARECROW.get(), 1)
            .input('#', Items.HAY_BLOCK)
            .input('/', Items.STICK)
            .pattern(" # ")
            .pattern("/#/")
            .pattern(" / ")
            .criterion(hasItem(Items.HAY_BLOCK), conditionsFromItem(Items.HAY_BLOCK))
            .offerTo(exporter);
    }
}
