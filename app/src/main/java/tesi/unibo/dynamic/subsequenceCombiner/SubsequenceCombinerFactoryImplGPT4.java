
package tesi.unibo.dynamic.subsequenceCombiner;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SubsequenceCombinerFactoryImplGPT4 implements SubsequenceCombinerFactory {

    @Override
    public SubsequenceCombiner<Integer, Integer> tripletsToSum() {
        return list -> {
            List<Integer> sums = new ArrayList<>();
            int sum = 0;
            for (int i = 0; i < list.size(); i++) {
                sum += list.get(i);
                if ((i + 1) % 3 == 0 || i == list.size() - 1) {
                    sums.add(sum);
                    sum = 0;
                }
            }
            return sums;
        };
    }

    @Override
    public <X> SubsequenceCombiner<X, List<X>> tripletsToList() {
        return list -> {
            List<List<X>> result = new ArrayList<>();
            List<X> current = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                current.add(list.get(i));
                if ((i + 1) % 3 == 0 || i == list.size() - 1) { // Every three elements or end of list
                    result.add(new ArrayList<>(current));
                    current.clear();
                }
            }
            return result;
        };
    }

    @Override
    public SubsequenceCombiner<Integer, Integer> countUntilZero() {
        return list -> {
            List<Integer> result = new ArrayList<>();
            int count = 0;
            for (Integer el : list) {
                if (el == 0) {
                    result.add(count);
                    count = 0;
                } else {
                    count++;
                }
            }
            if (count > 0) {
                result.add(count);
            }
            return result;
        };
    }

    @Override
    public <X, Y> SubsequenceCombiner<X, Y> singleReplacer(Function<X, Y> function) {
        return list -> {
            List<Y> result = new ArrayList<>();
            for (X el : list) {
                result.add(function.apply(el));
            }
            return result;
        };
    }

    @Override
    public SubsequenceCombiner<Integer, List<Integer>> cumulateToList(int threshold) {
        return list -> {
            List<List<Integer>> result = new ArrayList<>();
            List<Integer> current = new ArrayList<>();
            int sum = 0;

            for (Integer el : list) {
                current.add(el);
                sum += el;
                if (sum >= threshold) {
                    result.add(new ArrayList<>(current));
                    current.clear();
                    sum = 0;
                }
            }

            if (!current.isEmpty()) {
                result.add(current);
            }

            return result;
        };
    }
}
