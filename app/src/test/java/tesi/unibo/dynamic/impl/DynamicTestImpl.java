package tesi.unibo.dynamic.impl;
import org.junit.jupiter.api.Assertions;

public class DynamicTestImpl {
	public DynamicTestImpl() {
	}

	@org.junit.Test
	public void dynamicTest() {
		System.out.println("Dynamic test executed.");
		Assertions.assertTrue(false);
	}

}
