package ui.menu.trabajador;

import static util.console.Console.println;

import util.menu.BaseMenu;

public class TrabajadorMenu extends BaseMenu {

	public TrabajadorMenu() {
		menuOptions = new Object[][] {
				// Label - ActionClazz
				//{ "Menu option", MiAction.class }
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
