package business.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import business.model.Banco;
import lombok.NonNull;

public class BancoDAO extends AbstractRepositoryDAO<Banco> {

	@Override
	public @NonNull List<Banco> findAll() {
		return run(em -> {
			final List<Banco> result;
			Query query = em.createQuery("SELECT b FROM Banco b");
			result = query.getResultList();
			return result;
		});
	}

	@Override
	public Banco findById(final @NonNull Long id) {
		return run(em -> {
			return em.find(Banco.class, id);
		});
	}

	@Override
	public Banco insert(final @NonNull Banco entity) {
		return runInTransaction(em -> {
			em.persist(entity);
			return entity;
		});
	}

	@Override
	public @NonNull Banco update(final @NonNull Banco entity) {
		return runInTransaction(em -> {
			return em.merge(entity);
		});
	}

	@Override
	public void remove(final @NonNull Banco entity) {
		runInTransaction(em -> {
			em.remove(entity);
		});
	}


	public Banco findByCodigo(final @NonNull String codigo) {
		return run(em -> {
			Banco result = null;
			final TypedQuery<Banco> query = em.createNamedQuery("Banco.findByCodigo", Banco.class);
			query.setParameter(1, codigo);
			try {
				result = query.getSingleResult();
			} catch (NoResultException e) {
				// return null as same as findById
			}
			return result;
		});
	}

}
