package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.entity.ai.brain.task.*;
import aqario.fowlplay.common.util.Birds;
import aqario.fowlplay.core.FowlPlayActivities;
import aqario.fowlplay.core.FowlPlayEntityType;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import aqario.fowlplay.core.FowlPlaySensorType;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class PenguinBrain {
    private static final ImmutableList<SensorType<? extends Sensor<? super PenguinEntity>>> SENSORS = ImmutableList.of(
        SensorType.NEAREST_ITEMS,
        SensorType.NEAREST_ADULT,
        SensorType.IS_IN_WATER,
        FowlPlaySensorType.ATTACKED,
        FowlPlaySensorType.NEARBY_ADULTS,
        FowlPlaySensorType.NEARBY_LIVING_ENTITIES,
        FowlPlaySensorType.TEMPTING_PLAYER,
        FowlPlaySensorType.ATTACK_TARGETS
    );
    private static final ImmutableList<MemoryModuleType<?>> MEMORIES = ImmutableList.of(
        MemoryModuleType.LOOK_TARGET,
        MemoryModuleType.MOBS,
        MemoryModuleType.VISIBLE_MOBS,
        MemoryModuleType.WALK_TARGET,
        MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
        MemoryModuleType.PATH,
        MemoryModuleType.BREED_TARGET,
        MemoryModuleType.NEAREST_VISIBLE_PLAYER,
        MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER,
        MemoryModuleType.ATTACK_COOLING_DOWN,
        MemoryModuleType.NEAREST_VISIBLE_ADULT,
        MemoryModuleType.ATTACK_TARGET,
        MemoryModuleType.HAS_HUNTING_COOLDOWN,
        MemoryModuleType.TEMPTING_PLAYER,
        MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
        MemoryModuleType.GAZE_COOLDOWN_TICKS,
        MemoryModuleType.IS_TEMPTED,
        MemoryModuleType.HURT_BY,
        MemoryModuleType.HURT_BY_ENTITY,
        MemoryModuleType.NEAREST_ATTACKABLE,
        MemoryModuleType.IS_IN_WATER,
        MemoryModuleType.IS_PREGNANT,
        MemoryModuleType.IS_PANICKING,
        MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM,
        MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM,
        MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS,
        FowlPlayMemoryModuleType.SEES_FOOD,
        FowlPlayMemoryModuleType.CANNOT_PICKUP_FOOD
    );

    public static Brain.Profile<PenguinEntity> createProfile() {
        return Brain.createProfile(MEMORIES, SENSORS);
    }

    public static Brain<?> create(Brain<PenguinEntity> brain) {
        addCoreActivities(brain);
        addIdleActivities(brain);
        addSwimActivities(brain);
        addPickupFoodActivities(brain);
        addFightActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    public static void reset(PenguinEntity penguin) {
        Brain<PenguinEntity> brain = penguin.getBrain();
        Activity activity = brain.getFirstPossibleNonCoreActivity().orElse(null);
        brain.resetPossibleActivities(
            ImmutableList.of(
                Activity.IDLE,
                Activity.SWIM,
                FowlPlayActivities.PICK_UP,
                Activity.FIGHT
            )
        );
        if (activity == Activity.FIGHT && brain.getFirstPossibleNonCoreActivity().orElse(null) != Activity.FIGHT) {
            brain.remember(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, 20000L);
        }
    }

    private static void addCoreActivities(Brain<PenguinEntity> brain) {
        brain.setTaskList(
            Activity.CORE,
            0,
            ImmutableList.of(
                new BreatheAirTask(Birds.SWIM_SPEED),
                new FleeTask<>(Birds.RUN_SPEED),
                PickupFoodTask.run(Birds::canPickupFood),
                new LookAroundTask(45, 90),
                new MoveToTargetTask(),
                new TemptationCooldownTask(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
                new TemptationCooldownTask(MemoryModuleType.GAZE_COOLDOWN_TICKS)
            )
        );
    }

    private static void addIdleActivities(Brain<PenguinEntity> brain) {
        brain.setTaskList(
            Activity.IDLE,
            ImmutableList.of(
                Pair.of(0, SwimControlTask.startSwimming()),
                Pair.of(1, new BreedTask(FowlPlayEntityType.PENGUIN, Birds.WALK_SPEED, 10)),
                Pair.of(2, FindLookTargetTask.create(EntityType.PLAYER, 32.0F)),
                Pair.of(3, new TemptTask(penguin -> Birds.WALK_SPEED)),
                Pair.of(4, WalkTowardClosestAdultTask.create(Birds.FOLLOW_ADULT_RANGE, Birds.WALK_SPEED)),
                Pair.of(5, new RandomLookAroundTask(UniformIntProvider.create(150, 250), 30.0F, 0.0F, 0.0F)),
                Pair.of(6, UpdateAttackTargetTask.create(Birds::getAttackTarget)),
                Pair.of(
                    7,
                    new RandomTask<>(
                        ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT),
                        ImmutableList.of(
                            Pair.of(StrollTask.create(Birds.WALK_SPEED), 2),
                            Pair.of(GoTowardsLookTargetTask.create(Birds.WALK_SPEED, 10), 3),
                            Pair.of(SlideControlTask.toggleSliding(20), 5),
                            Pair.of(new WaitTask(400, 800), 5),
                            Pair.of(makeGoToWaterTask(), 6)
                        )
                    )
                )
            ),
            ImmutableSet.of(
                Pair.of(MemoryModuleType.IS_IN_WATER, MemoryModuleState.VALUE_ABSENT),
                Pair.of(FowlPlayMemoryModuleType.SEES_FOOD, MemoryModuleState.VALUE_ABSENT),
                Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_ABSENT)
            )
        );
    }

    private static void addSwimActivities(Brain<PenguinEntity> brain) {
        brain.setTaskList(
            Activity.SWIM,
            ImmutableList.of(
                Pair.of(0, SwimControlTask.stopSwimming()),
                Pair.of(1, WalkTowardClosestAdultTask.create(Birds.FOLLOW_ADULT_RANGE, Birds.SWIM_SPEED)),
                Pair.of(2, UpdateAttackTargetTask.create(Birds::getAttackTarget)),
                Pair.of(
                    3,
                    new RandomTask<>(
                        ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT),
                        ImmutableList.of(
                            Pair.of(GoToLandTask.create(32, Birds.SWIM_SPEED), 5),
                            Pair.of(PenguinSwimTask.create(Birds.SWIM_SPEED), 2)
                        )
                    )
                )
            ),
            ImmutableSet.of(
                Pair.of(MemoryModuleType.IS_IN_WATER, MemoryModuleState.VALUE_PRESENT),
                Pair.of(FowlPlayMemoryModuleType.SEES_FOOD, MemoryModuleState.VALUE_ABSENT),
                Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_ABSENT)
            )
        );
    }

    private static void addPickupFoodActivities(Brain<PenguinEntity> brain) {
        brain.setTaskList(
            FowlPlayActivities.PICK_UP,
            10,
            ImmutableList.of(
                SlideControlTask.startSliding(),
                GoToNearestWantedItemTask.create(
                    Birds::canPickupFood,
                    entity -> entity.isInsideWaterOrBubbleColumn() ? Birds.SWIM_SPEED : Birds.RUN_SPEED,
                    true,
                    Birds.ITEM_PICK_UP_RANGE
                ),
                ForgetTask.create(Predicate.not(Birds::canPickupFood), FowlPlayMemoryModuleType.SEES_FOOD)
            ),
            FowlPlayMemoryModuleType.SEES_FOOD
        );
    }

    private static void addFightActivities(Brain<PenguinEntity> brain) {
        brain.setTaskList(
            Activity.FIGHT,
            0,
            ImmutableList.of(
                SlideControlTask.startSliding(),
                ForgetAttackTargetTask.create(),
                RangedApproachTask.create(entity -> entity.isInsideWaterOrBubbleColumn() ? Birds.SWIM_SPEED : Birds.RUN_SPEED),
                MeleeAttackTask.create(20),
                ForgetTask.create(LookTargetUtil::hasBreedTarget, MemoryModuleType.ATTACK_TARGET)
            ),
            MemoryModuleType.ATTACK_TARGET
        );
    }

    private static CompositeTask<PenguinEntity> makeGoToWaterTask() {
        return new CompositeTask<>(
            ImmutableMap.of(MemoryModuleType.HAS_HUNTING_COOLDOWN, MemoryModuleState.VALUE_ABSENT),
            ImmutableSet.of(),
            CompositeTask.Order.ORDERED,
            CompositeTask.RunMode.TRY_ALL,
            ImmutableList.of(
                Pair.of(SlideControlTask.startSliding(), 1),
                Pair.of(SeekWaterTask.create(32, Birds.WALK_SPEED), 2)
            )
        );
    }

    public static class PenguinSwimTask {
        private static final int[][] SWIM_DISTANCES = new int[][]{{31, 15}};

        public static Task<PathAwareEntity> create(float speed) {
            return create(speed, PenguinSwimTask::findSwimTargetPos, Entity::isInsideWaterOrBubbleColumn);
        }

        private static SingleTickTask<PathAwareEntity> create(float speed, Function<PathAwareEntity, Vec3d> targetGetter, Predicate<PathAwareEntity> predicate) {
            return TaskTriggerer.task(
                instance -> instance.group(instance.queryMemoryAbsent(MemoryModuleType.WALK_TARGET)).apply(instance, memoryAccessor -> (world, entity, time) -> {
                    if (!predicate.test(entity)) {
                        return false;
                    }
                    else {
                        Optional<Vec3d> optional = Optional.ofNullable(targetGetter.apply(entity));
                        memoryAccessor.remember(optional.map(vec3d -> new WalkTarget(vec3d, speed, 0)));
                        return true;
                    }
                })
            );
        }

        @Nullable
        private static Vec3d findSwimTargetPos(PathAwareEntity entity) {
            Vec3d vec3d = null;
            Vec3d vec3d2 = null;

            for (int[] is : SWIM_DISTANCES) {
                if (vec3d == null) {
                    vec3d2 = LookTargetUtil.find(entity, is[0], is[1]);
                }
                else {
                    vec3d2 = entity.getPos().add(entity.getPos().relativize(vec3d).normalize().multiply(is[0], is[1], is[0]));
                }

                if (vec3d2 == null || entity.getWorld().getFluidState(BlockPos.ofFloored(vec3d2)).isEmpty()) {
                    return vec3d;
                }

                vec3d = vec3d2;
            }

            return vec3d2;
        }
    }
}
