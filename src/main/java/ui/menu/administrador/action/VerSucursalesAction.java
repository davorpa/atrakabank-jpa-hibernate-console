package ui.menu.administrador.action;

import static util.console.Console.printf;

import java.util.Collection;
import java.util.List;

import business.model.Banco;
import business.model.Sucursal;
import business.service.BancoService;
import business.service.ServiceFactory;
import business.service.SucursalService;
import ui.ConsoleUI;
import util.menu.Action;

/**
 * Listado de sucursales de un banco.
 */
public class VerSucursalesAction implements Action {

	@Override
	public void execute() throws Exception
	{
		ConsoleUI.printHeader("SUCURSALES BANCO");

		BancoService bancoService = ServiceFactory.getInstance().getBancoService();

		final List<Banco> bancos = bancoService.findAll();
		if (ConsoleUI.printBancos(bancos)) {
			return;
		}

		Banco banco = null;
		try {
			banco = ConsoleUI.readBancoByCodigo(bancos, true);

		} catch (IllegalArgumentException e) {
			printf("%n%nERROR :: %s.%n%n", e.getLocalizedMessage());
			return;
		}

		SucursalService sucursalService = ServiceFactory.getInstance().getSucursalService();

		final List<Sucursal> sucursales = sucursalService.findAllByCodigoBanco(banco.getCodigo());
		printSucursales(sucursales);
	}


	protected void printSucursales(final Collection<Sucursal> sucursales) {
		ConsoleUI.printBox(sucursales,
				"SUCURSALES",
				"INFO :: La búsqueda no ha devuelto resultados",
				true);
	}

}
