package business.dao;

import static java.lang.String.format;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static util.ReflectionUtils.setField;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

import business.SampleBaseTestCase;
import business.model.BandaOrganizada;
import business.model.Delincuente;

public class BandaOrganizadaDAOTest extends SampleBaseTestCase {

	private volatile static long longSeed = (long) (Math.random() * 100);

	@Mock
	private BandaOrganizadaDAO dao;

	@Test
	public void testFindByIdShouldReturnEntityWithNumMiembrosIndependentOfDelinquientesSize() {
		// Given
		final Long bandaId = 11L;
		long expectedNumMiembros, expectedDelincuentesSize = 0;
		final BandaOrganizada banda = setField(
				new BandaOrganizada("B001", expectedNumMiembros = 5),
				"id", bandaId);
		setField(banda.addDelincuente(setField(
				new Delincuente(format("D00%s", ++expectedDelincuentesSize), "Pepito"),
				"id", expectedDelincuentesSize)),
				"id", ++longSeed);
		setField(banda.addDelincuente(setField(
				new Delincuente(format("D00%s", ++expectedDelincuentesSize), "Menganito"),
				"id", expectedDelincuentesSize)),
				"id", ++longSeed);

		final BandaOrganizada mockedBanda = spy(banda);

		when(dao.findOne(anyLong())).thenReturn(Optional.of(mockedBanda));

		// Do
		final BandaOrganizada result = dao.findOne(bandaId).orElseThrow(NoSuchElementException::new);

		long numMiembros = result.getNumMiembros();
		Collection<Delincuente> delincuentes = result.getDelincuentes();
		long delincuentesSize = delincuentes.size();

		// Assert expectations
		Assert.assertNotEquals("`banda.numMiembros` should not depend of `banda.delinquientes`.",
				numMiembros, delincuentesSize);
		Assert.assertEquals(expectedNumMiembros, numMiembros - expectedDelincuentesSize);
		Assert.assertEquals(expectedDelincuentesSize, delincuentesSize);
	}

}
