package business.service;

import static java.lang.String.format;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import business.SampleBaseTestCase;
import business.dao.BandaOrganizadaDAO;
import business.model.BandaOrganizada;
import business.model.Delincuente;

public class BandaOrganizadaServiceTest extends SampleBaseTestCase {

	@Mock(name = "bandaOrganizadaDAO")
	private BandaOrganizadaDAO dao;

	@InjectMocks
	private BandaOrganizadaService service;

	@Test
	public void testFindByIdShouldReturnEntityWithNumMiembrosIndependentOfDelinquientesSize() {
		// Given
		final Long bandaId = 11L;
		int expectedNumMiembros, expectedDelinquentesSize = 0;
		final BandaOrganizada banda = new BandaOrganizada("B001", expectedNumMiembros = 55);
		banda.addDelincuente(new Delincuente(format("Z00%s", ++expectedDelinquentesSize), "Zutano"));
		banda.addDelincuente(new Delincuente(format("M00%s", ++expectedDelinquentesSize), "Mengano"));
		banda.addDelincuente(new Delincuente(format("V00%s", ++expectedDelinquentesSize), "Villano"));

		final BandaOrganizada mockedBanda = spy(banda);
		when(mockedBanda.getId()).thenReturn(bandaId);

		when(dao.findById(bandaId)).thenReturn(mockedBanda);

		// Do
		final BandaOrganizada result = service.findById(bandaId);

		int numMiembros = result.getNumMiembros();
		Collection<Delincuente> delinquentes = result.getDelincuentes();
		int delinquentesSize = delinquentes.size();

		// Assert expectations
		Assert.assertNotEquals("`banda.numMiembros` should not depend of `banda.delinquientes`.",
				numMiembros, delinquentesSize);
		Assert.assertEquals(expectedNumMiembros, numMiembros - expectedDelinquentesSize);
		Assert.assertEquals(expectedDelinquentesSize, delinquentesSize);

		verify(dao, times(1)).findById(bandaId);
		verifyNoMoreInteractions(dao);
	}

}