package ui.menu.trabajador;

import ui.ConsoleUI;
import ui.menu.administrador.action.VerSucursalesAction;
import ui.menu.trabajador.action.VerDirectivosPorCargoAction;
import ui.menu.trabajador.action.VerEmpleadosBancoAction;
import util.menu.BaseMenu;

public class TrabajadorMenu extends BaseMenu {

	public TrabajadorMenu() {
		menuOptions = new Object[][] {
				// Label - ActionClazz
				{ "Ver las sucursales de un banco", VerSucursalesAction.class },
				null,
				{ "Ver los empleados de un banco", VerEmpleadosBancoAction.class },
				{ "Ver los directivos por cargo", VerDirectivosPorCargoAction.class }
		};
	}

	@Override
	protected void printMenuHeader() {
		super.printMenuHeader();
		ConsoleUI.printHeader("MENÚ TRABAJADOR");
	}

}
