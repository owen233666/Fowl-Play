package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.entity.ai.brain.FowlPlayActivities;
import aqario.fowlplay.common.entity.ai.brain.FowlPlayMemoryModuleType;
import aqario.fowlplay.common.entity.ai.brain.sensor.FowlPlaySensorType;
import aqario.fowlplay.common.entity.ai.brain.task.LocateFoodTask;
import aqario.fowlplay.common.tags.FowlPlayBlockTags;
import aqario.fowlplay.common.tags.FowlPlayItemTags;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.unmapped.C_lygsomtd;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.int_provider.UniformIntProvider;
import net.minecraft.world.World;

import java.util.Optional;

public class PenguinBrain {
    private static final ImmutableList<SensorType<? extends Sensor<? super PenguinEntity>>> SENSORS = ImmutableList.of(
        SensorType.NEAREST_LIVING_ENTITIES,
        SensorType.NEAREST_ITEMS,
        SensorType.NEAREST_ADULT,
        SensorType.HURT_BY,
        SensorType.IS_IN_WATER,
        FowlPlaySensorType.PENGUIN_TEMPTATIONS,
        FowlPlaySensorType.PENGUIN_ATTACKABLES
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
    private static final UniformIntProvider FOLLOW_ADULT_RANGE = UniformIntProvider.create(5, 16);
    private static final int PICK_UP_RANGE = 32;
    private static final float RUN_SPEED = 1.5F;
    private static final float TEMPTED_SPEED = 0.8F;
    private static final float WALK_SPEED = 1.0F;
    private static final float SWIM_SPEED = 4.0F;

    public static void init() {
    }

    public static Brain.Profile<PenguinEntity> createProfile() {
        return Brain.createProfile(MEMORIES, SENSORS);
    }

    public static Brain<?> create(Brain<PenguinEntity> brain) {
        addCoreActivities(brain);
        addIdleActivities(brain);
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
        brain.resetPossibleActivities(ImmutableList.of(Activity.IDLE, FowlPlayActivities.PICKUP_FOOD, Activity.FIGHT));
        if (activity == Activity.FIGHT && brain.getFirstPossibleNonCoreActivity().orElse(null) != Activity.FIGHT) {
            brain.remember(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, 2400L);
        }
    }

    private static void addCoreActivities(Brain<PenguinEntity> brain) {
        brain.setTaskList(
            Activity.CORE,
            0,
            ImmutableList.of(
                new WalkTask<>(RUN_SPEED),
                LocateFoodTask.run(),
                new LookAroundTask(45, 90),
                new WanderAroundTask(),
                new ReduceCooldownTask(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
                new ReduceCooldownTask(MemoryModuleType.GAZE_COOLDOWN_TICKS)
            )
        );
    }

    private static void addIdleActivities(Brain<PenguinEntity> brain) {
        brain.setTaskList(
            Activity.IDLE,
            ImmutableList.of(
                Pair.of(0, C_lygsomtd.method_47069(EntityType.PLAYER, 16.0f, UniformIntProvider.create(30, 60))),
                Pair.of(1, new BreedTask(FowlPlayEntityType.PENGUIN, WALK_SPEED, 2)),
                Pair.of(2, new TemptTask(penguin -> TEMPTED_SPEED)),
                Pair.of(3, WalkTowardClosestAdultTask.create(FOLLOW_ADULT_RANGE, WALK_SPEED)),
                Pair.of(4, new RandomLookAroundTask(UniformIntProvider.create(150, 250), 30.0F, 0.0F, 0.0F)),
                Pair.of(5, UpdateAttackTargetTask.create(PenguinBrain::getAttackTarget)),
                Pair.of(
                    6,
                    new RandomTask<>(
                        ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT),
//                        ImmutableSet.of(),
//                        CompositeTask.Order.ORDERED,
//                        CompositeTask.RunMode.TRY_ALL,
                        ImmutableList.of(
                            Pair.of(MeanderTask.createSwim(SWIM_SPEED), 2),
                            Pair.of(MeanderTask.create(WALK_SPEED, true), 2),
                            Pair.of(GoTowardsLookTarget.create(PenguinBrain::canGoToLookTarget, entity -> entity.isInsideWaterOrBubbleColumn() ? SWIM_SPEED : WALK_SPEED, 1), 3),
//                            Pair.of(TaskBuilder.triggerIf(Entity::isInsideWaterOrBubbleColumn), 5),
                            Pair.of(new RandomSlideTask(20), 5),
                            Pair.of(new PenguinWaitTask(400, 800), 5)
                        )
                    )
                )
            ),
            ImmutableSet.of(
//                Pair.of(MemoryModuleType.IS_IN_WATER, MemoryModuleState.VALUE_ABSENT),
                Pair.of(FowlPlayMemoryModuleType.SEES_FOOD, MemoryModuleState.VALUE_ABSENT),
                Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_ABSENT)
            )
        );
    }

