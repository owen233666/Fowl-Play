package aqario.fowlplay.common.entity.ai.brain;

import aqario.fowlplay.common.entity.FowlPlayEntityType;
import aqario.fowlplay.common.entity.SeagullEntity;
import aqario.fowlplay.common.tags.FowlPlayItemTags;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ReportingTaskControl;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.unmapped.C_lygsomtd;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.int_provider.UniformIntProvider;
import net.minecraft.util.random.RandomGenerator;

public class SeagullBrain {
    private static final ImmutableList<SensorType<? extends Sensor<? super SeagullEntity>>> SENSORS = ImmutableList.of(
        SensorType.NEAREST_LIVING_ENTITIES,
        SensorType.NEAREST_ADULT,
        SensorType.HURT_BY,
        SensorType.IS_IN_WATER
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
        MemoryModuleType.IS_PANICKING
    );
    private static final UniformIntProvider RUN_FROM_PLAYER_MEMORY_DURATION = TimeHelper.betweenSeconds(5, 7);
    private static final UniformIntProvider FOLLOW_ADULT_RANGE = UniformIntProvider.create(5, 16);
    private static final float PANICKING_SPEED = 1.6F;
    private static final float TEMPTED_SPEED = 1.2F;
    private static final float WALK_SPEED = 1.0F;

    public static void initialize(SeagullEntity seagull, RandomGenerator random) {
    }

    public static Brain.Profile<SeagullEntity> createProfile() {
        return Brain.createProfile(MEMORIES, SENSORS);
    }

    public static Brain<?> create(Brain<SeagullEntity> brain) {
        addCoreActivities(brain);
        addIdleActivities(brain);
        addAvoidActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<SeagullEntity> brain) {
        brain.setTaskList(
            Activity.CORE,
            0,
            ImmutableList.of(
                new WalkTask<>(PANICKING_SPEED),
                new LookAroundTask(45, 90),
//                makeRunFromPlayerTask(),
                new ReduceCooldownTask(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
                new ReduceCooldownTask(MemoryModuleType.GAZE_COOLDOWN_TICKS)
            )
        );
    }

    private static void addIdleActivities(Brain<SeagullEntity> brain) {
        brain.setTaskList(
            Activity.IDLE,
            ImmutableList.of(
                Pair.of(1, new BreedTask(FowlPlayEntityType.SEAGULL, WALK_SPEED, 20)),
                Pair.of(2, C_lygsomtd.method_47069(EntityType.PLAYER, 16.0f, UniformIntProvider.create(30, 60))),
                Pair.of(3, new TemptTask(seagull -> TEMPTED_SPEED)),
                Pair.of(4, WalkTowardClosestAdultTask.create(FOLLOW_ADULT_RANGE, WALK_SPEED)),
                Pair.of(5, new RandomLookAroundTask(UniformIntProvider.create(150, 250), 30.0F, 0.0F, 0.0F)),
                Pair.of(
                    6,
                    new RandomTask<>(
                        ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT),
                        ImmutableList.of(
                            Pair.of(MeanderTask.create(WALK_SPEED), 1),
                            Pair.of(GoTowardsLookTarget.create(WALK_SPEED, 3), 2),
                            Pair.of(TaskBuilder.triggerIf(Entity::isInsideWaterOrBubbleColumn), 2),
                            Pair.of(new WaitTask(100, 300), 2)
                        )
                    )
                )
            ),
            ImmutableSet.of(
                Pair.of(MemoryModuleType.IS_IN_WATER, MemoryModuleState.VALUE_ABSENT)
            )
        );
    }

    private static void addAvoidActivities(Brain<SeagullEntity> brain) {
        brain.setTaskList(
            Activity.AVOID,
            10,
            ImmutableList.of(
                GoToRememberedPositionTask.toEntity(MemoryModuleType.AVOID_TARGET, PANICKING_SPEED, 15, true),
                makeRandomFollowTask(),
                makeRandomWanderTask(),
                ForgetTask.run(entity -> true, MemoryModuleType.AVOID_TARGET)
            ),
            MemoryModuleType.AVOID_TARGET
        );
    }

    private static ImmutableList<Pair<ReportingTaskControl<LivingEntity>, Integer>> createLookTasks() {
        return ImmutableList.of(
            Pair.of(FollowMobTask.createMatchingType(FowlPlayEntityType.SEAGULL, 8.0F), 1),
            Pair.of(FollowMobTask.create(8.0F), 1)
        );
    }

    private static RandomTask<LivingEntity> makeRandomFollowTask() {
        return new RandomTask<>(
            ImmutableList.<Pair<? extends TaskControl<? super LivingEntity>, Integer>>builder().addAll(createLookTasks()).add(Pair.of(new WaitTask(30, 60), 1)).build()
        );
    }

    private static RandomTask<SeagullEntity> makeRandomWanderTask() {
        return new RandomTask<>(
            ImmutableList.of(
                Pair.of(MeanderTask.create(0.6F), 2),
                Pair.of(TaskBuilder.sequence(livingEntity -> true, GoTowardsLookTarget.create(0.6F, 3)), 2),
                Pair.of(new WaitTask(30, 60), 1)
            )
        );
    }

    private static TaskControl<SeagullEntity> makeRunFromPlayerTask() {
        return MemoryTransferTask.create(
            SeagullBrain::getNearestPlayer, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.AVOID_TARGET, RUN_FROM_PLAYER_MEMORY_DURATION
        );
    }

    private static boolean getNearestPlayer(SeagullEntity seagull) {
        Brain<SeagullEntity> brain = seagull.getBrain();
        if (brain.hasMemoryModule(MemoryModuleType.NEAREST_VISIBLE_PLAYER)) {
            LivingEntity livingEntity = brain.getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER).get();
            return seagull.isInRange(livingEntity, 10.0);
        }
        else {
            return false;
        }
    }

    public static void reset(SeagullEntity seagull) {
        seagull.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE));
    }

    protected static void trusting(SeagullEntity seagull) {
        seagull.getBrain().remember(MemoryModuleType.LIKED_PLAYER, seagull.getTrustedUuid());
    }

    public static Ingredient getTemptIngredient() {
        return Ingredient.ofTag(FowlPlayItemTags.SEAGULL_FOOD);
    }
}
