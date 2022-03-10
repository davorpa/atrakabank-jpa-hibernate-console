package business.model;

import java.io.Serializable;

public interface IEntity<PK extends Serializable> extends Serializable {

	/**
	 * Obtiene el valor de la clave artificial que identifica unequívocamente
	 * la entidad de persistencia subyacente.
	 *
	 * @return nunca {@code null}
	 */
	PK getId();

	/**
	 * Obtiene una representación textual de la entidad de persistencia subyacente.
	 * <p>
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	String toString();

	/**
	 * Dos instancias que representen una entidad de persistencia son iguales
	 * si implementan {@link #hashCode()} e {@link #equals(Object)} sobre alguna
	 * de las claves de la misma, ya sean artificiales o naturales.
	 * <p>
	 * {@inheritDoc}
	 * 
	 * @return hashcode
	 * @see #getId()
	 */
	@Override
	int hashCode();

	/**
	 * Dos instancias que representen una entidad de persistencia son iguales
	 * si implementan {@link #hashCode()} e {@link #equals(Object)} sobre alguna
	 * de las claves de la misma, ya sean artificiales o naturales.
	 * <p>
	 * {@inheritDoc}
	 * 
	 * @param obj la instancia con la que comparar
	 * @return {@code true} si son equivalentes
	 * @see #getId()
	 */
	@Override
	boolean equals(final Object obj);

}
