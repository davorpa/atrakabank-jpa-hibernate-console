package util.console;

import java.io.PrintStream;

/**
 * Métodos de utilidad para escribir cosas en pantalla de forma controlada.
 * Aquí irían todas las decoraciones pertinentes.
 */
public class Printer {

	private static PrintStream pout = System.out;
	
	public static void printHeading(String string) {
		pout.println(string);
	}

	/**
	 * Avisa de error lógico en la ejecución, muy probablemente por 
	 * equivocación del usuario o por circunstancias que han cambiado 
	 * durante el "think time" del usuario (control optimista y eso...)
	 * 
	 * @param e
	 */
	public static void printBusinessException(Exception e) {
		//TODO: Hacer esto un poco más curioso según lo pida la interfaz
		printException("Ha ocurrido un problema procesando su opción:", e);
	}

	/**
	 * Avisa de error irrecuperable en la interfaz del usuario
	 * @param e
	 */
	public static void printRuntimeException(RuntimeException e) {
		pout.println("Ha ocurrido un error interno no recuperable, el programa debe terminar.");
		pout.println("A continuación se muestra una traza del error.");
		pout.println("[la traza no sería visible por el usuario en una aplicación final]");
		e.printStackTrace();
	}

	/**
	 * Avisa de error irrecuperable en la interfaz del usuario
	 * @param message
	 * @param e
	 */
	public static void printException(String message, Exception e) {
		pout.println(message);
		pout.format("\t- %s%n", e.getLocalizedMessage());
	}

}
