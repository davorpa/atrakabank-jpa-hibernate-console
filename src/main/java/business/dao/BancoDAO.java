package business.dao;

import java.util.Optional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import business.model.Banco;
import lombok.NonNull;

public class BancoDAO extends AbstractRepositoryDAO<Banco, Long> {

	public Optional<Banco> findOneByCodigo(final @NonNull String codigo) {
		final Banco entity = run(em -> {
			Banco result = null;
			final TypedQuery<Banco> query = em.createNamedQuery("Banco.findOneByCodigo", Banco.class);
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
