package ui.menu.ladron;

import static util.console.Console.println;

import util.menu.BaseMenu;

public class LadronMenu extends BaseMenu {

	public LadronMenu() {
		menuOptions = new Object[][] {
				// Label - ActionClazz
				//{ "Menu option", MiAction.class }
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
