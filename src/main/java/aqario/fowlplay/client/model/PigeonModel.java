package aqario.fowlplay.client.model;

import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.PigeonEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class PigeonModel extends AnimatedGeoModel<PigeonEntity> {
    @Override
    public Identifier getModelResource(PigeonEntity object) {
        return new Identifier(FowlPlay.ID, "geo/pigeon/pigeon.geo.json");
    }

    @Override
    public Identifier getTextureResource(PigeonEntity object) {
        return new Identifier(FowlPlay.ID, "textures/entity/pigeon/pigeon.png");
    }

    @Override
    public Identifier getAnimationResource(PigeonEntity animatable) {
        return new Identifier(FowlPlay.ID, "animations/pigeon/pigeon.animation.json");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setCustomAnimations(PigeonEntity entity, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(entity, instanceId, animationEvent);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) animationEvent.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}
