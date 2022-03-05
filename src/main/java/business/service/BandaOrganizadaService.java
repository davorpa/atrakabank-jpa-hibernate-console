package business.service;

import java.util.List;
import java.util.Optional;

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

	/**
	 * Obtiene el detalle de una banda organizada a partir de su clave natural.
	 *
	 * @param codigo identificador alfanumérico unequívoco.
	 * @return nunca {@code null}, {@code Optional#empty()} si no hay resultados
	 */
	public Optional<BandaOrganizada> findByCodigo(String codigo) {
		return bandaOrganizadaDAO.findByCodigo(codigo);
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
