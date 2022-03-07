package business.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import business.model.Sucursal;
import lombok.NonNull;

public class SucursalDAO extends AbstractRepositoryDAO<Sucursal, Long> {

	public List<Sucursal> findAllByCodigoBanco(final @NonNull String codigo) {
		return run(em -> {
			List<Sucursal> result;
			final TypedQuery<Sucursal> query = em.createNamedQuery("Sucursal.findAllByCodigoBanco", Sucursal.class);
			query.setParameter(1, codigo);
			result = query.getResultList();
			return result;
		});
	}

	public Optional<Sucursal> findOneByCodigo(final @NonNull String codigo) {
		final Sucursal entity = run(em -> {
			Sucursal result = null;
			final TypedQuery<Sucursal> query = em.createNamedQuery("Sucursal.findOneByCodigo", Sucursal.class);
			query.setParameter(1, codigo);
			try {
				result = query.getSingleResult();
			} catch (NoResultException e) {
				// return null as same as findById
			}
			return result;
		});
		return Optional.ofNullable(entity);
	}

}
