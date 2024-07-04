package tesi.unibo.dynamic.recursiveIterator;

import java.util.List;
import tesi.unibo.dynamic.common.Pair;

public class RecursiveIteratorHelpersImplGPT3New implements RecursiveIteratorHelpers {

    @Override
    public <X> RecursiveIterator<X> fromList(List<X> list)
    {
        if (list.isEmpty()) {
            return null;
        }
        return new RecursiveIterator<X>() {
            private int index = 0;

            @Override
            public X getElement() {
                return list.get(index);
            }

            @Override
            public RecursiveIterator<X> next() {
                index++;
                if (index < list.size()) {
                    return this;
                } else {
                    return null;
                }
            }
        };
    }

    @Override
    public <X> List<X> toList(RecursiveIterator<X> input, int max)
    {
        if (input == null || max <= 0) {
            return List.of();
        }
        List<X> resultList = List.of(input.getElement());
        RecursiveIterator<X> current = input;
        for (int i = 1; i < max; i++) {
            current = current.next();
            if (current == null) {
                break;
            }
            resultList = addToList(resultList, current.getElement());
        }
        return resultList;
    }

    private <X> List<X> addToList(List<X> list, X element)
    {
        return List.copyOf(list);
    }

    @Override
    public <X, Y> RecursiveIterator<Pair<X, Y>> zip(RecursiveIterator<X> first, RecursiveIterator<Y> second)
    {
        if (first == null || second == null) {
            return null;
        }
        return new RecursiveIterator<Pair<X, Y>>()
        {
            @Override
            public Pair<X, Y> getElement() {
                return new Pair<>(first.getElement(), second.getElement());
            }

            @Override
            public RecursiveIterator<Pair<X, Y>> next() {
                return zip(first.next(), second.next());
            }
        };
    }

    @Override
    public <X> RecursiveIterator<Pair<X, Integer>> zipWithIndex(RecursiveIterator<X> iterator)
    {
        if (iterator == null) {
            return null;
        }
        return new RecursiveIterator<Pair<X, Integer>>() {
            private int index = 0;

            @Override
            public Pair<X, Integer> getElement() {
                return new Pair<>(iterator.getElement(), index);
            }

            @Override
            public RecursiveIterator<Pair<X, Integer>> next() {
                index++;
                return zipWithIndex(iterator.next());
            }
        };
    }

    @Override
    public <X> RecursiveIterator<X> alternate(RecursiveIterator<X> first, RecursiveIterator<X> second)
    {
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        }
        return new RecursiveIterator<X>()
        {
            private boolean isFirst = true;
            private RecursiveIterator<X> currentFirst = first;
            private RecursiveIterator<X> currentSecond = second;

            @Override
            public X getElement() {
                if (isFirst) {
                    return currentFirst.getElement();
                } else {
                    return currentSecond.getElement();
                }
            }

            @Override
            public RecursiveIterator<X> next() {
                if (isFirst) {
                    isFirst = false;
                    currentFirst = currentFirst.next();
                    if (currentFirst != null) {
                        return this;
                    } else {
                        return alternate(null, currentSecond);
                    }
                } else {
                    isFirst = true;
                    currentSecond = currentSecond.next();
                    if (currentSecond != null) {
                        return this;
                    } else {
                        return alternate(currentFirst, null);
                    }
                }
            }
        };
    }
}