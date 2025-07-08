package aqario.fowlplay.common.entity.ai.pathing;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.BirdPathNodeMaker;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNodeNavigator;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.tslat.smartbrainlib.api.core.navigation.ExtendedNavigator;
import org.jetbrains.annotations.Nullable;

public class FlightNavigation extends MobNavigation implements ExtendedNavigator {
    private static final int NODE_DISTANCE = 4;
    private static final int NODE_RADIUS = 4;
    private final FlyingBirdEntity bird;

    public FlightNavigation(FlyingBirdEntity bird, World world) {
        super(bird, world);
        this.bird = bird;
    }

    @Override
    public MobEntity getMob() {
        return this.entity;
    }

    @Nullable
    @Override
    public Path getCurrentPath() {
        return super.getCurrentPath();
    }

    @Override
    protected PathNodeNavigator createPathNodeNavigator(int maxVisitedNodes) {
        this.nodeMaker = new BirdPathNodeMaker();
        this.nodeMaker.setCanEnterOpenDoors(true);

//        return new PathNodeNavigator(this.nodeMaker, maxVisitedNodes);
        return this.createSmoothPathFinder(this.nodeMaker, maxVisitedNodes);
    }

    @Override
    public boolean startMovingTo(double x, double y, double z, double speed) {
        this.bird.getMoveControl().moveTo(x, y, z, speed);
        return true;
    }

    @Override
    public boolean startMovingTo(Entity entity, double speed) {
        this.bird.getMoveControl().moveTo(entity.getX(), entity.getY(), entity.getZ(), speed);
        return true;
    }

    @Override
    protected boolean canPathDirectlyThrough(Vec3d origin, Vec3d target) {
        return doesNotCollide(this.entity, origin, target, true);
    }

    @Override
    protected boolean isAtValidPosition() {
        return this.canSwim() && this.entity.isInFluid() || !this.entity.hasVehicle();
    }

    @Override
    protected Vec3d getPos() {
        return this.getMob().getPos();
    }

    @Override
    protected double adjustTargetY(Vec3d pos) {
        return pos.y;
    }

    @Nullable
    public Path findPathTo(BlockPos target, int distance) {
        return this.findPathTo(ImmutableSet.of(target), 32, false, distance);
    }

    @Override
    public void tick() {
        this.tickCount++;
        if(this.inRecalculationCooldown) {
            this.recalculatePath();
        }

        if(!this.isIdle()) {
            if(this.isAtValidPosition()) {
                this.continueFollowingPath();
            }
            else if(this.currentPath != null && !this.currentPath.isFinished()) {
                Vec3d vec3d = this.currentPath.getNodePosition(this.entity);
                if(this.entity.getBlockX() == MathHelper.floor(vec3d.x)
                    && this.entity.getBlockY() == MathHelper.floor(vec3d.y)
                    && this.entity.getBlockZ() == MathHelper.floor(vec3d.z)) {
                    this.currentPath.next();
                }
            }

            DebugInfoSender.sendPathfindingData(this.world, this.getMob(), this.getCurrentPath(), 0.1f);
            if(!this.isIdle()) {
                Vec3d vec3d = this.currentPath.getNodePosition(this.entity);
                this.entity.getMoveControl().moveTo(vec3d.x, vec3d.y, vec3d.z, this.speed);
            }
        }
    }

    @Override
    protected void continueFollowingPath() {
        final Vec3d safeSurfacePos = this.getPos();
        final int shortcutNode = this.getClosestVerticalTraversal(MathHelper.floor(safeSurfacePos.y));
        this.nodeReachProximity = this.entity.getWidth() > 0.75f ? this.entity.getWidth() / 2f : 0.75f - this.entity.getWidth() / 2f;

//        if (!this.attemptShortcut(shortcutNode, safeSurfacePos)) {
            if (this.isCloseToNextNode(NODE_RADIUS)/* || this.isAboutToTraverseVertically() && this.isCloseToNextNode(this.getNodeReachProximity())*/) {
                this.currentPath.setCurrentNodeIndex(this.currentPath.getCurrentNodeIndex() + NODE_DISTANCE);
            }
//        }

        this.checkTimeouts(safeSurfacePos);
    }

    @Override
    public boolean isCloseToNextNode(float distance) {
        final Vec3d nextNodePos = this.getEntityPosAtNode(this.getCurrentPath().getCurrentNodeIndex());

        return this.getPos().isInRange(nextNodePos, distance);
    }

    protected int getClosestVerticalTraversal(int safeSurfaceHeight) {
        final int nodesLength = this.currentPath.getLength();

        for (int nodeIndex = this.currentPath.getCurrentNodeIndex(); nodeIndex < nodesLength; nodeIndex++) {
            if (this.currentPath.getNode(nodeIndex).y != safeSurfaceHeight)
                return nodeIndex;
        }

        return nodesLength;
    }

    @Override
    public boolean isValidPosition(BlockPos pos) {
        return true;
    }
}
