package business.dao;

import java.util.List;

import business.model.Juez;
import lombok.NonNull;

public class JuezDAO extends AbstractRepositoryDAO<Juez> {

	@Override
	public @NonNull List<Juez> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Juez findById(final @NonNull Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Juez insert(final @NonNull Juez entity) {
		return runInTransaction(em -> {
			em.persist(entity);
			return entity;
		});
	}

	@Override
	public @NonNull Juez update(final @NonNull Juez entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(final @NonNull Juez entity) {
		// TODO Auto-generated method stub

	}

}
