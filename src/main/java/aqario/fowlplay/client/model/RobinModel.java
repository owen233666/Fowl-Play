package aqario.fowlplay.client.model;

import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.RobinEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class RobinModel extends AnimatedGeoModel<RobinEntity> {
    @Override
    public Identifier getModelResource(RobinEntity object) {
        return new Identifier(FowlPlay.ID, "geo/robin/robin.geo.json");
    }

    @Override
    public Identifier getTextureResource(RobinEntity object) {
        return new Identifier(FowlPlay.ID, "textures/entity/robin/" + object.getVariant().getId() + "_robin.png");
    }

    @Override
    public Identifier getAnimationResource(RobinEntity animatable) {
        return new Identifier(FowlPlay.ID, "animations/robin/robin.animation.json");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setCustomAnimations(RobinEntity entity, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(entity, instanceId, animationEvent);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) animationEvent.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}