    private static void addPickupFoodActivities(Brain<PenguinEntity> brain) {
        brain.setTaskList(
            FowlPlayActivities.PICKUP_FOOD,
            10,
            ImmutableList.of(
                WalkToNearestVisibleWantedItemTask.create(PenguinBrain::doesNotHaveFoodInHand, RUN_SPEED, true, PICK_UP_RANGE),
                ForgetTask.run(PenguinBrain::noFoodInRange, FowlPlayMemoryModuleType.SEES_FOOD)
            ),
            FowlPlayMemoryModuleType.SEES_FOOD
        );
    }

    private static void addFightActivities(Brain<PenguinEntity> brain) {
        brain.setTaskList(
            Activity.FIGHT,
            0,
            ImmutableList.of(
                ForgetAttackTargetTask.create(),
                RangedApproachTask.create(SWIM_SPEED),
                MeleeAttackTask.create(20),
                ForgetTask.run(LookTargetUtil::isValidBreedingTarget, MemoryModuleType.ATTACK_TARGET)
            ),
            MemoryModuleType.ATTACK_TARGET
        );
    }

    private static boolean canGoToLookTarget(LivingEntity entity) {
        World world = entity.getWorld();
        Optional<LookTarget> optional = entity.getBrain().getOptionalMemory(MemoryModuleType.LOOK_TARGET);
        if (optional.isPresent()) {
            BlockPos blockPos = optional.get().getBlockPos();
            return world.isWater(blockPos) == entity.isInsideWaterOrBubbleColumn();
        }
        else {
            return false;
        }
    }

    private static Optional<? extends LivingEntity> getAttackTarget(PenguinEntity penguin) {
        return LookTargetUtil.isValidBreedingTarget(penguin) ? Optional.empty() : penguin.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }

    private static boolean noFoodInRange(PenguinEntity penguin) {
        Optional<ItemEntity> item = penguin.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
        return item.isEmpty() || !item.get().isInRange(penguin, PICK_UP_RANGE);
    }

    private static boolean doesNotHaveFoodInHand(PenguinEntity penguin) {
        return !getFood().test(penguin.getMainHandStack());
    }

    public static Ingredient getFood() {
        return Ingredient.ofTag(FowlPlayItemTags.PENGUIN_FOOD);
    }

    public static class RandomSlideTask extends Task<PenguinEntity> {
        private final int minimalSlideTicks;

        public RandomSlideTask(int seconds) {
            super(ImmutableMap.of());
            this.minimalSlideTicks = seconds * 20;
        }

        @Override
        protected boolean shouldRun(ServerWorld world, PenguinEntity penguin) {
            return !penguin.isTouchingWater()
                && penguin.getAnimationTicks() >= (long) this.minimalSlideTicks
                && !penguin.hasPassengers()
                && penguin.isOnGround()
                && (penguin.isSliding() || world.getBlockState(penguin.getBlockPos()).isIn(FowlPlayBlockTags.PENGUINS_SLIDE_ON));
        }

        @Override
        protected void run(ServerWorld serverWorld, PenguinEntity penguin, long l) {
            if (penguin.isSliding()) {
                penguin.standUp();
            }
            else {
                penguin.startSliding();
            }
        }
    }

    public static class PenguinWaitTask implements TaskControl<PenguinEntity> {
        private final int minDuration;
        private final int maxDuration;
        private Task.Status status = Task.Status.STOPPED;
        private long endTimestamp;

        public PenguinWaitTask(int minRunTime, int maxRunTime) {
            this.minDuration = minRunTime;
            this.maxDuration = maxRunTime;
        }

        @Override
        public Task.Status getStatus() {
            return this.status;
        }

        @Override
        public final boolean tryStart(ServerWorld world, PenguinEntity penguin, long time) {
            if (penguin.isInsideWaterOrBubbleColumn()) {
                return false;
            }
            this.status = Task.Status.RUNNING;
            int i = this.minDuration + world.getRandom().nextInt(this.maxDuration + 1 - this.minDuration);
            this.endTimestamp = time + (long) i;
            return true;
        }

        @Override
        public final void tick(ServerWorld world, PenguinEntity penguin, long time) {
            if (time > this.endTimestamp) {
                this.stop(world, penguin, time);
            }
        }

        @Override
        public final void stop(ServerWorld world, PenguinEntity penguin, long time) {
            this.status = Task.Status.STOPPED;
        }

        @Override
        public String debugString() {
            return this.getClass().getSimpleName();
        }
    }
}
