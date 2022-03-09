package business.model;

import java.io.Serializable;

public interface IEntity<PK extends Serializable> extends Serializable {

	/**
	 * Obtiene el valor de la clave artificial que identifica unequívocamente
	 * la entidad de persistencia subyacente.
	 *
	 * @return nunca {@code}
	 */
	PK getId();

	@Override
	String toString();

	@Override
	int hashCode();

	@Override
	boolean equals(Object obj);

}
