package business.service;

import static java.lang.String.format;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static util.ReflectionUtils.setField;

import java.util.Collection;
import java.util.Optional;

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

	private volatile static long longSeed = (long) (Math.random() * 100);

	@Mock(name = "bandaOrganizadaDAO")
	private BandaOrganizadaDAO dao;

	@Spy
	@InjectMocks
	private BandaOrganizadaService bandaOrganizadaService;

	@Test
	public void testFindByIdShouldReturnEntityWithNumMiembrosIndependentOfDelinquientesSize() {
		// Given
		final Long bandaId = 11L;
		long expectedNumMiembros, expectedDelincuentesSize = 0;
		final BandaOrganizada banda = setField(
				new BandaOrganizada("B001", expectedNumMiembros = 55),
				"id", bandaId);
		setField(banda.addDelincuente(setField(
				new Delincuente(format("Z00%s", ++expectedDelincuentesSize), "Zutano"),
				"id", expectedDelincuentesSize)),
				"id", ++longSeed);
		setField(banda.addDelincuente(setField(
				new Delincuente(format("M00%s", ++expectedDelincuentesSize), "Mengano"),
				"id", expectedDelincuentesSize)),
				"id", ++longSeed);
		setField(banda.addDelincuente(setField(
				new Delincuente(format("V00%s", ++expectedDelincuentesSize), "Villano"),
				"id", expectedDelincuentesSize)),
				"id", ++longSeed);

		final BandaOrganizada mockedBanda = spy(banda);

		when(dao.findOne(anyLong())).thenReturn(Optional.of(mockedBanda));

		// Do
		final BandaOrganizadaService service = ServiceFactory.getInstance().getBandaOrganizadaService();
		final BandaOrganizada result = service.findById(bandaId);

		long numMiembros = result.getNumMiembros();
		Collection<Delincuente> delincuentes = result.getDelincuentes();
		long delincuentesSize = delincuentes.size();

		// Assert expectations
		Assert.assertNotEquals("`banda.numMiembros` should not depend of `banda.delinquientes`.",
				numMiembros, delincuentesSize);
		Assert.assertEquals(expectedNumMiembros, numMiembros - expectedDelincuentesSize);
		Assert.assertEquals(expectedDelincuentesSize, delincuentesSize);

		InOrder mocksVerifier = inOrder(service, dao);
		mocksVerifier.verify(service, times(1)).findById(bandaId);
		mocksVerifier.verify(dao, times(1)).findOne(bandaId);
		mocksVerifier.verifyNoMoreInteractions();
	}

}