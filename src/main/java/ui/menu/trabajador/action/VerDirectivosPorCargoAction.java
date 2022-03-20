package ui.menu.trabajador.action;

import static util.console.Console.printf;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import business.model.Empleado;
import business.model.tipos.CargoDirectivo;
import business.service.EmpleadoService;
import business.service.ServiceFactory;
import util.console.Console;
import util.menu.Action;

public class VerDirectivosPorCargoAction implements Action {

	@Override
	public void execute() throws Exception {
		final EmpleadoService empleadoService = ServiceFactory.getInstance().getEmpleadoService();

		Arrays.asList(CargoDirectivo.values()).forEach(Console::println);
		CargoDirectivo cargo;
		String cargostr = Console.readString("Seleccione cargo");
		try {
			cargo = cargostr == null || cargostr.isEmpty() ? null : CargoDirectivo.valueOf(cargostr);
		} catch (IllegalArgumentException e) {
			Console.printf("ERROR :: El cargo introducido no es válido: %s%n%n", cargostr);
			return;
		}

		List<Empleado> empleados = empleadoService.findAllByCargo(cargo);
		printEmpleados(empleados);
	}


	protected void printEmpleados(final Collection<Empleado> empleados) {
		if (empleados.isEmpty()) {
			printf("INFO :: Todavía no empleados con dichos criterios de búsqueda.%n%n");
			return;
		}
		empleados.forEach(Console::println);
		printf("Total de empleados: %s", empleados.size());
	}
}
