package business.dao;

import static java.lang.String.format;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

import business.SampleBaseTestCase;
import business.model.BandaOrganizada;
import business.model.Delincuente;

public class BandaOrganizadaDAOTest extends SampleBaseTestCase {

	@Mock
	private BandaOrganizadaDAO dao;

	@Test
	public void testFindByIdShouldReturnEntityWithNumMiembrosIndependentOfDelinquientesSize() {
		// Given
		final Long bandaId = 11L;
		int expectedNumMiembros, expectedDelinquentesSize = 0;
		final BandaOrganizada banda = new BandaOrganizada("B001", expectedNumMiembros = 5);
		banda.addDelincuente(new Delincuente(format("D00%s", ++expectedDelinquentesSize), "Pepito"));
		banda.addDelincuente(new Delincuente(format("D00%s", ++expectedDelinquentesSize), "Menganito"));

		final BandaOrganizada mockedBanda = spy(banda);
		when(mockedBanda.getId()).thenReturn(bandaId);

		when(dao.findById(anyLong())).thenReturn(mockedBanda);

		// Do
		final BandaOrganizada result = dao.findById(bandaId);

		int numMiembros = result.getNumMiembros();
		Collection<Delincuente> delinquentes = result.getDelincuentes();
		int delinquentesSize = delinquentes.size();

		// Assert expectations
		Assert.assertNotEquals("`banda.numMiembros` should not depend of `banda.delinquientes`.",
				numMiembros, delinquentesSize);
		Assert.assertEquals(expectedNumMiembros, numMiembros - expectedDelinquentesSize);
		Assert.assertEquals(expectedDelinquentesSize, delinquentesSize);
	}

}
