package business.dao;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import business.model.Empleado;
import business.model.tipos.CargoDirectivo;
import lombok.NonNull;

public class EmpleadoDAO extends AbstractRepositoryDAO<Empleado, Long> {

	/**
	 * Obtiene el detalle de la entidad a partir de su clave natural.
	 *
	 * @param dni documento nacional de identidad.
	 * @return nunca {@code null}, {@code Optional#empty()} si la consulta no devuelve resultados.
	 * @throws NonUniqueResultException si la consulta devuelve más de un resultado
	 * @see TypedQuery#getSingleResult()
	 */
	@NonNull
	public Optional<Empleado> findByDNI(final String dni) {
		Objects.requireNonNull(dni, "`dni` must be non-null");
		final Function<EntityManager, Empleado> retriever = em -> {
			Empleado result = null;
			final TypedQuery<Empleado> query = em.createNamedQuery("Empleado.findByDNI", Empleado.class);
			query.setParameter(1, dni);
			try {
				result = query.getSingleResult();
			} catch (NoResultException e) {
				// return null as same as findById
			}
			return result;
		};
		return Optional.ofNullable(run(retriever));
	}

	/**
	 * Obtiene los empleados que tiene en plantilla un determinado banco.
	 *
	 * @param codigo código alfanumerico que identifica unequívocamente al banco.
	 * @return nunca {@code null}.
	 * @see TypedQuery#getResultList()
	 */
	@NonNull
	public List<Empleado> findAllByBancoCodigo(final String codigo) {
		Objects.requireNonNull(codigo, "`codigo` must be non-null");
		final Function<EntityManager, List<Empleado>> retriever =
				em -> em.createNamedQuery("Empleado.findByBancoCodigo", Empleado.class)
					.setParameter(1, codigo)
					.getResultList();
		return run(retriever);
	}

	@NonNull
	public List<Empleado> findAllByCargo(final CargoDirectivo cargo) {
		Objects.requireNonNull(cargo, "`cargo` must be non-null");
		final Function<EntityManager, List<Empleado>> retriever =
				em -> em.createNamedQuery("Empleado.findAllByCargo", Empleado.class)
					.setParameter(1, cargo)
					.getResultList();
		return run(retriever);
	}
}
