
package tesi.unibo.dynamic;

import java.util.HashMap;
import java.util.Map;

public class DietFactoryImpl implements DietFactory {

    @Override
    public Diet standard() {
        return new DietImpl(1500, 2000, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
    }

    @Override
    public Diet lowCarb() {
        return new DietImpl(1000, 1500, 0, 300, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
    }

    @Override
    public Diet highProtein() {
        return new DietImpl(2000, 2500, 250, 300, 1500, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
    }

    @Override
    public Diet balanced() {
        return new DietImpl(1600, 2000, 600, Integer.MAX_VALUE, 600, Integer.MAX_VALUE, 400, 1100);
    }

    private static class DietImpl implements Diet {
        private Map<String, Map<Nutrient, Integer>> foods = new HashMap<>();
        private int minCalories;
        private int maxCalories;
        private int minCarbs;
        private int maxCarbs;
        private int minProteins;
        private int maxProteins;
        private int minFat;
        private int maxFat;

        public DietImpl(int minCalories, int maxCalories, int minCarbs, int maxCarbs, int minProteins, int maxProteins, int minFat, int maxFat) {
            this.minCalories = minCalories;
            this.maxCalories = maxCalories;
            this.minCarbs = minCarbs;
            this.maxCarbs = maxCarbs;
            this.minProteins = minProteins;
            this.maxProteins = maxProteins;
            this.minFat = minFat;
            this.maxFat = maxFat;
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
                String food = entry.getKey();
                double grams = entry.getValue();

                if (foods.containsKey(food)) {
                    Map<Nutrient, Integer> nutrition = foods.get(food);
                    totalCalories += nutrition.get(Nutrient.CARBS) * grams / 100;
                    totalCarbs += nutrition.get(Nutrient.CARBS) * grams / 100;
                    totalProteins += nutrition.get(Nutrient.PROTEINS) * grams / 100;
                    totalFat += nutrition.get(Nutrient.FAT) * grams / 100;
                }
            }

            return totalCalories >= minCalories && totalCalories <= maxCalories &&
                    totalCarbs <= maxCarbs &&
                    totalProteins >= minProteins && totalProteins <= maxProteins &&
                    totalFat >= minFat && totalFat <= maxFat;
        }
    }
}
