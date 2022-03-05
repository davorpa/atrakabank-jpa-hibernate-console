package ui.menu.ladron.action;

import static util.console.Console.printf;
import static util.console.Console.println;
import static util.console.Console.readString;

import java.util.List;

import business.model.AfiliacionBandaDelincuente;
import business.model.BandaOrganizada;
import business.model.Delincuente;
import business.service.BandaOrganizadaService;
import business.service.DelincuenteService;
import business.service.ServiceFactory;
import util.console.Console;
import util.menu.Action;

public class EntrarEnBandaAction implements Action {

	@Override
	public void execute() throws Exception
	{
		final BandaOrganizadaService bandaService = ServiceFactory.getInstance().getBandaOrganizadaService();

		BandaOrganizada banda = requestForBandas(bandaService);
		if (banda == null) {
			return;
		}

		final DelincuenteService delincuenteService = ServiceFactory.getInstance().getDelincuenteService();

		Delincuente delincuente = requestForDelincuentes(delincuenteService, banda);
		if (delincuente == null) {
			return;
		}

		AfiliacionBandaDelincuente afiliacion = banda.addDelincuente(delincuente);
		bandaService.update(banda);
		afiliacion = banda.findAfiliacion(afiliacion); // refresh SQL generated keys

		printf("%n%nSUCCESS :: Afiliación nº%s con código %s registrada correctamente.%n%n",
				afiliacion.getId(), afiliacion.getClaveIds());
	}

	private BandaOrganizada requestForBandas(
			final BandaOrganizadaService bandaService)
	{
		List<BandaOrganizada> bandas = bandaService.findAll();
		if (bandas.isEmpty()) {
			printf("%n%nINFO :: Todavía no hay bandas organizadas.%n%n");
			return null;
		}
		println("");
		println("                        ╔════════════════════════════╗                        ");
		println("  ◄╔════════════════════╝►    BANDAS ORGANIZADAS    ◄╚════════════════════╗►  ");
		bandas.forEach(Console::println);
		println("  ◄╚══════════════════════════════╣► ♦♦♦♦ ◄╠══════════════════════════════╝►  ");

		String codigo = readString("   Código de la banda");
		BandaOrganizada banda = bandaService.findByCodigo(codigo).orElse(null);
		if (banda == null) {
			printf("%n%nERROR :: La banda con código `%s` no existe.%n%n", codigo);
		}
		return banda;
	}

	private Delincuente requestForDelincuentes(
			final DelincuenteService delincuenteService,
			final BandaOrganizada banda)
	{
		List<Delincuente> delincuentes = delincuenteService.findAll();
		if (delincuentes.isEmpty()) {
			printf("%n%nINFO :: Todavía no hay delincuentes.%n%n");
			return null;
		}
		// exceptuando aquellos que ya pertenecen
		delincuentes.removeAll(banda.getDelincuentes());
		if (delincuentes.isEmpty()) {
			printf("%n%nWARNING :: Todos los delincuentes ya pertenecen a la banda: %s%n%n", banda.getCodigo());
			return null;
		}
		println("");
		println("                        ╔════════════════════════════╗                        ");
		println("  ◄╔════════════════════╝►       DELINCUENTES       ◄╚════════════════════╗►  ");
		delincuentes.forEach(Console::println);
		println("  ◄╚══════════════════════════════╣► ♦♦♦♦ ◄╠══════════════════════════════╝►  ");

		String codigo = readString("   Código del delincuente");
		Delincuente delincuente = delincuenteService.findByCodigo(codigo).orElse(null);
		if (delincuente == null) {
			printf("%n%nERROR :: El delincuente con código %s no existe.%n%n", codigo);
		}
		return delincuente;
	}

}
