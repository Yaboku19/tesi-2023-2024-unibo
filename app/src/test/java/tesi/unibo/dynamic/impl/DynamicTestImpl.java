package tesi.unibo.dynamic.impl;
import org.junit.jupiter.api.Test;
import tesi.unibo.dynamic.api.DynamicTest;

public class DynamicTestImpl implements DynamicTest {
	@Override
	public String launcher() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'launcher'");
	}

	@Test
	void dynamicTest() {
		System.out.println("Dynamic test executed.");
	}

}
