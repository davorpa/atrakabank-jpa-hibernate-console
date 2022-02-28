package business.dao;

import java.util.List;

import business.model.Contrato;
import lombok.NonNull;

public class ContratoDAO extends AbstractRepositoryDAO<Contrato> {

	@Override
	public @NonNull List<Contrato> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Contrato findById(final @NonNull Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Contrato insert(final @NonNull Contrato entity) {
		return runInTransaction(em -> {
			em.persist(entity);
			return entity;
		});
	}

	@Override
	public @NonNull Contrato update(final @NonNull Contrato entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(final @NonNull Contrato entity) {
		// TODO Auto-generated method stub

	}

}
