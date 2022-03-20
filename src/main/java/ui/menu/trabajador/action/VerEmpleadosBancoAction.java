package ui.menu.trabajador.action;

import static util.console.Console.printf;
import static util.console.Console.println;
import static util.console.Console.readString;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import business.model.Banco;
import business.model.Empleado;
import business.service.BancoService;
import business.service.EmpleadoService;
import business.service.ServiceFactory;
import lombok.NonNull;
import util.console.Console;
import util.menu.Action;

/**
 * Listado de empleados de un banco
 */
public class VerEmpleadosBancoAction implements Action {

	@Override
	public void execute() throws Exception
	{
		BancoService bancoService = ServiceFactory.getInstance().getBancoService();
		EmpleadoService empleadoService = ServiceFactory.getInstance().getEmpleadoService();

		final List<Banco> bancos = requestForBancos(bancoService);
		if (bancos == null) {
			return;
		}

		String codigo = readString("Código del banco");

		// Random load balance strategy
		if (Math.random() < 0.5) {
			printEmpleadosUsingService(empleadoService, codigo);
		} else {
			printEmpleadosUsingCollection(bancos, codigo);
		}
	}

	protected List<Banco> requestForBancos(
			final BancoService bancoService) {
		final List<Banco> bancos = bancoService.findAll();
		if (bancos.isEmpty()) {
			printf("INFO :: Todavía no hay bancos.%n%n");
			return null;
		}
		println("==========  ENTIDADES BANCARIAS  ======================================");
		bancos.forEach(Console::println);
		return bancos;
	}

	protected void printEmpleadosUsingService(final @NonNull EmpleadoService empleadoService, final String codigo) {
		final List<Empleado> empleados = empleadoService.findAllByBancoCodigo(codigo);
		printEmpleados(empleados);
	}

	protected void printEmpleadosUsingCollection(final @NonNull Collection<Banco> bancos, final String codigo) {
		final Predicate<Banco> haveCodigoEqualsTo = banco -> Objects.equals(banco.getCodigo(), codigo);
		Banco banco = bancos.stream()
				.filter(Objects::nonNull)
				.filter(haveCodigoEqualsTo)
				.findAny()
				.orElse(null);
		if (banco == null) {
			printf("ERROR :: El banco con código `%s` no existe.%n%n", codigo);
			return;
		}
		final Set<Empleado> empleados = banco.getEmpleados();
		printEmpleados(empleados);
	}

	protected void printEmpleados(final Collection<Empleado> empleados) {
		if (empleados.isEmpty()) {
			printf("INFO :: Todavía no hay empleados.%n%n");
			return;
		}
		empleados.forEach(Console::println);
		printf("Total de empleados: %s", empleados.size());
	}

}
