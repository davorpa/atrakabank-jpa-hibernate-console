package business.dao;

import java.util.List;

import javax.persistence.Query;

import business.model.Delincuente;
import lombok.NonNull;

public class DelincuenteDAO extends AbstractRepositoryDAO<Delincuente> {

	@Override
	public @NonNull List<Delincuente> findAll() {
		return run(em -> {
			final List<Delincuente> result;
			Query query = em.createQuery("SELECT d FROM Delincuente d");
			result = query.getResultList();
			return result;
		});
	}

	@Override
	public Delincuente findById(final @NonNull Long id) {
		return run(em -> {
			return em.find(Delincuente.class, id);
		});
	}

	@Override
	public Delincuente insert(final @NonNull Delincuente entity) {
		return runInTransaction(em -> {
			em.persist(entity);
			return entity;
		});
	}

	@Override
	public @NonNull Delincuente update(final @NonNull Delincuente entity) {
		return runInTransaction(em -> {
			return em.merge(entity);
		});
	}

	@Override
	public void remove(final @NonNull Delincuente entity) {
		runInTransaction(em -> {
			em.remove(entity);
		});
	}

}
