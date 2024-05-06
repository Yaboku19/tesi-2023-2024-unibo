
package tesi.unibo.dynamic;

import java.util.Map;
import java.util.HashMap;

public class DietFactoryImpl implements DietFactory {

    private static class DietImpl implements Diet {
        private int minCalories;
        private int maxCalories;
        private Integer maxCarbCalories;
        private Integer maxProteinCalories;
        private Integer minProteinCalories;
        private Integer minFatCalories;
        private Integer maxFatAndProteinCalories;
        private Map<String, Map<Diet.Nutrient, Integer>> foodInfo;

        public DietImpl(int minCalories, int maxCalories, Integer maxCarbCalories, Integer maxProteinCalories,
                        Integer minProteinCalories, Integer minFatCalories, Integer maxFatAndProteinCalories) {
            this.minCalories = minCalories;
            this.maxCalories = maxCalories;
            this.maxCarbCalories = maxCarbCalories;
            this.maxProteinCalories = maxProteinCalories;
            this.minProteinCalories = minProteinCalories;
            this.minFatCalories = minFatCalories;
            this.maxFatAndProteinCalories = maxFatAndProteinCalories;
            this.foodInfo = new HashMap<>();
        }

        @Override
        public void addFood(String name, Map<Diet.Nutrient, Integer> nutritionMap) {
            foodInfo.put(name, nutritionMap);
        }

        @Override
        public boolean isValid(Map<String, Double> dietMap) {
            int totalCalories = 0;
            int totalCarbCalories = 0;
            int totalProteinCalories = 0;
            int totalFatCalories = 0;

            for (Map.Entry<String, Double> entry : dietMap.entrySet()) {
                if (foodInfo.containsKey(entry.getKey())) {
                    Map<Diet.Nutrient, Integer> nutritionMap = foodInfo.get(entry.getKey());
                    totalCalories += (int) (entry.getValue() * 100 * nutritionMap.values().stream().mapToInt(Integer::intValue).sum() / 100);
                    totalCarbCalories += (int) (entry.getValue() * 100 * nutritionMap.get(Diet.Nutrient.CARBS) / 100);
                    totalProteinCalories += (int) (entry.getValue() * 100 * nutritionMap.get(Diet.Nutrient.PROTEINS) / 100);
                    totalFatCalories += (int) (entry.getValue() * 100 * nutritionMap.get(Diet.Nutrient.FAT) / 100);
                }
            }

            return totalCalories >= minCalories && totalCalories <= maxCalories &&
                    (maxCarbCalories == null || totalCarbCalories <= maxCarbCalories) &&
                    (maxProteinCalories == null || totalProteinCalories >= maxProteinCalories) &&
                    (minProteinCalories == null || totalProteinCalories >= minProteinCalories) &&
                    (minFatCalories == null || totalFatCalories >= minFatCalories) &&
                    (maxFatAndProteinCalories == null || totalFatCalories + totalProteinCalories <= maxFatAndProteinCalories);
        }
    }

    @Override
    public Diet standard() {
        return new DietImpl(1500, 2000, null, null, null, null, null);
    }

    @Override
    public Diet lowCarb() {
        return new DietImpl(1000, 1500, null, 300, null, null, null);
    }

    @Override
    public Diet highProtein() {
        return new DietImpl(2000, 2500, null, 300, 1300, null, null);
    }

    @Override
    public Diet balanced() {
        return new DietImpl(1600, 2000, 600, null, 600, 400, 1100);
    }
}
