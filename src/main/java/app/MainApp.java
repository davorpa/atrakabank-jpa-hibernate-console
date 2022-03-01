package app;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import ui.menu.MainMenu;


public class MainApp {

	public static void main(String[] args) {
		if (!verboseActive(args)) {
			suppressLogs();
		}
		new MainMenu().execute();
	}

	private static boolean verboseActive(String[] args) {
		for (String arg: args) {
			if ("-v".equals(arg) || "--verbose".equals(arg) || "--debug".equals(arg)) {
				return true;
			}
		}
		return false;
	}

	private static void suppressLogs() {
		LogManager.getLogManager().reset();
		Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
		globalLogger.setLevel(java.util.logging.Level.OFF);
	}
}
