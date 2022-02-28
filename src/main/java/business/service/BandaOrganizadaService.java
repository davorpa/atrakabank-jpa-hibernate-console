package business.service;

import java.util.List;

import business.dao.BandaOrganizadaDAO;
import business.model.BandaOrganizada;
import lombok.NonNull;

public class BandaOrganizadaService {

	@NonNull
	private final BandaOrganizadaDAO bandaOrganizadaDAO;


	public BandaOrganizadaService() {
		super();
		bandaOrganizadaDAO = new BandaOrganizadaDAO();
	}

	BandaOrganizadaService(@NonNull final BandaOrganizadaDAO bandaOrganizadaDAO) {
		super();
		this.bandaOrganizadaDAO = bandaOrganizadaDAO;
	}


	public List<BandaOrganizada> findAll() {
		return bandaOrganizadaDAO.findAll();
	}

	public BandaOrganizada findById(Long id) {
		return bandaOrganizadaDAO.findById(id);
	}

	public BandaOrganizada insert(BandaOrganizada banda) {
		return bandaOrganizadaDAO.insert(banda);
	}

	public void update(BandaOrganizada banda) {
		bandaOrganizadaDAO.update(banda);
	}

	public void delete(BandaOrganizada banda) {
		bandaOrganizadaDAO.remove(banda);
	}

}
