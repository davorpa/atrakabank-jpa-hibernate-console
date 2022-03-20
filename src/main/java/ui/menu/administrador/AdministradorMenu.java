package ui.menu.administrador;

import static util.console.Console.println;

import ui.menu.administrador.action.AsociarSucursalAction;
import ui.menu.administrador.action.EliminarSucursalAction;
import ui.menu.administrador.action.VerSucursalesAction;
import ui.menu.trabajador.action.VerEmpleadosBancoAction;
import util.menu.BaseMenu;

public class AdministradorMenu extends BaseMenu {

	public AdministradorMenu() {
		menuOptions = new Object[][] {
				// Label - ActionClazz
				{ "Asociar sucursal a un banco", AsociarSucursalAction.class },
				{ "Ver las sucursales de un banco", VerSucursalesAction.class },
				{ "Eliminar sucursal", EliminarSucursalAction.class },
				null,
				{ "Ver los empleados de un banco", VerEmpleadosBancoAction.class }
		};
	}

	@Override
	protected void printMenuHeader() {
		super.printMenuHeader();
		println("╔═════════════════════════════════════════════════════════════════════════════╗");
		println("╠═══╣►►►►►►►►►►        MENÚ ADMINISTRADOR                       ◄◄◄◄◄◄◄◄◄◄╠═══╣");
		println("╚═════════════════════════════════════════════════════════════════════════════╝");
	}

}
