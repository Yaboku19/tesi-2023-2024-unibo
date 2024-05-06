
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
        return new DietImpl(1000, 1500, 0, 300, 0, 0, 0, 0);
    }

    @Override
    public Diet highProtein() {
        return new DietImpl(2000, 2500, 0, 300, 0, 1300, 0, 0);
    }

    @Override
    public Diet balanced() {
        return new DietImpl(1600, 2000, 600, 0, 600, 0, 400, 1100);
    }

    private static class DietImpl implements Diet {
        private Map<String, Map<Nutrient, Integer>> foods = new HashMap<>();
        private int minCalories;
        private int maxCalories;
        private int maxCarbs;
        private int minProteins;
        private int minCarbs;
        private int minFat;
        private int maxFat;
        private int maxProteins;

        public DietImpl(int minCalories, int maxCalories, int maxCarbs, int minCarbs, int minProteins, int maxProteins, int minFat, int maxFat) {
            this.minCalories = minCalories;
            this.maxCalories = maxCalories;
            this.maxCarbs = maxCarbs;
            this.minProteins = minProteins;
            this.minCarbs = minCarbs;
            this.minFat = minFat;
            this.maxFat = maxFat;
            this.maxProteins = maxProteins;
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
                    Map<Nutrient, Integer> nutrition = foods.get(foodName);
                    totalCalories += (nutrition.get(Nutrient.CARBS) * grams / 100);
                    totalCalories += (nutrition.get(Nutrient.PROTEINS) * grams / 100);
                    totalCalories += (nutrition.get(Nutrient.FAT) * grams / 100);

                    totalCarbs += (nutrition.get(Nutrient.CARBS) * grams / 100);
                    totalProteins += (nutrition.get(Nutrient.PROTEINS) * grams / 100);
                    totalFat += (nutrition.get(Nutrient.FAT) * grams / 100);
                }
            }

            return totalCalories >= minCalories && totalCalories <= maxCalories &&
                    totalCarbs <= maxCarbs &&
                    totalProteins >= minProteins && totalProteins <= maxProteins &&
                    totalCarbs >= minCarbs &&
                    totalFat >= minFat && totalFat <= maxFat &&
                    (totalProteins + totalFat) <= maxProteins;
}       
    }
}
