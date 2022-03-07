package business.service;

import java.util.List;

import business.dao.AtracoDAO;
import business.model.Atraco;
import lombok.NonNull;

public class AtracoService {

	@NonNull
	private final AtracoDAO atracoDAO;


	public AtracoService() {
		super();
		atracoDAO = new AtracoDAO();
	}

	AtracoService(@NonNull final AtracoDAO atracoDAO) {
		super();
		this.atracoDAO = atracoDAO;
	}


	public List<Atraco> findAll() {
		return atracoDAO.findAll();
	}

	public Atraco findById(final Long id) {
		return atracoDAO.findOne(id).orElse(null);
	}

	public Atraco insert(final Atraco atraco) {
		return atracoDAO.create(atraco);
	}

	public void update(final Atraco atraco) {
		atracoDAO.update(atraco);
	}

	public void delete(final Atraco atraco) {
		atracoDAO.delete(atraco);
	}

}
