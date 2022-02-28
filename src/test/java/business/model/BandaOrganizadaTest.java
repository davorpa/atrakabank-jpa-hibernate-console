package business.model;

import static java.lang.String.format;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BandaOrganizadaTest {

	private BandaOrganizada banda;

	@Before
	public void setUp() {
		banda = new BandaOrganizada();
	}

	@Test
	public void testNumMiembrosIsIndependentOfDelinquientesSize() {
		// Given
		int expectedNumMiembros, expectedDelinquentesSize = 0;
		banda.setNumMiembros(expectedNumMiembros = 11);
		banda.addDelincuente(new Delincuente(format("D00%s", ++expectedDelinquentesSize), "Pepito"));
		banda.addDelincuente(new Delincuente(format("D00%s", ++expectedDelinquentesSize), "Fulanito"));
		banda.addDelincuente(new Delincuente(format("D00%s", ++expectedDelinquentesSize), "Menganito"));

		// Do
		int numMiembros = banda.getNumMiembros();
		Collection<Delincuente> delinquentes = banda.getDelincuentes();
		int delinquentesSize = delinquentes.size();

		// Assert expectations
		Assert.assertNotEquals("`banda.numMiembros` should not depend of `banda.delinquientes`.",
				numMiembros, delinquentesSize);
		Assert.assertEquals(expectedNumMiembros, numMiembros - expectedDelinquentesSize);
		Assert.assertEquals(expectedDelinquentesSize, delinquentesSize);
	}

}
