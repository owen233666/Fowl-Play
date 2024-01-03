package aqario.fowlplay.client.model;

import net.minecraft.util.Identifier;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.PenguinEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class PenguinModel extends AnimatedGeoModel<PenguinEntity> {
    @Override
    public Identifier getModelResource(PenguinEntity entity) {
        return entity.isBaby() ? new Identifier(FowlPlay.ID, "geo/penguin/penguin_baby.geo.json") : new Identifier(FowlPlay.ID, "geo/penguin/penguin.geo.json");
    }

    @Override
    public Identifier getTextureResource(PenguinEntity entity) {
        return entity.isBaby() ? new Identifier(FowlPlay.ID, "textures/entity/penguin/penguin_baby.png") : new Identifier(FowlPlay.ID, "textures/entity/penguin/penguin.png");
    }

    @Override
    public Identifier getAnimationResource(PenguinEntity entity) {
        return entity.isBaby() ? new Identifier(FowlPlay.ID, "animations/penguin/penguin_baby.animation.json") : new Identifier(FowlPlay.ID, "animations/penguin/penguin.animation.json");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setCustomAnimations(PenguinEntity entity, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(entity, instanceId, animationEvent);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) animationEvent.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}
