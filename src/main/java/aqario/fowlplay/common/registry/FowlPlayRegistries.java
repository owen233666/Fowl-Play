package aqario.fowlplay.common.registry;

import aqario.fowlplay.common.entity.DuckVariant;
import aqario.fowlplay.common.entity.SparrowVariant;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;

public class FowlPlayRegistries {
    public static final Registry<DuckVariant> DUCK_VARIANT = FabricRegistryBuilder
        .createSimple(FowlPlayRegistryKeys.DUCK_VARIANT)
        .buildAndRegister();
    public static final Registry<DuckVariant> GULL_VARIANT = FabricRegistryBuilder
        .createSimple(FowlPlayRegistryKeys.GULL_VARIANT)
        .buildAndRegister();
    public static final Registry<DuckVariant> PIGEON_VARIANT = FabricRegistryBuilder
        .createSimple(FowlPlayRegistryKeys.PIGEON_VARIANT)
        .buildAndRegister();
    public static final Registry<SparrowVariant> SPARROW_VARIANT = FabricRegistryBuilder
        .createSimple(FowlPlayRegistryKeys.SPARROW_VARIANT)
        .buildAndRegister();

    public static void init() {
    }
}
