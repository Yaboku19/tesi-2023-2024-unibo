package tesi.unibo.dynamic.diet;

import java.util.HashMap;
import java.util.Map;

public class DietFactoryImplGPT3 implements DietFactory {

	@Override
	public Diet standard() {
		return new DietImpl(1500, 2000, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
	}

	@Override
	public Diet lowCarb() {
		return new DietImpl(1000, 1500, 0, Integer.MAX_VALUE, 0, 300, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
	}

	@Override
	public Diet highProtein() {
		return new DietImpl(2000, 2500, 1300, Integer.MAX_VALUE, 0, 300, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
	}

	@Override
	public Diet balanced() {
		return new DietImpl(1600, 2000, 600, Integer.MAX_VALUE, 600, Integer.MAX_VALUE, 400, Integer.MAX_VALUE, 400, 1100);
	}

	private static class DietImpl implements Diet {
		private Map<String, Map<Nutrient, Integer>> foods;
		private int minCalories;
		private int maxCalories;
		private int minCarbs;
		private int maxCarbs;
		private int minProteins;
		private int maxProteins;
		private int minFat;
		private int maxFat;
		private int maxProteinsAndFat;

		public DietImpl(int minCalories, int maxCalories, int minCarbs, int maxCarbs,
						int minProteins, int maxProteins, int minFat, int maxFat,
						int minProteinsAndFat, int maxProteinsAndFat) {
			this.foods = new HashMap<>();
			this.minCalories = minCalories;
			this.maxCalories = maxCalories;
			this.minCarbs = minCarbs;
			this.maxCarbs = maxCarbs;
			this.minProteins = minProteins;
			this.maxProteins = maxProteins;
			this.minFat = minFat;
			this.maxFat = maxFat;
			this.maxProteinsAndFat = maxProteinsAndFat;
		}

		@Override
		public void addFood(String name, Map<Nutrient, Integer> nutritionMap) {
			foods.put(name, nutritionMap);
		}

		@Override
		public boolean isValid(Map<String, Double> dietMap) {
			double calories = 0;
			double carbs = 0;
			double proteins = 0;
			double fat = 0;
			for (var entry : dietMap.entrySet()) {
				String food = entry.getKey();
				double grams = entry.getValue();
				Map<Nutrient, Integer> nutritionMap = foods.get(food);
				calories += grams / 100 * nutritionMap.get(Nutrient.CARBS);
				carbs += grams / 100 * nutritionMap.get(Nutrient.CARBS);
				proteins += grams / 100 * nutritionMap.get(Nutrient.PROTEINS);
				fat += grams / 100 * nutritionMap.get(Nutrient.FAT);
			}
			if (calories < minCalories || calories > maxCalories) return false;
			if (carbs > maxCarbs) return false;
			if (proteins < minProteins) return false;
			if (fat > maxFat) return false;
			if (proteins + fat > maxProteinsAndFat) return false;
			return true;
		}
	}
}