package tesi.unibo.dynamic;
import static tesi.unibo.dynamic.Diet.Nutrient.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DynamicTest {
private DietFactory factory;
	@BeforeEach
	public void initFactory() {
		this.factory = new DietFactoryImpl();
	}
	private void fillProducts(Diet diet) {
		diet.addFood("pasta", Map.of(CARBS,280,PROTEINS,70,FAT,50)); // 400 calories overall
		diet.addFood("riso", Map.of(CARBS,250,PROTEINS,70,FAT,30));  // 350 calories overall
		diet.addFood("pollo", Map.of(CARBS,10,PROTEINS,60,FAT,30));  // 100 calories overall
		diet.addFood("insalata", Map.of(CARBS,10,PROTEINS,3,FAT,2)); // 15 calories overall
		diet.addFood("broccoli", Map.of(CARBS,20,PROTEINS,10,FAT,5));// 35 calories overall
		diet.addFood("grana", Map.of(CARBS,0,PROTEINS,200,FAT,200)); // 400 calories overall
	}
	@Test
	public void testStandard() {
		var diet = this.factory.standard();
		this.fillProducts(diet);
		assertTrue(diet.isValid(Map.of("pasta",200.0,"pollo",300.0,"grana",200.0)), 
		"The amount of calories is 1900, so it should be true"); // 800+300+800 calories
		assertFalse(diet.isValid(Map.of("pasta",200.0,"pollo",300.0,"grana",50.0))); // 800+300+200 calories: too low!!
		assertFalse(diet.isValid(Map.of("pasta",300.0,"pollo",300.0,"grana",200.0,"broccoli",300.0))); // 1200+300+800+105 calories: too much!!
	}
	@Test
	public void testLowCarb() {
		var diet = this.factory.lowCarb();
		this.fillProducts(diet);
		assertTrue(diet.isValid(Map.of("pollo",1000.0)), 
		"The amount of calories is 1000 and the amount of carbs is 100, so it should be true"); // ok calories, ok carbs
		assertFalse(diet.isValid(Map.of("pasta",200.0,"pollo",300.0,"grana",200.0))); // 800+300+800 calories, too much!
		assertFalse(diet.isValid(Map.of("pasta",400.0))); // ok calories, but too much carbs
	}
	@Test
	public void testHighProtein() {
		var diet = this.factory.highProtein();
		this.fillProducts(diet);
		assertTrue(diet.isValid(Map.of("pollo",2500.0))); // ok calories, ok proteins
		assertFalse(diet.isValid(Map.of("pasta",200.0,"pollo",300.0,"grana",200.0))); // 800+300+800 calories, too few!
		assertFalse(diet.isValid(Map.of("grana",500.0))); // ok calories, but too few proteins
	}
	@Test
	public void testBalanced() {
		var diet = this.factory.balanced();
		this.fillProducts(diet);
		assertTrue(diet.isValid(Map.of("pasta", 200.0, "pollo", 400.0, "grana", 100.0, "broccoli", 300.0))); // OK
		assertFalse(diet.isValid(Map.of("pasta",200.0,"pollo",300.0,"grana",200.0)));
		assertFalse(diet.isValid(Map.of("pollo",1000.0)));
		assertFalse(diet.isValid(Map.of("pollo",2000.0)));
	}
	
}