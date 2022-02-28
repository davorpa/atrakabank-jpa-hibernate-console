package business.service;

import static java.lang.String.format;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import business.ServiceFactoredBaseTestCase;
import business.dao.BandaOrganizadaDAO;
import business.model.BandaOrganizada;
import business.model.Delincuente;

public class BandaOrganizadaServiceFactoredTest extends ServiceFactoredBaseTestCase {

	@Mock(name = "bandaOrganizadaDAO")
	private BandaOrganizadaDAO dao;

	@Spy
	@InjectMocks
	private BandaOrganizadaService bandaOrganizadaService;

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
		final BandaOrganizadaService service = ServiceFactory.getInstance().getBandaOrganizadaService();
		final BandaOrganizada result = service.findById(bandaId);

		int numMiembros = result.getNumMiembros();
		Collection<Delincuente> delinquentes = result.getDelincuentes();
		int delinquentesSize = delinquentes.size();

		// Assert expectations
		Assert.assertNotEquals("`banda.numMiembros` should not depend of `banda.delinquientes`.",
				numMiembros, delinquentesSize);
		Assert.assertEquals(expectedNumMiembros, numMiembros - expectedDelinquentesSize);
		Assert.assertEquals(expectedDelinquentesSize, delinquentesSize);

		InOrder mocksVerifier = inOrder(service, dao);
		mocksVerifier.verify(service, times(1)).findById(bandaId);
		mocksVerifier.verify(dao, times(1)).findById(bandaId);
		mocksVerifier.verifyNoMoreInteractions();
	}

}