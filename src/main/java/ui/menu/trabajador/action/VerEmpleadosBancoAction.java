package ui.menu.trabajador.action;

import static util.console.Console.printf;

import java.util.Collection;
import java.util.List;

import business.model.Banco;
import business.model.Empleado;
import business.service.BancoService;
import business.service.EmpleadoService;
import business.service.ServiceFactory;
import ui.ConsoleUI;
import util.menu.Action;

/**
 * Listado de empleados de un banco
 */
public class VerEmpleadosBancoAction implements Action {

	@Override
	public void execute() throws Exception
	{
		ConsoleUI.printHeader("EMPLEADOS BANCO");

		BancoService bancoService = ServiceFactory.getInstance().getBancoService();

		final List<Banco> bancos = bancoService.findAll();
		if (ConsoleUI.printBancos(bancos)) {
			return;
		}

		Banco banco = null;
		try {
			banco = ConsoleUI.readBancoByCodigo(bancos, false);

		} catch (IllegalArgumentException e) {
			printf("%n%nERROR :: %s.%n%n", e.getLocalizedMessage());
			return;
		}

		EmpleadoService empleadoService = ServiceFactory.getInstance().getEmpleadoService();

		final Collection<Empleado> empleados = findEmpleados(empleadoService, banco);
		printEmpleados(empleados);
	}


	protected Collection<Empleado> findEmpleados(final EmpleadoService empleadoService, final Banco banco) {
		Collection<Empleado> empleados;
		if (banco == null) {
			empleados = empleadoService.findAll();
		} else if (Math.random() < 0.5){
			empleados = banco.getEmpleados();
		} else {
			empleados = empleadoService.findAllByBancoCodigo(banco.getCodigo());
		}
		return empleados;
	}


	protected void printEmpleados(final Collection<Empleado> empleados) {
		ConsoleUI.printBox(empleados,
				"EMPLEADOS",
				"INFO :: La búsqueda no ha devuelto resultados",
				true);
	}

}
