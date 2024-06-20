
package tesi.unibo.dynamic;

import java.util.List;
import java.util.ArrayList;
import tesi.unibo.dynamic.common.Pair;

public class RecursiveIteratorHelpersImpl implements RecursiveIteratorHelpers {

    @Override
    public <X> RecursiveIterator<X> fromList(List<X> list) {
        return list.isEmpty() ? null : new RecursiveListIterator<>(list, 0);
    }

    @Override
    public <X> List<X> toList(RecursiveIterator<X> input, int max) {
        List<X> resultList = new ArrayList<>();
        int count = 0;
        while (input != null && count < max) {
            resultList.add(input.getElement());
            input = input.next();
            count++;
        }
        return resultList;
    }

    @Override
    public <X, Y> RecursiveIterator<Pair<X, Y>> zip(RecursiveIterator<X> first, RecursiveIterator<Y> second) {
        if (first == null || second == null) return null;
        return new RecursivePairIterator<>(first, second);
    }

    @Override
    public <X> RecursiveIterator<Pair<X, Integer>> zipWithIndex(RecursiveIterator<X> iterator) {
        return iterator == null ? null : new RecursiveIndexIterator<>(iterator, 0);
    }

    @Override
    public <X> RecursiveIterator<X> alternate(RecursiveIterator<X> first, RecursiveIterator<X> second) {
        if (first == null) return second;
        if (second == null) return first;
        return new RecursiveAlternateIterator<>(first, second);
    }

    private static class RecursiveListIterator<X> implements RecursiveIterator<X> {
        private final List<X> list;
        private final int index;

        public RecursiveListIterator(List<X> list, int index) {
            this.list = list;
            this.index = index;
        }

        @Override
        public X getElement() {
            return list.get(index);
        }

        @Override
        public RecursiveIterator<X> next() {
            return index + 1 < list.size() ? new RecursiveListIterator<>(list, index + 1) : null;
        }
    }

    private static class RecursivePairIterator<X, Y> implements RecursiveIterator<Pair<X, Y>> {
        private RecursiveIterator<X> first;
        private RecursiveIterator<Y> second;

        public RecursivePairIterator(RecursiveIterator<X> first, RecursiveIterator<Y> second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public Pair<X, Y> getElement() {
            return new Pair<>(first.getElement(), second.getElement());
        }

        @Override
        public RecursiveIterator<Pair<X, Y>> next() {
            RecursiveIterator<X> nextFirst = first.next();
            RecursiveIterator<Y> nextSecond = second.next();
            return (nextFirst != null && nextSecond != null) ? new RecursivePairIterator<>(nextFirst, nextSecond) : null;
        }
    }

    private static class RecursiveIndexIterator<X> implements RecursiveIterator<Pair<X, Integer>> {
        private RecursiveIterator<X> iterator;
        private final int index;

        public RecursiveIndexIterator(RecursiveIterator<X> iterator, int index) {
            this.iterator = iterator;
            this.index = index;
        }

        @Override
        public Pair<X, Integer> getElement() {
            return new Pair<>(iterator.getElement(), index);
        }

        @Override
        public RecursiveIterator<Pair<X, Integer>> next() {
            RecursiveIterator<X> nextIterator = iterator.next();
            return nextIterator != null ? new RecursiveIndexIterator<>(nextIterator, index + 1) : null;
        }
    }

    private static class RecursiveAlternateIterator<X> implements RecursiveIterator<X> {
        private RecursiveIterator<X> first, second;
        private boolean isTurnOfFirst;

        public RecursiveAlternateIterator(RecursiveIterator<X> first, RecursiveIterator<X> second) {
            this.first = first;
            this.second = second;
            this.isTurnOfFirst = true;
        }

        @Override
        public X getElement() {
            return isTurnOfFirst ? first.getElement() : second.getElement();
        }

        @Override
        public RecursiveIterator<X> next() {
            if (isTurnOfFirst && first != null) {
                first = first.next();
            } else if (!isTurnOfFirst && second != null) {
                second = second.next();
            }
            isTurnOfFirst = !isTurnOfFirst;
            return first == null ? second : (second == null ? first : this);
        }
    }
}
