package business.service;

import java.util.List;
import java.util.Optional;

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

	/**
	 * Obtiene el detalle de un delincuente a partir de su clave natural.
	 *
	 * @param codigo identificador alfanumérico unequívoco.
	 * @return nunca {@code null}, {@code Optional#empty()} si no hay resultados
	 */
	public Optional<Delincuente> findByCodigo(final String codigo) {
		return delincuenteDAO.findByCodigo(codigo);
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
