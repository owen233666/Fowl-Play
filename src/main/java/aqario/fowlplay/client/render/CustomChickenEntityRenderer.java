package aqario.fowlplay.client.render;

import aqario.fowlplay.client.model.CustomBabyChickenEntityModel;
import aqario.fowlplay.client.model.CustomChickenEntityModel;
import aqario.fowlplay.common.FowlPlay;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.Identifier;

import java.util.Map;

public class CustomChickenEntityRenderer extends MobEntityRenderer<ChickenEntity, CustomChickenEntityModel> {
    private static final Identifier TEXTURE = Identifier.of(FowlPlay.ID, "textures/entity/chicken/white_chicken.png");
    private static final Identifier BABY_TEXTURE = Identifier.of(FowlPlay.ID, "textures/entity/chicken/white_baby_chicken.png");
    private final Map<Boolean, CustomChickenEntityModel> models;

    public CustomChickenEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new CustomChickenEntityModel(context.getPart(CustomChickenEntityModel.MODEL_LAYER)), 0.3f);
        this.models = bakeModels(context);
    }

    private static Map<Boolean, CustomChickenEntityModel> bakeModels(EntityRendererFactory.Context context) {
        return Map.of(
            false,
            new CustomChickenEntityModel(context.getPart(CustomChickenEntityModel.MODEL_LAYER)),
            true,
            new CustomBabyChickenEntityModel(context.getPart(CustomBabyChickenEntityModel.MODEL_LAYER))
        );
    }

    @Override
    public void render(ChickenEntity chicken, float f, float g, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int i) {
        this.model = this.models.get(chicken.isBaby());
        if (chicken.isBaby()) {
            matrices.scale(0.8F, 0.8F, 0.8F);
        }
        super.render(chicken, f, g, matrices, vertexConsumers, i);
    }

    @Override
    public Identifier getTexture(ChickenEntity chicken) {
        return chicken.isBaby() ? BABY_TEXTURE : TEXTURE;
    }
}
