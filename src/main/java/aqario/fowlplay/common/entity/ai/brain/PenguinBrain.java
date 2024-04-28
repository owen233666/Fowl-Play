package aqario.fowlplay.common.entity.ai.brain;

import aqario.fowlplay.common.entity.FowlPlayEntityType;
import aqario.fowlplay.common.entity.PenguinEntity;
import aqario.fowlplay.common.tags.FowlPlayItemTags;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.unmapped.C_lygsomtd;
import net.minecraft.unmapped.C_rcqaryar;
import net.minecraft.util.math.int_provider.UniformIntProvider;

public class PenguinBrain {
    private static final UniformIntProvider ADULT_FOLLOW_RANGE = UniformIntProvider.create(5, 16);
    private static final float PANICKING_SPEED = 1.5F;
    private static final float TEMPTED_SPEED = 0.8F;
    private static final float WALK_SPEED = 1.0F;
    private static final float SWIM_SPEED = 4.0F;

    public static Brain<?> create(Brain<PenguinEntity> brain) {
        addCoreActivities(brain);
        addIdleActivities(brain);
//        addSwimActivities(brain);
//        addFightActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<PenguinEntity> brain) {
        brain.setTaskList(
            Activity.CORE,
            0,
            ImmutableList.of(
                new WalkTask(PANICKING_SPEED),
                new LookAroundTask(45, 90),
                new WanderAroundTask(),
                new ReduceCooldownTask(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS)
            )
        );
    }

    private static void addIdleActivities(Brain<PenguinEntity> brain) {
        brain.setTaskList(
            Activity.IDLE,
            ImmutableList.of(
                Pair.of(0, C_lygsomtd.follow(EntityType.PLAYER, 16.0f, UniformIntProvider.create(30, 60))),
                Pair.of(1, new BreedTask(FowlPlayEntityType.PENGUIN, WALK_SPEED)),
                Pair.of(2, new TemptTask(penguin -> TEMPTED_SPEED)),
                Pair.of(3, WalkTowardClosestAdultTask.create(ADULT_FOLLOW_RANGE, WALK_SPEED)),
                Pair.of(
                    4,
                    new CompositeTask<>(
                        ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT),
                        ImmutableSet.of(),
                        CompositeTask.Order.ORDERED,
                        CompositeTask.RunMode.TRY_ALL,
                        ImmutableList.of(
                            Pair.of(StrollTask.createDynamicRadius(SWIM_SPEED), 2),
                            Pair.of(StrollTask.create(WALK_SPEED), 2),
                            Pair.of(GoTowardsLookTarget.create(WALK_SPEED, 3), 3),
                            Pair.of(C_rcqaryar.predicate(Entity::isInsideWaterOrBubbleColumn), 5),
                            Pair.of(new WaitTask(400, 1200), 5)
                        )
                    )
                )
            ),
            ImmutableSet.of(
                Pair.of(MemoryModuleType.IS_IN_WATER, MemoryModuleState.VALUE_ABSENT)
            )
        );
    }

//    private static void addSwimActivities(Brain<PenguinEntity> brain) {
//        brain.setTaskList(
//            Activity.SWIM,
//            ImmutableList.of(
//                Pair.of(0, new TimeLimitedTask<LivingEntity>(new FollowMobTask(EntityType.PLAYER, 6.0f), UniformIntProvider.create(30, 60))),
//                Pair.of(1, new TemptTask(penguin -> TEMPTED_SPEED)),
//                Pair.of(2, new UpdateAttackTargetTask<>(PenguinBrain::isNotBreeding, penguin -> penguin.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_ATTACKABLE))),
//                Pair.of(3, new FindLandTask(8, 1.5f)),
//                Pair.of(
//                    5,
//                    new CompositeTask<>(
//                        ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT),
//                        ImmutableSet.of(),
//                        CompositeTask.Order.ORDERED,
//                        CompositeTask.RunMode.TRY_ALL,
//                        ImmutableList.of(
//                            Pair.of(new AquaticStrollTask(SWIM_SPEED), 1),
//                            Pair.of(new StrollTask(WALK_SPEED, true), 1),
//                            Pair.of(new GoTowardsLookTarget(1.0f, 3), 1),
//                            Pair.of(new ConditionalTask<LivingEntity>(Entity::isInsideWaterOrBubbleColumn, new WaitTask(30, 60)), 5)
//                        )
//                    )
//                )
//            ),
//            ImmutableSet.of(
//                Pair.of(MemoryModuleType.IS_IN_WATER, MemoryModuleState.VALUE_PRESENT)
//            )
//        );
//    }

//    private static void addGroupActivities(Brain<PenguinEntity> brain) {
//        brain.setTaskList(
//            Activity.MEET,
//            ImmutableList.of(
//                Pair.of(0, new TimeLimitedTask<LivingEntity>(new FollowMobTask(EntityType.PLAYER, 8.0f), UniformIntProvider.create(30, 60))),
//                Pair.of(0, new BreedTask(FowlPlayEntityType.PENGUIN, WALK_SPEED)),
//                Pair.of(1, new TemptTask(penguin -> TEMPTED_SPEED)),
//                Pair.of(2, new WalkTowardClosestAdultTask<>(UniformIntProvider.create(5, 16), WALK_SPEED)),
//                Pair.of(3, new FindLandTask(6, WALK_SPEED)),
//                Pair.of(
//                    4,
//                    new RandomTask<>(
//                        ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT),
//                        ImmutableList.of(
//                            Pair.of(new StrollTask(WALK_SPEED), 1),
//                            Pair.of(new GoTowardsLookTarget(WALK_SPEED, 16), 1),
//                            Pair.of(new ConditionalTask<>(Entity::isOnGround, new WaitTask(900, 1200)), 2)
//                        )
//                    )
//                )
//            )
//        );
//    }

//    private static void addFightActivities(Brain<PenguinEntity> brain) {
//        brain.setTaskList(
//            Activity.FIGHT,
//            0,
//            ImmutableList.of(
//                new ForgetAttackTargetTask<>(),
//                new RangedApproachTask(PenguinBrain::getChaseSpeed),
//                new MeleeAttackTask(20),
//                new ForgetTask<>(LookTargetUtil::hasBreedTarget, MemoryModuleType.ATTACK_TARGET)
//            ),
//            MemoryModuleType.ATTACK_TARGET
//        );
//    }

    private static float getChaseSpeed(LivingEntity entity) {
        return entity.isInsideWaterOrBubbleColumn() ? 0.6F : 0.15F;
    }

    public static void updateActivities(PenguinEntity penguin) {
        penguin.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE, Activity.FIGHT));
    }

    public static Ingredient getTemptIngredient() {
        return Ingredient.ofTag(FowlPlayItemTags.PENGUIN_TEMPT_ITEMS);
    }
}
