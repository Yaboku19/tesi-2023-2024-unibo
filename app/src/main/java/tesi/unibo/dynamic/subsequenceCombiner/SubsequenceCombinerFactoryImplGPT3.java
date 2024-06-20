package tesi.unibo.dynamic.subsequenceCombiner;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SubsequenceCombinerFactoryImplGPT3 implements SubsequenceCombinerFactory {

    @Override
    public SubsequenceCombiner<Integer, Integer> tripletsToSum() {
        return list -> {
            List<Integer> result = new ArrayList<>();
            int sum = 0;
            for (int i = 0; i < list.size(); i++) {
                sum += list.get(i);
                if ((i + 1) % 3 == 0) {
                    result.add(sum);
                    sum = 0;
                }
            }
            if (sum > 0) {
                result.add(sum);
            }
            return result;
        };
    }

    @Override
    public <X> SubsequenceCombiner<X, List<X>> tripletsToList() {
        return list -> {
            List<List<X>> result = new ArrayList<>();
            List<X> subList = new ArrayList<>();
            for (X element : list) {
                subList.add(element);
                if (subList.size() == 3) {
                    result.add(new ArrayList<>(subList));
                    subList.clear();
                }
            }
            if (!subList.isEmpty()) {
                result.add(subList);
            }
            return result;
        };
    }

    @Override
    public SubsequenceCombiner<Integer, Integer> countUntilZero() {
        return list -> {
            List<Integer> result = new ArrayList<>();
            int count = 0;
            for (Integer num : list) {
                if (num == 0) {
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
            for (X element : list) {
                result.add(function.apply(element));
            }
            return result;
        };
    }

    @Override
    public SubsequenceCombiner<Integer, List<Integer>> cumulateToList(int threshold) {
        return list -> {
            List<List<Integer>> result = new ArrayList<>();
            List<Integer> subList = new ArrayList<>();
            int sum = 0;
            for (Integer num : list) {
                sum += num;
                subList.add(num);
                if (sum >= threshold) {
                    result.add(new ArrayList<>(subList));
                    subList.clear();
                    sum = 0;
                }
            }
            if (!subList.isEmpty()) {
                result.add(subList);
            }
            return result;
        };
    }

}