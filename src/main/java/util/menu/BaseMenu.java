package util.menu;

import java.util.ArrayList;
import java.util.List;

import util.console.Console;
import util.console.Printer;

public abstract class BaseMenu implements Action {

	protected static final int EXIT = 0;

	protected Object[][] menuOptions;
	private List<Class<Action>> actions = null;

	@Override
	public void execute() {
		int opt;
		do {
			showMenu();
			opt = getMenuOption();
			try {

				processOption(opt);

			} catch (RuntimeException rte) {
				Printer.printRuntimeException(rte);
				throw rte; // Quits the app
			} catch (Exception be) {
				Printer.printBusinessException(be);
			}
		} while (opt != EXIT);
	}

	protected void processOption(int option) throws Exception {
		if (option == EXIT)
			return;

		Class<Action> actionClass = null;
		try {
			actionClass = actions.get(option - 1);
		} catch (IndexOutOfBoundsException e) {
			Console.printf("Opcion %s no válida", option);
		}
		if (actionClass == null)
			return;

		createInstanceOf(actionClass).execute();
	}

	private Action createInstanceOf(Class<Action> clazz) {
		try {

			return clazz.newInstance();

		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	private int getMenuOption() {
		Integer opt;
		do {
			opt = Console.readInt("  Opción");
		} while (opt == null || opt < EXIT || (opt > actions.size()));
		return opt;
	}

	private void showMenu() {
		if (actions == null) {
			fillActions();
		}

		int opc = EXIT;
		printMenuHeader();
		for (Object[] row : menuOptions) {
			if ( isOptionRow(row) ) {
				printMenuOption(++opc, resolveOptionText(row, opc));
			} else {
				printMenuSeparator(resolveMenuSeparator(row));
			}
		}
		printMenuFooter();
	}

	@SuppressWarnings("unchecked")
	private void fillActions() {
		actions = new ArrayList<>();
		for (Object[] row : menuOptions) {
			if (isOptionRow(row)) {
				actions.add((Class<Action>) row[1]);
			}
		}
	}

	protected void printMenuFooter() {
		Console.println();
		printMenuOption(EXIT, "Salir");
	}

	protected void printMenuHeader() {
		Console.println();
	}

	protected void printMenuSeparator(String text) {
		Console.println(text);
	}

	protected void printMenuOption(int opc, String text) {
		Console.printf("\t%2d- %s%n", opc, text);
	}

	protected boolean isOptionRow(Object[] row) {
		return row != null && row.length > 1 && row[1] != null;
	}

	protected String resolveOptionText(Object[] row, int pos) {
		return row == null ? String.format("Opción nº%d", pos) : (String) row[0];
	}

	protected String resolveMenuSeparator(Object[] row) {
		return row == null ? "" : (String) row[0];
	}

}
