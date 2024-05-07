package tesi.unibo.dynamic.help.diet;

public interface DietFactory {
	
	/**
	 * @return a diet where a selection of food is ok if:
	 * - overall calories are within range [1500,2000]
	 * - does not care about carbs
	 * - does not care about protein
	 * - does not care about fat
	 */
	Diet standard();
	
	/**
	 * @return a diet where a selection of food is ok if:
	 * - overall calories are within range [1000,1500]
	 * - carbs give overall <=300 calories
	 * - does not care about protein
	 * - does not care about fat
	 */
	Diet lowCarb();
	
	/**
	 * @return a diet where a selection of food is ok if:
	 * - overall calories are within range [2000,2500]
	 * - carbs give overall <=300 calories
	 * - proteins give overall >=1300 calories
	 * - does not care about fat
	 */
	Diet highProtein();
	
	/**
	 * @return a diet where a selection of food is ok if:
	 * - overall calories are within range [1600,2000]
	 * - carbs give overall >=600 calories
	 * - proteins give overall >=600 calories
	 * - fat gives overall >=400 calories
	 * - fat and proteins together give overall <=1100 calories
	 */
	Diet balanced();
}
