package business.service;

import lombok.Getter;
import lombok.Setter;

public class ServiceFactory {

	// tantas propiedades como servicios

	private BancoService bancoService;

	@Setter
	@Getter
	private SucursalService sucursalService;

	@Setter
	@Getter
	private DelincuenteService delincuenteService;

	@Setter
	@Getter
	private AtracoService atracoService;

	@Setter
	@Getter
	private VigilanteService vigilanteService;

	@Setter
	@Getter
	private ContratoService contratoService;

	@Setter
	@Getter
	private JuezService juezService;

	@Setter
	@Getter
	private BandaOrganizadaService bandaOrganizadaService;



	private static volatile ServiceFactory serviceFactory;

	private ServiceFactory() {
		super();
	}

	public static ServiceFactory getInstance() {
		// Use singleton but with thread safe lazy strategy instantiation
		// (volatile + double race sync)
		ServiceFactory sf;
		if ((sf = serviceFactory) == null) {
			synchronized (ServiceFactory.class) {
				if ((sf = serviceFactory) == null) {
					serviceFactory = sf = new ServiceFactory();

					// tantas como servicios
					sf.setBancoService(new BancoService());
					sf.setSucursalService(new SucursalService());
					sf.setDelincuenteService(new DelincuenteService());
					sf.setAtracoService(new AtracoService());
					sf.setVigilanteService(new VigilanteService());
					sf.setContratoService(new ContratoService());
					sf.setJuezService(new JuezService());
					sf.setBandaOrganizadaService(new BandaOrganizadaService());
				}
			}
		}
		return sf;
	}

	public static ServiceFactory getServiceFactory() {
		return serviceFactory;
	}

	public static void setServiceFactory(final ServiceFactory serviceFactory) {
		ServiceFactory.serviceFactory = serviceFactory;
	}



	public BancoService getBancoService() {
		return bancoService;
	}

	private void setBancoService(final BancoService bancoService) {
		this.bancoService = bancoService;
	}

}
