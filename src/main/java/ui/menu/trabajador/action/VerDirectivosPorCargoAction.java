package ui.menu.trabajador.action;

import static java.lang.String.format;
import static util.Strings.isEmpty;
import static util.console.Console.printf;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import business.model.Empleado;
import business.model.tipos.CargoDirectivo;
import business.service.EmpleadoService;
import business.service.ServiceFactory;
import ui.ConsoleUI;
import util.console.Console;
import util.menu.Action;

public class VerDirectivosPorCargoAction implements Action {

	@Override
	public void execute() throws Exception
	{
		ConsoleUI.printHeader("EMPLEADOS BANCO :: Directivos");

		final EmpleadoService empleadoService = ServiceFactory.getInstance().getEmpleadoService();

		if (printCargosDirectivos()) {
			return;
		}

		CargoDirectivo cargo;
		try {
			cargo = readCargoDirectivo();
		} catch (IllegalArgumentException e) {
			printf("%n%nERROR :: %s.%n%n", e.getLocalizedMessage());
			return;
		}

		final List<Empleado> empleados = empleadoService.findAllByCargo(cargo);
		printEmpleados(empleados);
	}


	private CargoDirectivo readCargoDirectivo() {
		CargoDirectivo cargo;
		String value = Console.readString("Seleccione cargo");
		try {
			cargo = isEmpty(value) ? null : CargoDirectivo.valueOf(value);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(format("El cargo introducido `%s` no es válido", value), e);
		}
		return cargo;
	}


	protected boolean printCargosDirectivos() {
		final Set<CargoDirectivo> cargosDirectivos = EnumSet.allOf(CargoDirectivo.class);
		return ConsoleUI.printBox(cargosDirectivos,
				"CARGOS DIRECTIVOS",
				"INFO :: Aún no hay cargos registrados en el sistema",
				false);
	}


	protected void printEmpleados(final Collection<Empleado> empleados) {
		ConsoleUI.printBox(empleados,
				"EMPLEADOS",
				"INFO :: Todavía no empleados con dichos criterios de búsqueda",
				true);
	}
}
