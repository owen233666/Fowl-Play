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
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class PenguinBrain {
    private static final UniformIntProvider ADULT_FOLLOW_RANGE = UniformIntProvider.create(5, 16);
    private static final float PANICKING_SPEED = 1.5F;
    private static final float TEMPTED_SPEED = 0.8F;
    private static final float WALK_SPEED = 1.0F;
    private static final float SWIM_SPEED = 4.0F;

    public static Brain<?> create(Brain<PenguinEntity> brain) {
        addCoreActivities(brain);
        addIdleActivities(brain);
        addFightActivities(brain);
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
                new TemptationCooldownTask(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS)
            )
        );
    }

    private static void addIdleActivities(Brain<PenguinEntity> brain) {
        brain.setTaskList(
            Activity.IDLE,
            ImmutableList.of(
                Pair.of(0, new UpdateAttackTargetTask<>(entity -> entity.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_ATTACKABLE))),
                Pair.of(1, new TimeLimitedTask<>(new FollowMobTask(EntityType.PLAYER, 16.0f), UniformIntProvider.create(30, 60))),
                Pair.of(2, new BreedTask(FowlPlayEntityType.PENGUIN, WALK_SPEED)),
                Pair.of(3, new TemptTask(penguin -> TEMPTED_SPEED)),
                Pair.of(4, new WalkTowardClosestAdultTask<>(ADULT_FOLLOW_RANGE, WALK_SPEED)),
                Pair.of(
                    5,
                    new CompositeTask<>(
                        ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT),
                        ImmutableSet.of(),
                        CompositeTask.Order.ORDERED,
                        CompositeTask.RunMode.TRY_ALL,
                        ImmutableList.of(
                            Pair.of(new AquaticStrollTask(SWIM_SPEED), 2),
                            Pair.of(new StrollTask(WALK_SPEED), 2),
                            Pair.of(new GoTowardsLookTarget(WALK_SPEED, 3), 3),
                            Pair.of(new ConditionalTask<>(Entity::isInsideWaterOrBubbleColumn, new WaitTask(30, 60)), 5),
                            Pair.of(new WaitTask(600, 800), 5)
//                            Pair.of(new ConditionalTask<>(entity -> true, new DebugWaitTask(600, 800)), 5)
                        )
                    )
                )
            ),
            ImmutableSet.of(
                Pair.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT)
            )
        );
    }

    private static void addGroupActivities(Brain<PenguinEntity> brain) {
        brain.setTaskList(
            Activity.MEET,
            ImmutableList.of(
                Pair.of(0, new TimeLimitedTask<LivingEntity>(new FollowMobTask(EntityType.PLAYER, 8.0f), UniformIntProvider.create(30, 60))),
                Pair.of(0, new BreedTask(FowlPlayEntityType.PENGUIN, WALK_SPEED)),
                Pair.of(1, new TemptTask(penguin -> TEMPTED_SPEED)),
                Pair.of(2, new WalkTowardClosestAdultTask<>(UniformIntProvider.create(5, 16), WALK_SPEED)),
                Pair.of(3, new FindLandTask(6, WALK_SPEED)),
                Pair.of(
                    4,
                    new RandomTask<>(
                        ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT),
                        ImmutableList.of(
                            Pair.of(new StrollTask(WALK_SPEED), 1),
                            Pair.of(new GoTowardsLookTarget(WALK_SPEED, 16), 1),
                            Pair.of(new ConditionalTask<>(Entity::isOnGround, new WaitTask(900, 1200)), 2)
                        )
                    )
                )
            )
        );
    }

    private static void addSwimActivities(Brain<PenguinEntity> brain) {
        brain.setTaskList(
            Activity.SWIM,
            ImmutableList.of(
                Pair.of(0, new TimeLimitedTask<LivingEntity>(new FollowMobTask(EntityType.PLAYER, 6.0f), UniformIntProvider.create(30, 60))),
                Pair.of(1, new TemptTask(penguin -> 1.25f)),
                Pair.of(2, new UpdateAttackTargetTask<>(PenguinBrain::isNotBreeding, penguin -> penguin.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_ATTACKABLE))),
                Pair.of(3, new FindLandTask(8, 1.5f)),
                Pair.of(
                    5,
                    new CompositeTask<>(
                        ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT),
                        ImmutableSet.of(),
                        CompositeTask.Order.ORDERED,
                        CompositeTask.RunMode.TRY_ALL,
                        ImmutableList.of(
                            Pair.of(new AquaticStrollTask(0.75f), 1),
                            Pair.of(new StrollTask(1.0f, true), 1),
                            Pair.of(new GoTowardsLookTarget(1.0f, 3), 1),
                            Pair.of(new ConditionalTask<LivingEntity>(Entity::isInsideWaterOrBubbleColumn, new WaitTask(30, 60)), 5)
                        )
                    )
                )
            ),
            ImmutableSet.of(
                Pair.of(MemoryModuleType.LONG_JUMP_MID_JUMP, MemoryModuleState.VALUE_ABSENT), Pair.of(MemoryModuleType.IS_IN_WATER, MemoryModuleState.VALUE_PRESENT)
            )
        );
    }

    private static void addFightActivities(Brain<PenguinEntity> brain) {
        brain.setTaskList(
            Activity.FIGHT,
            0,
            ImmutableList.of(
                new ForgetAttackTargetTask<>(),
                new RangedApproachTask(PenguinBrain::getChaseSpeed),
                new MeleeAttackTask(20),
                new ForgetTask<>(LookTargetUtil::method_41331, MemoryModuleType.ATTACK_TARGET)
            ),
            MemoryModuleType.ATTACK_TARGET
        );
    }

    private static float getChaseSpeed(LivingEntity entity) {
        return entity.isInsideWaterOrBubbleColumn() ? 0.6F : 0.15F;
    }

    private static boolean isNotBreeding(PenguinEntity penguin) {
        return !LookTargetUtil.method_41331(penguin);
    }

    public static void updateActivities(PenguinEntity penguin) {
        penguin.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE, Activity.FIGHT));
    }

    public static Ingredient getTemptIngredient() {
        return Ingredient.ofTag(FowlPlayItemTags.PENGUIN_TEMPT_ITEMS);
    }
}
