package ui.menu.administrador;

import static util.console.Console.println;

import util.menu.BaseMenu;

public class AdministradorMenu extends BaseMenu {

	public AdministradorMenu() {
		menuOptions = new Object[][] {
				// Label - ActionClazz
				//{ "Menu option", MiAction.class }
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
