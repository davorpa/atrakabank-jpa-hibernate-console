package ui.menu.trabajador;

import static util.console.Console.println;

import ui.menu.administrador.action.VerSucursalesAction;
import util.menu.BaseMenu;

public class TrabajadorMenu extends BaseMenu {

	public TrabajadorMenu() {
		menuOptions = new Object[][] {
				// Label - ActionClazz
				{ "Ver las sucursales de un banco", VerSucursalesAction.class }
		};
	}

	@Override
	protected void printMenuHeader() {
		super.printMenuHeader();
		println("╔═════════════════════════════════════════════════════════════════════════════╗");
		println("╠═══╣►►►►►►►►►►        MENÚ TRABAJADOR                          ◄◄◄◄◄◄◄◄◄◄╠═══╣");
		println("╚═════════════════════════════════════════════════════════════════════════════╝");
	}

}
