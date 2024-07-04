package tesi.unibo.dynamic;

import java.util.ArrayList;
import java.util.List;
import tesi.unibo.dynamic.common.Pair;

public class RecursiveIteratorHelpersImplGPT4 implements RecursiveIteratorHelpers {

    @Override
    public <X> RecursiveIterator<X> fromList(List<X> list) {
        return new RecursiveIterator<X>() {
            private int index = 0;

            @Override
            public X getElement() {
                return index < list.size() ? list.get(index) : null;
            }

            @Override
            public RecursiveIterator<X> next() {
                if (index < list.size() - 1) {
                    index++;
                    return this;
                } else {
                    return null;
                }
            }
        };
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
        return new RecursiveIterator<Pair<X, Y>>() {
            private RecursiveIterator<X> currentFirst = first;
            private RecursiveIterator<Y> currentSecond = second;

            @Override
            public Pair<X, Y> getElement() {
                if (currentFirst == null || currentSecond == null) {
                    return null;
                }
                return new Pair<>(currentFirst.getElement(), currentSecond.getElement());
            }

            @Override
            public RecursiveIterator<Pair<X, Y>> next() {
                if (currentFirst != null && currentSecond != null) {
                    currentFirst = currentFirst.next();
                    currentSecond = currentSecond.next();
                    if (currentFirst != null && currentSecond != null) {
                        return this;
                    }
                }
                return null;
            }
        };
    }

    @Override
    public <X> RecursiveIterator<Pair<X, Integer>> zipWithIndex(RecursiveIterator<X> iterator) {
        return new RecursiveIterator<Pair<X, Integer>>() {
            private int index = 0;
            private RecursiveIterator<X> current = iterator;

            @Override
            public Pair<X, Integer> getElement() {
                if (current == null) {
                    return null;
                }
                return new Pair<>(current.getElement(), index);
            }

            @Override
            public RecursiveIterator<Pair<X, Integer>> next() {
                if (current != null) {
                    current = current.next();
                    index++;
                    if (current != null) {
                        return this;
                    }
                }
                return null;
            }
        };
    }

    @Override
    public <X> RecursiveIterator<X> alternate(RecursiveIterator<X> first, RecursiveIterator<X> second) {
        return new RecursiveIterator<X>() {
            private RecursiveIterator<X> currentFirst = first;
            private RecursiveIterator<X> currentSecond = second;
            private boolean useFirst = true;

            @Override
            public X getElement() {
                return useFirst ? (currentFirst != null ? currentFirst.getElement() : null)
                                : (currentSecond != null ? currentSecond.getElement() : null);
            }

            @Override
            public RecursiveIterator<X> next() {
                if (useFirst) {
                    useFirst = false;
                    if (currentFirst != null) {
                        currentFirst = currentFirst.next();
                    }
                } else {
                    useFirst = true;
                    if (currentSecond != null) {
                        currentSecond = currentSecond.next();
                    }
                }
                if ((useFirst && currentFirst == null) || (!useFirst && currentSecond == null)) {
                    return null;
                }
                return this;
            }
        };
    }
}
