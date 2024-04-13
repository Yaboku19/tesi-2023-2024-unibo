package tesi.unibo.dynamic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DynamicTest {
	public DynamicTest() {
	}

	private Contatore contatore;
	
	@BeforeEach
	public void setUp() {
		// Inizializza un nuovo contatore prima di ogni test
		contatore = new Contatore();
	}
	
	@Test
	public void testIncrementa() {
		// Incrementa il valore e verifica che sia aumentato di 1
		contatore.incrementa();
		assertEquals(1, contatore.getValore(), "Il valore dovrebbe essere incrementato di 1");
	}
	
	@Test
	public void testDecrementa() {
		// Decrementa il valore e verifica che sia diminuito di 1
		contatore.decrementa();
		assertEquals(-1, contatore.getValore(), "Il valore dovrebbe essere decrementato di 1");
	}
	
	@Test
	public void testIncrementaDecrementa() {
		// Combina incrementi e decrementi e verifica il risultato finale
		contatore.incrementa();
		contatore.incrementa();
		contatore.decrementa();
		assertEquals(1, contatore.getValore(), "Il valore dovrebbe essere 1 dopo due incrementi e un decremento");
	}
}
