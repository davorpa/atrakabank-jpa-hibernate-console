package ui.menu.administrador.action;

import static util.console.Console.printf;
import static util.console.Console.readString;

import java.util.List;

import business.model.Banco;
import business.model.Sucursal;
import business.service.BancoService;
import business.service.ServiceFactory;
import business.service.SucursalService;
import util.console.Console;
import util.menu.Action;

/**
 * Listado de sucursales de un banco.
 */
public class VerSucursalesAction implements Action {

	@Override
	public void execute() throws Exception {
		BancoService bancoService = ServiceFactory.getInstance().getBancoService();

		List<Banco> bancos = bancoService.findAll();
		bancos.forEach(Console::println);
		String codigo = readString("Código del banco");

		SucursalService sucursalService = ServiceFactory.getInstance().getSucursalService();

		List<Sucursal> sucursales = sucursalService.findByCodigoBanco(codigo);
		sucursales.forEach(Console::println);
		printf("Número de sucursales: %s%n", sucursales.size());
	}

}
