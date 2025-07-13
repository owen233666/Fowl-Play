package aqario.fowlplay.common.entity.ai.pathing;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.util.Birds;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import net.tslat.smartbrainlib.api.core.navigation.SmoothAmphibiousPathNavigation;
import org.jetbrains.annotations.Nullable;

public class AmphibiousNavigation extends SmoothAmphibiousPathNavigation {
    public AmphibiousNavigation(MobEntity mob, World level) {
        super(mob, level);
    }

    @Override
    public @Nullable Path patchPath(@Nullable Path path) {
        Path newPath = super.patchPath(path);
        if(newPath == null) {
            return null;
        }
        // noinspection ConstantConditions
        Path.DebugNodeInfo debugNodeInfo = path.getDebugNodeInfos();
        if(debugNodeInfo != null) {
            newPath.setDebugInfo(debugNodeInfo.openSet(), debugNodeInfo.closedSet(), debugNodeInfo.targetNodes());
        }
        return newPath;
    }

    @Override
    public boolean startMovingAlong(@Nullable Path path, double speed) {
        if(path != null && this.entity instanceof FlyingBirdEntity flyingBird) {
            Birds.tryFlyingAlongPath(flyingBird, path);
        }
        return super.startMovingAlong(path, speed);
    }
}
