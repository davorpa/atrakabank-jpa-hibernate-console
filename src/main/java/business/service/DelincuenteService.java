package business.service;

import java.util.List;

import business.dao.DelincuenteDAO;
import business.model.Delincuente;
import lombok.NonNull;

public class DelincuenteService {

	@NonNull
	private DelincuenteDAO delincuenteDAO;


	public DelincuenteService() {
		super();
		delincuenteDAO = new DelincuenteDAO();
	}

	DelincuenteService(@NonNull final DelincuenteDAO delincuenteDAO) {
		super();
		this.delincuenteDAO = delincuenteDAO;
	}


	public List<Delincuente> findAll() {
		return delincuenteDAO.findAll();
	}

	public Delincuente findById(final Long idDelincuente) {
		return delincuenteDAO.findById(idDelincuente);
	}

	public Delincuente insert(final Delincuente delincuente) {
		return delincuenteDAO.insert(delincuente);
	}

	public void update(final Delincuente delincuente) {
		delincuenteDAO.update(delincuente);
	}

	public void delete(final Delincuente delincuente) {
		delincuenteDAO.remove(delincuente);
	}

}
