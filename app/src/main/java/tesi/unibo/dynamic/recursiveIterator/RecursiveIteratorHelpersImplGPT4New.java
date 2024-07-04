package tesi.unibo.dynamic.recursiveIterator;

import java.util.ArrayList;
import java.util.List;
import tesi.unibo.dynamic.common.Pair;

public class RecursiveIteratorHelpersImplGPT4New implements RecursiveIteratorHelpers {

    @Override
    public <X> RecursiveIterator<X> fromList(List<X> list) {
        if (list.isEmpty()) {
            return null;
        }
        return new RecursiveIteratorImpl<>(list, 0);
    }

    @Override
    public <X> List<X> toList(RecursiveIterator<X> input, int max) {
        List<X> result = new ArrayList<>();
        int count = 0;
        while (input != null && count < max) {
            result.add(input.getElement());
            input = input.next();
            count++;
        }
        return result;
    }

    @Override
    public <X, Y> RecursiveIterator<Pair<X, Y>> zip(RecursiveIterator<X> first, RecursiveIterator<Y> second) {
        if (first == null || second == null) {
            return null;
        }

        return new RecursiveIterator<Pair<X, Y>>() {
            private RecursiveIterator<X> currentFirst = first;
            private RecursiveIterator<Y> currentSecond = second;

            @Override
            public Pair<X, Y> getElement() {
                return new Pair<>(currentFirst.getElement(), currentSecond.getElement());
            }

            @Override
            public RecursiveIterator<Pair<X, Y>> next() {
                RecursiveIterator<X> nextFirst = currentFirst.next();
                RecursiveIterator<Y> nextSecond = currentSecond.next();
                if (nextFirst == null || nextSecond == null) {
                    return null;
                }
                currentFirst = nextFirst;
                currentSecond = nextSecond;
                return this;
            }
        };
    }

    @Override
    public <X> RecursiveIterator<Pair<X, Integer>> zipWithIndex(RecursiveIterator<X> iterator) {
        return new RecursiveIterator<Pair<X, Integer>>() {
            private RecursiveIterator<X> current = iterator;
            private int index = 0;

            @Override
            public Pair<X, Integer> getElement() {
                return new Pair<>(current.getElement(), index);
            }

            @Override
            public RecursiveIterator<Pair<X, Integer>> next() {
                current = current.next();
                if (current == null) {
                    return null;
                }
                index++;
                return this;
            }
        };
    }

    @Override
    public <X> RecursiveIterator<X> alternate(RecursiveIterator<X> first, RecursiveIterator<X> second) {
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        }
        return new RecursiveIterator<X>() {
            private RecursiveIterator<X> currentFirst = first;
            private RecursiveIterator<X> currentSecond = second;
            private boolean useFirst = true;

            @Override
            public X getElement() {
                return useFirst ? currentFirst.getElement() : currentSecond.getElement();
            }

            @Override
            public RecursiveIterator<X> next() {
                if (useFirst) {
                    currentFirst = currentFirst.next();
                } else {
                    currentSecond = currentSecond.next();
                }
                useFirst = !useFirst;
                return currentFirst == null ? currentSecond : (currentSecond == null ? currentFirst : this);
            }
        };
    }

    private static class RecursiveIteratorImpl<X> implements RecursiveIterator<X> {
        private final List<X> list;
        private int index;

        RecursiveIteratorImpl(List<X> list, int index) {
            this.list = list;
            this.index = index;
        }

        @Override
        public X getElement() {
            return list.get(index);
        }

        @Override
        public RecursiveIterator<X> next() {
            if (index + 1 < list.size()) {
                return new RecursiveIteratorImpl<>(list, index + 1);
            }
            return null;
        }
    }
}
