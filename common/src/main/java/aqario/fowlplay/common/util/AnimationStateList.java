package aqario.fowlplay.common.util;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectIterators;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AnimationState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class AnimationStateList implements Iterable<AnimationState> {
    private final List<Entry> entries;
    private final RandomSource random = RandomSource.createNewThreadLocalInstance();

    public AnimationStateList(AnimationState... states) {
        this.entries = new ObjectArrayList<>(states.length);

        for(AnimationState state : states) {
            this.entries.add(new Entry(state, 1));
        }
    }

    @SafeVarargs
    public AnimationStateList(Pair<AnimationState, Integer>... entries) {
        this.entries = new ObjectArrayList<>(entries.length);

        for(Pair<AnimationState, Integer> entry : entries) {
            this.entries.add(new Entry(entry.getFirst(), entry.getSecond()));
        }
    }

    public AnimationStateList(Collection<Pair<AnimationState, Integer>> entries) {
        this.entries = new ObjectArrayList<>(entries.size());

        for(Pair<AnimationState, Integer> entry : entries) {
            this.entries.add(new Entry(entry.getFirst(), entry.getSecond()));
        }
    }

    public AnimationStateList randomize() {
        this.entries.forEach(entry -> entry.setRandomizedWeight(this.random.nextFloat()));
        this.entries.sort(Comparator.comparingDouble(Entry::getRandomizedWeight));

        return this;
    }

    public boolean add(AnimationState entry, int weight) {
        return this.entries.add(new Entry(entry, weight));
    }

    public void startRandom(int tickCount) {
        this.getRandom().ifPresent(animState -> animState.start(tickCount));
    }

    @NotNull
    public Optional<AnimationState> getRandom() {
        return this.randomize().getFirstOptional();
    }

    @NotNull
    public Optional<AnimationState> getOptional(int index) {
        return Optional.ofNullable(this.get(index));
    }

    @NotNull
    public Optional<AnimationState> getFirstOptional() {
        return Optional.ofNullable(this.getFirst());
    }

    @Nullable
    public AnimationState get(int index) {
        return this.entries.get(index).get();
    }

    @Nullable
    public AnimationState getFirst() {
        return this.entries.getFirst().get();
    }

    public int size() {
        return this.entries.size();
    }

    public boolean containsStarted() {
        for(Entry entry : this.entries) {
            if(entry.get().isStarted()) {
                return true;
            }
        }
        return false;
    }

    public void stopAll() {
        this.forEach(AnimationState::stop);
    }

    @Override
    public void forEach(Consumer<? super AnimationState> action) {
        this.entries.forEach(entry -> action.accept(entry.get()));
    }

    public Stream<AnimationState> stream() {
        return this.entries.stream().map(Entry::get);
    }

    @NotNull
    @Override
    public Iterator<AnimationState> iterator() {
        return new ObjectIterators.AbstractIndexBasedIterator<>(0, 0) {
            @Override
            protected AnimationState get(int location) {
                return AnimationStateList.this.entries.get(location).get();
            }

            @Override
            protected void remove(int location) {
                AnimationStateList.this.entries.remove(location);
            }

            @Override
            protected int getMaxPos() {
                return AnimationStateList.this.entries.size();
            }
        };
    }

    public static class Entry {
        private final AnimationState state;
        private final int weight;
        private double randomizedWeight;

        protected Entry(AnimationState state, int weight) {
            this.state = state;
            this.weight = weight;
        }

        protected double getRandomizedWeight() {
            return this.randomizedWeight;
        }

        protected AnimationState get() {
            return this.state;
        }

        protected int getWeight() {
            return this.weight;
        }

        protected void setRandomizedWeight(float mod) {
            this.randomizedWeight = -Math.pow(mod, 1f / this.weight);
        }

        @Override
        public String toString() {
            return this.state + ":" + this.weight;
        }
    }
}
