package business.service;

import java.util.List;

import business.dao.VigilanteDAO;
import business.model.Vigilante;
import lombok.NonNull;

public class VigilanteService {

	@NonNull
	private VigilanteDAO vigilanteDAO;


	public VigilanteService() {
		super();
		vigilanteDAO = new VigilanteDAO();
	}

	VigilanteService(@NonNull final VigilanteDAO vigilanteDAO) {
		super();
		this.vigilanteDAO = vigilanteDAO;
	}


	public List<Vigilante> findAll() {
		return vigilanteDAO.findAll();
	}

	public Vigilante findById(final Long idVigilante) {
		return vigilanteDAO.findOne(idVigilante).orElse(null);
	}

	public Vigilante insert(final Vigilante vigilante) {
		return vigilanteDAO.create(vigilante);
	}

	public void update(final Vigilante vigilante) {
		vigilanteDAO.update(vigilante);
	}

	public void delete(final Vigilante vigilante) {
		vigilanteDAO.delete(vigilante);
	}

}
