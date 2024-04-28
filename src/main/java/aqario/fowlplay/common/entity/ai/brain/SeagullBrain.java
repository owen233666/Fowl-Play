//package aqario.fowlplay.common.entity.ai.brain;
//
//import aqario.fowlplay.common.entity.FowlPlayEntityType;
//import aqario.fowlplay.common.entity.SeagullEntity;
//import aqario.fowlplay.common.tags.FowlPlayItemTags;
//import com.google.common.collect.ImmutableList;
//import com.google.common.collect.ImmutableMap;
//import com.google.common.collect.ImmutableSet;
//import com.mojang.datafixers.util.Pair;
//import net.minecraft.entity.EntityType;
//import net.minecraft.entity.LivingEntity;
//import net.minecraft.entity.ai.brain.Activity;
//import net.minecraft.entity.ai.brain.Brain;
//import net.minecraft.entity.ai.brain.MemoryModuleState;
//import net.minecraft.entity.ai.brain.MemoryModuleType;
//import net.minecraft.entity.ai.brain.task.*;
//import net.minecraft.recipe.Ingredient;
//import net.minecraft.util.math.intprovider.UniformIntProvider;
//import net.minecraft.util.random.RandomGenerator;
//
//public class SeagullBrain {
//
//    public static Brain<?> create(Brain<SeagullEntity> brain) {
//        SeagullBrain.addCoreActivities(brain);
//        SeagullBrain.addIdleActivities(brain);
//        SeagullBrain.addFightActivities(brain);
//        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
//        brain.setDefaultActivity(Activity.IDLE);
//        brain.resetPossibleActivities();
//        return brain;
//    }
//
//    private static void addCoreActivities(Brain<SeagullEntity> brain) {
//        brain.setTaskList(
//            Activity.CORE,
//            0,
//            ImmutableList.of(
//                new LookAroundTask(45, 90),
//                new WanderAroundTask(),
//                new TemptationCooldownTask(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS)
//            )
//        );
//    }
//
//    private static void addIdleActivities(Brain<SeagullEntity> brain) {
//        brain.setTaskList(Activity.IDLE, ImmutableList.of(
//            Pair.of(0, new TimeLimitedTask<LivingEntity>(new FollowMobTask(EntityType.PLAYER, 6.0f), UniformIntProvider.create(30, 60))),
//            Pair.of(0, new BreedTask(FowlPlayEntityType.PENGUIN, 1f)),
//            Pair.of(1, new TemptTask(penguin -> 1f)),
////            Pair.of(2, new FleeTask<SeagullEntity>(SeagullEntity.class, PlayerEntity.class, 16f, 1.6, 1.8, entity -> SeagullEntity.NOTICEABLE_PLAYER_FILTER.test(entity) && !brain.remember(trusting()).canTrust(entity.getUuid()))),
//            Pair.of(3, new FindLandTask(6, 1f)),
//            Pair.of(4, new RandomTask<>(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT),
//                ImmutableList.of(
//                    Pair.of(new StrollTask(1f), 1),
//                    Pair.of(new GoTowardsLookTarget(1f, 3), 1)
//                ))
//            )
//        ));
//    }
//
//    private static void addFightActivities(Brain<SeagullEntity> brain) {
//        brain.setTaskList(Activity.FIGHT, 0,
//            ImmutableList.of(
//                new ForgetAttackTargetTask<>(),
//                new RangedApproachTask(SeagullBrain::getTargetApproachingSpeed),
//                new MeleeAttackTask(20),
//                new ForgetTask<>(null, MemoryModuleType.ATTACK_TARGET)),
//            MemoryModuleType.ATTACK_TARGET
//        );
//    }
//
//
///*    public static void updateActivities(SeagullEntity seagull) {
//        Brain<SeagullEntity> brain = seagull.getBrain();
//        Activity activity = brain.getFirstPossibleNonCoreActivity().orElse(null);
//        if (activity == Activity.FIGHT && brain.getFirstPossibleNonCoreActivity().orElse(null) != Activity.FIGHT) {
//                brain.remember(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, 2400L);
//        }
//    }*/
//
//    private static float getTargetApproachingSpeed(LivingEntity entity) {
//        return entity.isInsideWaterOrBubbleColumn() ? 0.6f : 0.15f;
//    }
//
//    protected static void trusting(SeagullEntity seagull, RandomGenerator random) {
////        seagull.getBrain().remember(MemoryModuleType.LIKED_PLAYER, seagull.getTrustedUuids());
//    }
//
//    private static float getTemptedSpeed(SeagullEntity entity) {
//        return entity.isFlying() ? 0.5f : 0.2f;
//    }
//
//    public static Ingredient getTemptItems() {
//        return Ingredient.ofTag(FowlPlayItemTags.SEAGULL_TEMPT_ITEMS);
//    }
//}
