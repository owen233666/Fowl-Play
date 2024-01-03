package aqario.fowlplay.client.model;

import net.minecraft.util.Identifier;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.SeagullEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class SeagullModel extends AnimatedGeoModel<SeagullEntity> {
    @Override
    public Identifier getModelResource(SeagullEntity object) {
        return object.isFlying() ? new Identifier(FowlPlay.ID, "geo/seagull/seagull_flying.geo.json") : new Identifier(FowlPlay.ID, "geo/seagull/seagull.geo.json");
    }

    @Override
    public Identifier getTextureResource(SeagullEntity object) {
        return new Identifier(FowlPlay.ID, "textures/entity/seagull/seagull.png");
    }

    @Override
    public Identifier getAnimationResource(SeagullEntity animatable) {
        return new Identifier(FowlPlay.ID, "animations/seagull/seagull.animation.json");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setCustomAnimations(SeagullEntity entity, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(entity, instanceId, animationEvent);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) animationEvent.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}
