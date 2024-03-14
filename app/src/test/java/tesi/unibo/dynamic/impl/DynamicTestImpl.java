package tesi.unibo.dynamic.impl;
import org.junit.jupiter.api.Test;
import tesi.unibo.dynamic.api.DynamicTest;

public class DynamicTestImpl implements DynamicTest {
	public DynamicTestImpl() {
	}

	@Override
	public String launcher() {
		System.out.println("ce l'hai fatta");
		return "";
	}

	@Test
	void dynamicTest() {
		System.out.println("Dynamic test executed.");
	}

}
