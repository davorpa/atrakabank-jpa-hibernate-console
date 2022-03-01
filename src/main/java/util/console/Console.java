package util.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class Console {

	private static final String FORMATSTR_DEF = "%s: ";


	protected static BufferedReader kbd = new BufferedReader(
			new InputStreamReader(System.in));

	protected static PrintStream pout = System.out;
	protected static PrintStream perr = System.err;

	public static void println() {
		pout.println();
	}

	public static void println(Object obj) {
		println( obj == null ? null : obj.toString() );
	}

	public static void println(String string) {
		pout.println(string);
	}

	public static void print(String string) {
		pout.print(string);
	}

	public static void printf(String format, Object... args) {
		pout.printf(format, args);
	}


	public static void eprintln() {
		perr.println();
	}

	public static void eprintln(Object obj) {
		eprintln( obj == null ? null : obj.toString() );
	}

	public static void eprintln(String string) {
		perr.println(string);
	}

	public static void eprint(String string) {
		perr.print(string);
	}

	public static void eprintf(String format, Object... args) {
		perr.printf(format, args);
	}



	public static Integer readInt() {
		try {
			return Integer.parseInt(readString());
		} catch (NumberFormatException nfe) {
			return null;
		}
	}

	public static Long readLong() {
		try {
			return Long.parseLong(readString());
		} catch (NumberFormatException nfe) {
			return null;
		}
	}

	public static Double readDouble() {
		try {
			return Double.parseDouble(readString());
		} catch (NumberFormatException nfe) {
			return null;
		}
	}

	public static String readString() {
		try {
			return kbd.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String readString(String msg) {
		printf(FORMATSTR_DEF, msg);
		return readString();
	}

	public static Long readLong(String msg) {
		printf(FORMATSTR_DEF, msg);
		return readLong();
	}

	public static Integer readInt(String msg) {
		printf(FORMATSTR_DEF, msg);
		return readInt();
	}

	public static double readDouble(String msg) {
		printf(FORMATSTR_DEF, msg);
		return readDouble();
	}

}
