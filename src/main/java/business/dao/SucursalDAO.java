package business.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import business.model.Sucursal;
import lombok.NonNull;

public class SucursalDAO extends AbstractRepositoryDAO<Sucursal> {

	@Override
	public List<Sucursal> findAll() {
		return run(em -> {
			final List<Sucursal> result;
			Query query = em.createQuery("SELECT s FROM Sucursal s");
			result = query.getResultList();
			return result;
		});
	}

	@Override
	public Sucursal findById(final @NonNull Long id) {
		return run(em -> {
			return em.find(Sucursal.class, id);
		});
	}

	@Override
	public Sucursal insert(final @NonNull Sucursal entity) {
		return runInTransaction(em -> {
			em.persist(entity);
			return entity;
		});
	}

	@Override
	public @NonNull Sucursal update(final @NonNull Sucursal entity) {
		return runInTransaction(em -> {
			return em.merge(entity);
		});
	}

	@Override
	public void remove(final @NonNull Sucursal entity) {
		runInTransaction(em -> {
			em.remove(entity);
		});
	}

	public List<Sucursal> findByCodigoBanco(final @NonNull String codigo) {
		return run(em -> {
			List<Sucursal> result;
			final TypedQuery<Sucursal> query = em.createNamedQuery("Sucursal.findByCodigoBanco", Sucursal.class);
			query.setParameter(1, codigo);
			result = query.getResultList();
			return result;
		});
	}

	public Sucursal findByCodigo(final @NonNull String codigo) {
		return run(em -> {
			Sucursal result = null;
			final TypedQuery<Sucursal> query = em.createNamedQuery("Sucursal.findByCodigo", Sucursal.class);
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
