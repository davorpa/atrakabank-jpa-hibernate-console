package ui.menu.ladron;

import static util.console.Console.println;

import ui.menu.ladron.action.AtracarSucursalAction;
import ui.menu.ladron.action.EntrarEnBandaAction;
import ui.menu.ladron.action.SalirDeBandaAction;
import util.menu.BaseMenu;

public class LadronMenu extends BaseMenu {

	public LadronMenu() {
		menuOptions = new Object[][] {
				// Label - ActionClazz
				{ "Atracar sucursal", AtracarSucursalAction.class },
				{ "Entrar en banda organizada", EntrarEnBandaAction.class },
				{ "Salir de banda", SalirDeBandaAction.class }
		};
	}

	@Override
	protected void printMenuHeader() {
		super.printMenuHeader();
		println("╔═════════════════════════════════════════════════════════════════════════════╗");
		println("╠═══╣►►►►►►►►►►        MENÚ LADRÓN                              ◄◄◄◄◄◄◄◄◄◄╠═══╣");
		println("╚═════════════════════════════════════════════════════════════════════════════╝");
	}

}
