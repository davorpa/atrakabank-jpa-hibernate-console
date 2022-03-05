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

public class DelincuenteDAO extends AbstractRepositoryDAO<Delincuente> {

	@Override
	public @NonNull List<Delincuente> findAll() {
		return run(em -> {
			final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			final CriteriaQuery<Delincuente> criteriaQuery =
					criteriaBuilder.createQuery(Delincuente.class);

			final Root<Delincuente> root = criteriaQuery.from(Delincuente.class);
			criteriaQuery.select(root);

			final TypedQuery<Delincuente> query = em.createQuery(criteriaQuery);
			return query.getResultList();
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

	/**
	 * Obtiene el detalle de la entidad a partir de su clave natural.
	 *
	 * @param codigo identificador alfanumérico unequívoco de la entidad.
	 * @return nunca {@code null}, {@code Optional#empty()} si la consulta no devuelve resultados.
	 * @throws NonUniqueResultException si la consulta devuelve más de un resultado
	 * @see TypedQuery#getSingleResult()
	 */
	public Optional<Delincuente> findByCodigo(String codigo) {
		Objects.requireNonNull(codigo, "`codigo` must be non-null");
		return run(em -> {
			try {
				return Optional.ofNullable(em
						.createNamedQuery("Delincuente.findByCodigo", Delincuente.class)
						.setParameter(1, codigo)
						.getSingleResult());
			} catch (NoResultException e) {
				return Optional.empty();
			}
		});
	}

	/**
	 * Obtiene las bandas organizadas a las que está afiliado un determinado delincuente.
	 *
	 * @param delincuenteId - identificador o clave artificial por el que consultar, nunca {@code null}.
	 * @return {@code Optional#empty()} si no se encuentra el delincuente, si no la colección resultante.
	 * @see #findById(Long)
	 * @see Delincuente#getBandasOrganizadas()
	 */
	public Optional<Set<BandaOrganizada>> getBandasOrganizadasOf(Long delincuenteId) {
		Objects.requireNonNull(delincuenteId, "`delincuenteId` must be non-null");
		// TODO: PERFORMANCE. use JQL or CriteriaQuery instead of findById + stream projection. Then, move to BandaOrganizadaDAO
		Delincuente delincuente = findById(delincuenteId);
		return Optional.ofNullable(delincuente).map(Delincuente::getBandasOrganizadas);
	}

	/**
	 * Obtiene las bandas organizadas a las que está afiliado un determinado delincuente.
	 *
	 * @param delincuenteId - identificador o clave artificial por el que consultar, nunca {@code null}.
	 * @return {@code Optional#empty()} si no se encuentra el delincuente, si no la colección resultante.
	 * @see #findByCodigo(String)
	 * @see Delincuente#getBandasOrganizadas()
	 */
	public Optional<Set<BandaOrganizada>> getBandasOrganizadasOf(String delincuenteCodigo) {
		Objects.requireNonNull(delincuenteCodigo, "`delincuenteCodigo` must be non-null");
		// TODO: PERFORMANCE. use JQL or CriteriaQuery instead of findByCode + stream projection. Then, move to BandaOrganizadaDAO
		Delincuente delincuente = findByCodigo(delincuenteCodigo).orElse(null);
		return Optional.ofNullable(delincuente).map(Delincuente::getBandasOrganizadas);
	}

}
