package ui.menu.administrador.action;

import static util.console.Console.printf;
import static util.console.Console.println;
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
		if (bancos.isEmpty()) {
			printf("%n%nINFO :: Todavía no hay bancos.%n%n");
			return;
		}
		println("");
		println("                        ╔════════════════════════════╗                        ");
		println("  ◄╔════════════════════╝►          BANCOS          ◄╚════════════════════╗►  ");
		bancos.forEach(Console::println);
		println("  ◄╚══════════════════════════════╣► ♦♦♦♦ ◄╠══════════════════════════════╝►  ");

		String codigo = readString("   Código del banco");

		SucursalService sucursalService = ServiceFactory.getInstance().getSucursalService();

		List<Sucursal> sucursales = sucursalService.findByCodigoBanco(codigo);
		if (sucursales.isEmpty()) {
			printf("%n%nINFO :: La búsqueda no ha devuelto resultados.%n%n");
			return;
		}
		println("");
		println("                        ╔════════════════════════════╗                        ");
		println("  ◄╔════════════════════╝►        SUCURSALES        ◄╚════════════════════╗►  ");
		sucursales.forEach(Console::println);
		println("  ◄╚══════════════════════════════╣► ♦♦♦♦ ◄╠══════════════════════════════╝►  ");
		printf("         Número de sucursales: %3s%n", sucursales.size());
	}

}
