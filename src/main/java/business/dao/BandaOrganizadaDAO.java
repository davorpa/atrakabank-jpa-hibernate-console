package business.dao;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import business.model.BandaOrganizada;
import business.model.Delincuente;
import lombok.NonNull;

public class BandaOrganizadaDAO extends AbstractRepositoryDAO<BandaOrganizada> {

	@Override
	public @NonNull List<BandaOrganizada> findAll() {
		return run(em -> {
			final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			final CriteriaQuery<BandaOrganizada> criteriaQuery =
					criteriaBuilder.createQuery(BandaOrganizada.class);

			final Root<BandaOrganizada> root = criteriaQuery.from(BandaOrganizada.class);
			criteriaQuery.select(root);

			final TypedQuery<BandaOrganizada> query = em.createQuery(criteriaQuery);
			return query.getResultList();
		});
	}

	@Override
	public BandaOrganizada findById(final @NonNull Long id) {
		return run(em -> {
			return em.find(BandaOrganizada.class, id);
		});
	}

	@Override
	public BandaOrganizada insert(final @NonNull BandaOrganizada entity) {
		return runInTransaction(em -> {
			em.persist(entity);
			return entity;
		});
	}

	@Override
	public @NonNull BandaOrganizada update(final @NonNull BandaOrganizada entity) {
		return runInTransaction(em -> {
			return em.merge(entity);
		});
	}

	@Override
	public void remove(final @NonNull BandaOrganizada entity) {
		runInTransaction(em -> {
			em.remove(entity);
		});
	}



	/**
	 * Obtiene el detalle de la entidad a partir de su clave natural.
	 *
	 * @param codigo identificador alfanumérico unequívoco de la entidad.
	 * @return nunca {@code null}, {@code Optional#empty()} si la consulta no devuelve resultados.
	 * @throws NonUniqueResultException si la consulta devuelve más de un resultado
	 * @see TypedQuery#getSingleResult()
	 */
	public Optional<BandaOrganizada> findByCodigo(final String codigo) {
		Objects.requireNonNull(codigo, "`codigo` must be non-null");
		return run(em -> {
			try {
				return Optional.ofNullable(em
						.createNamedQuery("BandaOrganizada.findByCodigo", BandaOrganizada.class)
						.setParameter(1, codigo)
						.getSingleResult());
			} catch (NoResultException e) {
				return Optional.empty();
			}
		});
	}

	/**
	 * Obtiene los delincuentes que tiene como miembros una determinada banda organizada.
	 *
	 * @param bandaId - identificador o clave artificial por el que consultar, nunca {@code null}.
	 * @return {@code Optional#empty} si no se encuentra la banda organizada, si no la colección resultante.
	 * @see #findById(Long)
	 * @see BandaOrganizada#getDelincuentes()
	 */
	public Optional<Set<Delincuente>> getMiembrosOf(final Long bandaId) {
		Objects.requireNonNull(bandaId, "`bandaId` must be non-null");
		// TODO: PERFORMANCE. use JQL or CriteriaQuery instead of findById + stream projection. Then, move to DelincuenteDAO
		BandaOrganizada banda = findById(bandaId);
		return Optional.ofNullable(banda).map(BandaOrganizada::getDelincuentes);
	}

	/**
	 * Obtiene los delincuentes que tiene como miembros una determinada banda organizada.
	 *
	 * @param delincuenteId - identificador o clave artificial por el que consultar, nunca {@code null}..
	 * @return {@code Optional#empty} si no se encuentra la banda organizada, si no la colección resultante.
	 * @see #findByCodigo(String))
	 * @see BandaOrganizada#getDelincuentes()
	 */
	public Optional<Set<Delincuente>> getMiembrosOf(final String bandaCodigo) {
		Objects.requireNonNull(bandaCodigo, "`bandaCodigo` must be non-null");
		// TODO: PERFORMANCE. use JQL or CriteriaQuery instead of findByCode + stream projection. Then, move to DelincuenteDAO
		BandaOrganizada banda = findByCodigo(bandaCodigo).orElse(null);
		return Optional.ofNullable(banda).map(BandaOrganizada::getDelincuentes);
	}

}
