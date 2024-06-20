package tesi.unibo.dynamic.diet;

import java.util.HashMap;
import java.util.Map;

public class DietFactoryImplGPT4 implements DietFactory {

    private Map<String, Map<Diet.Nutrient, Integer>> foodData = new HashMap<>();

    @Override
    public Diet standard() {
        return new Diet() {
            @Override
            public void addFood(String name, Map<Diet.Nutrient, Integer> nutritionMap) {
                foodData.put(name, nutritionMap);
            }

            @Override
            public boolean isValid(Map<String, Double> dietMap) {
                double totalCalories = calculateCalories(dietMap);
                return totalCalories >= 1500 && totalCalories <= 2000;
            }
        };
    }

    @Override
    public Diet lowCarb() {
        return new Diet() {
            @Override
            public void addFood(String name, Map<Diet.Nutrient, Integer> nutritionMap) {
                foodData.put(name, nutritionMap);
            }

            @Override
            public boolean isValid(Map<String, Double> dietMap) {
                double totalCalories = calculateCalories(dietMap);
                double carbsCalories = calculateSpecificNutrientCalories(dietMap, Diet.Nutrient.CARBS);
                return totalCalories >= 1000 && totalCalories <= 1500 && carbsCalories <= 300;
            }
        };
    }

    @Override
    public Diet highProtein() {
        return new Diet() {
            @Override
            public void addFood(String name, Map<Diet.Nutrient, Integer> nutritionMap) {
                foodData.put(name, nutritionMap);
            }

            @Override
            public boolean isValid(Map<String, Double> dietMap) {
                double totalCalories = calculateCalories(dietMap);
                double carbsCalories = calculateSpecificNutrientCalories(dietMap, Diet.Nutrient.CARBS);
                double proteinCalories = calculateSpecificNutrientCalories(dietMap, Diet.Nutrient.PROTEINS);
                return totalCalories >= 2000 && totalCalories <= 2500 && carbsCalories <= 300 && proteinCalories >= 1300;
            }
        };
    }

    @Override
    public Diet balanced() {
        return new Diet() {
            @Override
            public void addFood(String name, Map<Diet.Nutrient, Integer> nutritionMap) {
                foodData.put(name, nutritionMap);
            }

            @Override
            public boolean isValid(Map<String, Double> dietMap) {
                double totalCalories = calculateCalories(dietMap);
                double carbsCalories = calculateSpecificNutrientCalories(dietMap, Diet.Nutrient.CARBS);
                double proteinCalories = calculateSpecificNutrientCalories(dietMap, Diet.Nutrient.PROTEINS);
                double fatCalories = calculateSpecificNutrientCalories(dietMap, Diet.Nutrient.FAT);
                double proteinFatCalories = proteinCalories + fatCalories;
                return totalCalories >= 1600 && totalCalories <= 2000 &&
                        carbsCalories >= 600 && proteinCalories >= 600 &&
                        fatCalories >= 400 && proteinFatCalories <= 1100;
            }
        };
    }

    private double calculateCalories(Map<String, Double> dietMap) {
        double totalCalories = 0;
        for (Map.Entry<String, Double> entry : dietMap.entrySet()) {
            Map<Diet.Nutrient, Integer> nutrients = foodData.get(entry.getKey());
            if (nutrients != null) {
                for (Map.Entry<Diet.Nutrient, Integer> nutrient : nutrients.entrySet()) {
                    totalCalories += nutrient.getValue() * entry.getValue() / 100;
                }
            }
        }
        return totalCalories;
    }

    private double calculateSpecificNutrientCalories(Map<String, Double> dietMap, Diet.Nutrient nutrientType) {
        double nutrientCalories = 0;
        for (Map.Entry<String, Double> entry : dietMap.entrySet()) {
            Map<Diet.Nutrient, Integer> nutrients = foodData.get(entry.getKey());
            if (nutrients != null && nutrients.containsKey(nutrientType)) {
                nutrientCalories += nutrients.get(nutrientType) * entry.getValue() / 100;
            }
        }
        return nutrientCalories;
    }
}
