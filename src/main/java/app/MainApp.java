package app;

import java.io.PrintStream;
import java.util.Scanner;

import javax.persistence.EntityManager;

import business.dao.JPAUtil;

/**
 * Clase para probar el ejercicio de los atracadores.
 */
public class MainApp {

	public static void main(String[] args) {
		final PrintStream sysout = System.out;

		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

		sysout.println("Presione ENTER para continuar...");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();

		JPAUtil.closeEntityManager(em);
		JPAUtil.closeEntityManagerFactory();
		scanner.close();
	}

}
