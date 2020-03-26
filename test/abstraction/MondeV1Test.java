package abstraction;

import org.junit.Test;

import abstraction.fourni.Monde;

public class MondeV1Test {

	@Test
	public void testNext() {
		Monde.LE_MONDE = new Monde(); 
		Monde.LE_MONDE.peupler();
		for (int i=0; i<300; i++)
			Monde.LE_MONDE.next();
	}

}
