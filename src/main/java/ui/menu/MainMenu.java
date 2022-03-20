package ui.menu;

import ui.ConsoleUI;
import ui.menu.administrador.AdministradorMenu;
import ui.menu.ladron.LadronMenu;
import ui.menu.trabajador.TrabajadorMenu;
import util.menu.BaseMenu;

public class MainMenu extends BaseMenu {

	public MainMenu() {
		menuOptions = new Object[][] {
				// Label - ActionClazz
				{ "Administrador", AdministradorMenu.class },
				{ "Trabajador", TrabajadorMenu.class },
				{ "Ladrón", LadronMenu.class }
		};
	}

	@Override
	protected void printMenuHeader() {
		super.printMenuHeader();
		ConsoleUI.printHeader("MENÚ PRINCIPAL");
	}

}
