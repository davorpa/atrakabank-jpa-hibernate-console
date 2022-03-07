package business.model;

import static java.lang.String.format;
import static util.ReflectionUtils.setField;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BandaOrganizadaTest {

	private volatile static long longSeed = 0;

	private BandaOrganizada banda;

	@Before
	public void setUp() {
		banda = setField(new BandaOrganizada(), "id", ++longSeed);
	}

	@Test
	public void testNumMiembrosIsIndependentOfDelinquientesSize() {
		// Given
		long expectedNumMiembros, expectedDelincuentesSize = 0;
		banda.setNumMiembros(expectedNumMiembros = 11);
		banda.addDelincuente(setField(
				new Delincuente(format("P00%s", ++expectedDelincuentesSize), "Pepito"),
				"id", expectedDelincuentesSize)
			).setId(++longSeed);
		banda.addDelincuente(setField(
				new Delincuente(format("F00%s", ++expectedDelincuentesSize), "Fulanito"),
				"id", expectedDelincuentesSize)
			).setId(++longSeed);
		banda.addDelincuente(setField(
				new Delincuente(format("M00%s", ++expectedDelincuentesSize), "Menganito"),
				"id", expectedDelincuentesSize)
			).setId(++longSeed);

		// Do
		long numMiembros = banda.getNumMiembros();
		Collection<Delincuente> delincuentes = banda.getDelincuentes();
		long delincuentesSize = delincuentes.size();

		// Assert expectations
		Assert.assertNotEquals("`banda.numMiembros` should not depend of `banda.delinquientes`.",
				numMiembros, delincuentesSize);
		Assert.assertEquals(expectedNumMiembros, numMiembros - expectedDelincuentesSize);
		Assert.assertEquals(expectedDelincuentesSize, delincuentesSize);
	}

}
