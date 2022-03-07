package business.dao;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import business.model.BandaOrganizada;
import business.model.Delincuente;

public class DelincuenteDAO extends AbstractRepositoryDAO<Delincuente, Long> {

	/**
	 * Obtiene el detalle de la entidad a partir de su clave natural.
	 *
	 * @param codigo identificador alfanumérico unequívoco de la entidad.
	 * @return nunca {@code null}, {@code Optional#empty()} si la consulta no devuelve resultados.
	 * @throws NonUniqueResultException si la consulta devuelve más de un resultado
	 * @see TypedQuery#getSingleResult()
	 */
	public Optional<Delincuente> findOneByCodigo(String codigo) {
		Objects.requireNonNull(codigo, "`codigo` must be non-null");
		return run(em -> {
			try {
				return Optional.ofNullable(em
						.createNamedQuery("Delincuente.findOneByCodigo", Delincuente.class)
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
		return findOne(delincuenteId).map(Delincuente::getBandasOrganizadas);
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
		return findOneByCodigo(delincuenteCodigo).map(Delincuente::getBandasOrganizadas);
	}

}
