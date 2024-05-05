
package tesi.unibo.dynamic;

import java.util.HashMap;
import java.util.Map;

public class DietFactoryImpl implements DietFactory {

    @Override
    public Diet standard() {
        return new DietImpl(1500, 2000, 0, 0, 0, 0, 0, 0);
    }

    @Override
    public Diet lowCarb() {
        return new DietImpl(1000, 1500, 300, 0, 0, 0, 0, 0);
    }

    @Override
    public Diet highProtein() {
        return new DietImpl(2000, 2500, 300, 0, 1300, 0, 0, 0);
    }

    @Override
    public Diet balanced() {
        return new DietImpl(1600, 2000, 600, 0, 0, 400, 600, 1100);
    }

    private static class DietImpl implements Diet {
        private final int minCalories;
        private final int maxCalories;
        private final int maxCarbCalories;
        private final int minProteinCalories;
        private final int minFatCalories;
        private final int minProteinFatCalories;
        private final int minCarbohydrateCalories;
        private final int maxProteinFatCalories;
        private final Map<String, Map<Nutrient, Integer>> foods = new HashMap<>();

        public DietImpl(int minCalories, int maxCalories, int maxCarbCalories, int minProteinCalories,
                        int minFatCalories, int minProteinFatCalories, int minCarbohydrateCalories, int maxProteinFatCalories) {
            this.minCalories = minCalories;
            this.maxCalories = maxCalories;
            this.maxCarbCalories = maxCarbCalories;
            this.minProteinCalories = minProteinCalories;
            this.minFatCalories = minFatCalories;
            this.minProteinFatCalories = minProteinFatCalories;
            this.minCarbohydrateCalories = minCarbohydrateCalories;
            this.maxProteinFatCalories = maxProteinFatCalories;
        }

        @Override
        public void addFood(String name, Map<Nutrient, Integer> nutritionMap) {
            foods.put(name, nutritionMap);
        }

        @Override
        public boolean isValid(Map<String, Double> dietMap) {
            int totalCalories = 0;
            int totalCarbs = 0;
            int totalProteins = 0;
            int totalFat = 0;

            for (Map.Entry<String, Double> entry : dietMap.entrySet()) {
                String foodName = entry.getKey();
                double grams = entry.getValue();
                if (foods.containsKey(foodName)) {
                    Map<Nutrient, Integer> nutritionMap = foods.get(foodName);
                    totalCalories += grams / 100 * nutritionMap.get(Nutrient.CARBS);
                    totalCalories += grams / 100 * nutritionMap.get(Nutrient.PROTEINS);
                    totalCalories += grams / 100 * nutritionMap.get(Nutrient.FAT);

                    totalCarbs += grams / 100 * nutritionMap.get(Nutrient.CARBS);
                    totalProteins += grams / 100 * nutritionMap.get(Nutrient.PROTEINS);
                    totalFat += grams / 100 * nutritionMap.get(Nutrient.FAT);
                } else {
                    return false;
                }
            }

            if (totalCalories < minCalories || totalCalories > maxCalories) {
                return false;
            }

            if (totalCarbs > maxCarbCalories || totalCarbs < minCarbohydrateCalories) {
                return false;
            }

            if (totalProteins < minProteinCalories) {
                return false;
            }

            if (totalFat < minFatCalories) {
                return false;
            }

            if (totalProteins + totalFat > maxProteinFatCalories) {
                return false;
            }

            return true;
        }
    }
}
