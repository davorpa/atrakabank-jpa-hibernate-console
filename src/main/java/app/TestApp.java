package app;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;

import business.dao.JPAUtil;
import business.model.Atraco;
import business.model.Banco;
import business.model.BandaOrganizada;
import business.model.Contrato;
import business.model.Delincuente;
import business.model.Juez;
import business.model.Sucursal;
import business.model.Vigilante;
import business.model.tipos.TipoCondena;
import business.service.AtracoService;
import business.service.BancoService;
import business.service.BandaOrganizadaService;
import business.service.ContratoService;
import business.service.DelincuenteService;
import business.service.JuezService;
import business.service.ServiceFactory;
import business.service.SucursalService;
import business.service.VigilanteService;

/**
 * Clase para probar el ejercicio de los atracadores.
 * 
 * Se recomienda depurar e ir viendo los cambios en los objetos y BD
 *
 * La opción 1 crea una banco y una sucursal y asigna la sucursal al banco.
 * Las opciones 1, 2 y 3 son independiente del resto.
 * Con la opción 2 se puede ver la sucursal que acabamos de crear para ese banco.
 * Con la opción 3 se desasocia la sucursal del banco y luego se borran ambos.
 *
 * ------------------------------------------------------------------------------
 * El resto de opciones se deben probar en orden: 4, 5, 6 y 7, y sólo se puede
 * ejecutar la secuencia una sóla vez.
 *
 * Para realizar otra prueba arrancar de nuevo la aplicación con la estrategia
 * de generación de BD create-drop, para que borre la información de las tablas.
 *
 */
public class TestApp {

	public static void main(String[] args) {
		PrintStream sysout = System.out;

		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

		BancoService bancoService = ServiceFactory.getInstance().getBancoService();
		SucursalService sucursalService = ServiceFactory.getInstance().getSucursalService();
		DelincuenteService delincuenteService = ServiceFactory.getInstance().getDelincuenteService();
		AtracoService atracoService = ServiceFactory.getInstance().getAtracoService();
		VigilanteService vigilanteService = ServiceFactory.getInstance().getVigilanteService();
		ContratoService contratoService = ServiceFactory.getInstance().getContratoService();
		JuezService juezService = ServiceFactory.getInstance().getJuezService();
		BandaOrganizadaService bandaOrganizadaService = ServiceFactory.getInstance().getBandaOrganizadaService();

		int opc = -1;
		try (Scanner scanner = new Scanner(System.in)) {

			// Datos para las pruebas de las opciones: 4 a 7
			Delincuente delincuente = new Delincuente("DEL01", "J.F.F.");
			Banco banco2 = new Banco("BAN02", "sede central", 3);
			Sucursal sucursal2 = new Sucursal("SUC02", "ciudad 2", "direccion 2", "Nombre del director", 4);
			Atraco atraco = new Atraco(new Date());

			Vigilante vigilante = new Vigilante("VIG01", "Nombre del vigilante");
			Contrato contrato = new Contrato("CON01", new Date(), new Date(), Boolean.TRUE);

			while (opc != 0) {
				sysout.println("1. Asociar sucursal a un banco");
				sysout.println("2. Ver las sucursales de un banco");
				sysout.println("3. Eliminar sucursal");
				sysout.println("-----------------------------------------");
				sysout.println("4. Atracar sucursal");
				sysout.println("5. Contratar seguridad");
				sysout.println("6. Juzgar atraco");
				sysout.println("7. Asignar delincuente a banda organizada");
				sysout.println("0. Salir");
				sysout.print("Introducir opción: ");

				opc = scanner.nextInt();
				sysout.println();

				switch (opc) {
				case 0:
					break;
				case 1:
					// asociar sucursal a un banco
					Banco banco1 = new Banco("BAN01", "sede central", 3);
					Sucursal sucursal1 = new Sucursal("SUC01", "ciudad 1", "direccion 1", "Nombre del director", 4);
					banco1.addSucursal(sucursal1);
					bancoService.insert(banco1);
					sucursalService.insert(sucursal1);
					sysout.println("Se ha asociado la sucursal: ");
					sysout.println(sucursal1);
					sysout.println("al banco: ");
					sysout.println(banco1);
					break;

				case 2:
					// Listado de sucursales de un banco
					List<Sucursal> sucursales = new ArrayList<Sucursal>();
					sucursales = sucursalService.findByCodigoBanco("BAN01");
					for (Sucursal s : sucursales) {
						sysout.println(s);
					}
					break;

				case 3:
					// Eliminar sucursal del banco
					Sucursal s = sucursalService.findByCodigo("SUC01");
					Banco b = bancoService.findByCodigo("BAN01");
					b.removeSucursal(s);
					sucursalService.update(s);
					bancoService.update(b);
					sysout.println(b);
					// borramos sucursal y banco
					sucursalService.delete(s);
					bancoService.delete(b);

					break;

				case 4:
					// atracar una sucursal: SUC02 de BAN01
					// asociamos sucursal al banco
					banco2.addSucursal(sucursal2);

					// asociar delincuente y atraco
					delincuente.addAtraco(atraco);
					// asociar atraco y sucursal
					sucursal2.addAtraco(atraco);

					// persistimos en BD
					bancoService.insert(banco2);
					sucursalService.insert(sucursal2);
					delincuenteService.insert(delincuente);
					atracoService.insert(atraco);
					sysout.println("Se ha registrado el atraco correctamente");
					sysout.println("Sucursal afectada: ");
					sysout.println(sucursal2);
					sysout.println("Registro del atraco: ");
					sysout.println(atraco);
					break;

				case 5:
					// contratar seguridad: vigilante y contrato
					// añadir al vigilante y a la sucursal el contrato

					vigilante.addContrato(contrato);
					sucursal2 = sucursalService.findByCodigo("SUC02");
					sucursal2.addContrato(contrato);

					// guardar vigilante, contrato y actualizar: sucursal
					vigilanteService.insert(vigilante);
					sucursalService.update(sucursal2);
					contratoService.insert(contrato);
					break;

				case 6:
					// juzgar atraco anterior
					Juez juez = new Juez("J01", "nombre de juez");
					juez.addAtraco(atraco);
					atraco.setTipoCondena(TipoCondena.GRAVE);
					// guardar juez y actualizar atraco
					juezService.insert(juez);
					atracoService.update(atraco);
					break;

				case 7:
					// Asociar delincuente a banda organizada
					BandaOrganizada bandaOrganizada = new BandaOrganizada("BAN01", 0);
					bandaOrganizada.addDelincuente(delincuente);
					// guardar banda y actualizar delincuente
					bandaOrganizadaService.insert(bandaOrganizada);
					delincuenteService.update(delincuente);
					break;

				default:
					sysout.printf("Opción inválida %n%n");
				}
			}

			JPAUtil.closeEntityManager(em);
			JPAUtil.closeEntityManagerFactory();
		}
	}

}
