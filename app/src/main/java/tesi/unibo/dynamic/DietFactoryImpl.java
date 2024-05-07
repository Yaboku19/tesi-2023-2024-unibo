package tesi.unibo.dynamic;
import java.util.HashMap;
import java.util.Map;

public class DietFactoryImpl implements DietFactory {

	@Override
	public Diet standard() {
		return new Diet() {

			private final Map<String, Map<Diet.Nutrient, Integer>> foodMap = new HashMap<>();

			@Override
			public void addFood(String name, Map<Diet.Nutrient, Integer> nutritionMap) {
				foodMap.put(name, nutritionMap);
			}

			@Override
			public boolean isValid(Map<String, Double> dietMap) {
				double overallCalories = dietMap.keySet().stream().mapToDouble(food -> {
					double weight = dietMap.get(food);
					return foodMap.get(food).values().stream().mapToDouble(calories -> (calories * weight) / 100).sum();
				}).sum();
				return overallCalories >= 1500 && overallCalories <= 2000;
			}
		};
	}

	@Override
	public Diet lowCarb() {
		return new Diet() {

			private final Map<String, Map<Diet.Nutrient, Integer>> foodMap = new HashMap<>();

			@Override
			public void addFood(String name, Map<Diet.Nutrient, Integer> nutritionMap) {
				foodMap.put(name, nutritionMap);
			}

			@Override
			public boolean isValid(Map<String, Double> dietMap) {
				double overallCalories = dietMap.keySet().stream().mapToDouble(food -> {
					double weight = dietMap.get(food);
					return foodMap.get(food).values().stream().mapToDouble(calories -> (calories * weight) / 100).sum();
				}).sum();
				double overallCarbs = dietMap.keySet().stream().mapToDouble(food -> {
					double weight = dietMap.get(food);
					return foodMap.get(food).get(Nutrient.CARBS)* weight / 100;
				}).sum();
				return overallCalories >= 1000 && overallCalories <= 1500 && overallCarbs <= 300;
		    }
		};
	}

	@Override
	public Diet highProtein() {
		return new Diet() {

			private final Map<String, Map<Diet.Nutrient, Integer>> foodMap = new HashMap<>();

			@Override
			public void addFood(String name, Map<Diet.Nutrient, Integer> nutritionMap) {
				foodMap.put(name, nutritionMap);
			}
	
			@Override
			public boolean isValid(Map<String, Double> dietMap) {
				double overallCalories = dietMap.keySet().stream().mapToDouble(food -> {
					double weight = dietMap.get(food);
					return foodMap.get(food).values().stream().mapToDouble(calories -> (calories * weight) / 100).sum();
				}).sum();
				double overallCarbs = dietMap.keySet().stream().mapToDouble(food -> {
					double weight = dietMap.get(food);
					return foodMap.get(food).get(Nutrient.CARBS)* weight / 100;
				}).sum();
				double overallProteins = dietMap.keySet().stream().mapToDouble(food -> {
					double weight = dietMap.get(food);return foodMap.get(food).get(Nutrient.PROTEINS)* weight / 100;
				}).sum();
				return overallCalories >= 2000 && overallCalories <= 2500 && overallCarbs <= 300 && overallProteins >= 1300;
			}
	    	};
	}

	@Override
	public Diet balanced() {
		return new Diet() {

			private final Map<String, Map<Diet.Nutrient, Integer>> foodMap = new HashMap<>();

			@Override
			public void addFood(String name, Map<Diet.Nutrient, Integer> nutritionMap) {
				foodMap.put(name, nutritionMap);
			}

			@Override
			public boolean isValid(Map<String, Double> dietMap) {
				double overallCalories = dietMap.keySet().stream().mapToDouble(food -> {
					double weight = dietMap.get(food);
					return foodMap.get(food).values().stream().mapToDouble(calories -> (calories * weight) / 100).sum();
				}).sum();
				double overallCarbs = dietMap.keySet().stream().mapToDouble(food -> {
					double weight = dietMap.get(food);
					return foodMap.get(food).get(Nutrient.CARBS)* weight / 100;
				}).sum();
				double overallProteins = dietMap.keySet().stream().mapToDouble(food -> {
					double weight = dietMap.get(food);
					return foodMap.get(food).get(Nutrient.PROTEINS)* weight / 100;
				}).sum();
				double overallFat  = dietMap.keySet().stream().mapToDouble(food -> {
					double weight = dietMap.get(food);
					return foodMap.get(food).get(Nutrient.FAT)* weight / 100;
			    }).sum();
			    double fatPlusProtein = overallFat + overallProteins;
			    return overallCalories >= 1600 && overallCalories <= 2000 && overallCarbs >= 600 && overallProteins >= 600 &&
				    overallFat >= 400 && fatPlusProtein <= 1100;
		    }
	    };
	}

}