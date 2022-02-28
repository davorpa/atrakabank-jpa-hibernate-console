package business.dao;

import java.util.List;

import business.model.Atraco;
import lombok.NonNull;

public class AtracoDAO extends AbstractRepositoryDAO<Atraco> {

	@Override
	public @NonNull List<Atraco> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Atraco findById(final @NonNull Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Atraco insert(final @NonNull Atraco entity) {
		return runInTransaction(em -> {
			em.persist(entity);
			return entity;
		});
	}

	@Override
	public Atraco update(final @NonNull Atraco entity) {
		return runInTransaction(em -> {
			return em.merge(entity);
		});
	}

	@Override
	public void remove(final @NonNull Atraco entity) {
		// TODO Auto-generated method stub

	}

}
