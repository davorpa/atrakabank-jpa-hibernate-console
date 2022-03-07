package business.dao;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import business.model.BandaOrganizada;
import business.model.Delincuente;

public class BandaOrganizadaDAO extends AbstractRepositoryDAO<BandaOrganizada, Long> {

	/**
	 * Obtiene el detalle de la entidad a partir de su clave natural.
	 *
	 * @param codigo identificador alfanumérico unequívoco de la entidad.
	 * @return nunca {@code null}, {@code Optional#empty()} si la consulta no devuelve resultados.
	 * @throws NonUniqueResultException si la consulta devuelve más de un resultado
	 * @see TypedQuery#getSingleResult()
	 */
	public Optional<BandaOrganizada> findOneByCodigo(final String codigo) {
		Objects.requireNonNull(codigo, "`codigo` must be non-null");
		return run(em -> {
			try {
				return Optional.ofNullable(em
						.createNamedQuery("BandaOrganizada.findOneByCodigo", BandaOrganizada.class)
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
		return findOne(bandaId).map(BandaOrganizada::getDelincuentes);
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
		return findOneByCodigo(bandaCodigo).map(BandaOrganizada::getDelincuentes);
	}

}
