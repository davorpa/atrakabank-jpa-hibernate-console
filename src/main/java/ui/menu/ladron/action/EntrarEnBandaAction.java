package ui.menu.ladron.action;

import static java.lang.String.format;
import static util.console.Console.printf;
import static util.console.Console.readString;

import java.util.Collection;
import java.util.List;

import business.model.AfiliacionBandaDelincuente;
import business.model.BandaOrganizada;
import business.model.Delincuente;
import business.service.BandaOrganizadaService;
import business.service.DelincuenteService;
import business.service.ServiceFactory;
import ui.ConsoleUI;
import util.menu.Action;

public class EntrarEnBandaAction implements Action {

	@Override
	public void execute() throws Exception
	{
		ConsoleUI.printHeader("AFILIACION EN BANDA ORGANIZADA :: Alta");

		final BandaOrganizadaService bandaService = ServiceFactory.getInstance().getBandaOrganizadaService();

		final List<BandaOrganizada> bandas = bandaService.findAll();
		if (ConsoleUI.printBox(bandas,
				"BANDAS ORGANIZADAS", "INFO :: Todavía no hay bandas organizadas", false)) {
			return;
		}

		BandaOrganizada banda = readBandaByCodigo(bandaService);
		if (banda == null) {
			return;
		}

		final DelincuenteService delincuenteService = ServiceFactory.getInstance().getDelincuenteService();

		final List<Delincuente> delincuentes = delincuenteService.findAll();
		if (printDelincuentes(delincuentes, banda)) {
			return;
		}

		final Delincuente delincuente;
		try {
			delincuente = ConsoleUI.readDelincuenteByCodigo(delincuentes, true);
		} catch (IllegalArgumentException e) {
			printf("%n%nERROR :: %s.%n%n", e.getLocalizedMessage());
			return;
		}

		AfiliacionBandaDelincuente afiliacion = banda.addDelincuente(delincuente);
		bandaService.update(banda);
		afiliacion = banda.findAfiliacion(afiliacion); // refresh SQL generated keys

		printf("%n%nSUCCESS :: Afiliación nº%s con código %s registrada correctamente.%n%n",
				afiliacion.getId(), afiliacion.getClaveIds());
	}


	protected boolean printDelincuentes(final Collection<Delincuente> delincuentes, final BandaOrganizada banda) {
		if (delincuentes == null || delincuentes.isEmpty()) {
			printf("%n%nINFO :: Todavía no hay delincuentes.%n%n");
			return true;
		}
		// exceptuando aquellos que ya pertenecen
		delincuentes.removeAll(banda.getDelincuentes());
		return ConsoleUI.printBox(delincuentes,
				"DELINCUENTES",
				() -> format("WARNING :: Todos los delincuentes ya pertenecen a la banda: %s", banda.getCodigo()),
				false);
	}


	protected BandaOrganizada readBandaByCodigo(final BandaOrganizadaService bandaService) {
		String codigo = readString("   Código de la banda");
		BandaOrganizada banda = bandaService.findByCodigo(codigo).orElse(null);
		if (banda == null) {
			printf("%n%nERROR :: La banda con código `%s` no existe.%n%n", codigo);
		}
		return banda;
	}

}
