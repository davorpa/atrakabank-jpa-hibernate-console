package business.dao;

import java.util.List;

import business.model.Vigilante;
import lombok.NonNull;

public class VigilanteDAO extends AbstractRepositoryDAO<Vigilante> {

	@Override
	public @NonNull List<Vigilante> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vigilante findById(final @NonNull Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vigilante insert(final @NonNull Vigilante entity) {
		return runInTransaction(em -> {
			em.persist(entity);
			return entity;
		});
	}

	@Override
	public @NonNull Vigilante update(final @NonNull Vigilante entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(final @NonNull Vigilante entity) {
		// TODO Auto-generated method stub

	}

}
