package business.dao;

import java.io.Serializable;
import java.util.List;

import lombok.NonNull;

/**
 * Contrato de marca para todos los DAO (Data Access Objects) con las
 * operaciones CRUD.
 *
 * @param <T>  el tipo de dato de la entidad al que accede
 */
public interface RepositoryDAO<T extends Serializable> {

	/**
	 * Obtiene todas las entidades.
	 *
	 * @return una lista con todas las entidades. Nunca {@code null}.
	 */
	@NonNull
	List<T> findAll();

	/**
	 * Recupera una entidad a partir de su identificador (también conocido como
	 * Primary Key).
	 *
	 * @param id el identificador por el que consultar.
	 * @return {@code null} si no se encuentra.
	 */
	T findById(@NonNull Long id);

	/**
	 * Persiste una nueva entidad no disponible aún en el motor de persistencia y
	 * por consiguiente también en base de datos.
	 *
	 * @param entity entidad a persistir
	 * @return la entidad en estado persistente
	 */
	T insert(@NonNull T entity);

	/**
	 * Actualiza una entidad disponible en el motor de persistencia y por tanto en
	 * base de datos.
	 *
	 * @param entity entidad a actualizar
	 * @return la entidad en estado persistente
	 */
	@NonNull
	T update(@NonNull T entity);

	/**
	 * Borra una entidad de base de datos.
	 *
	 * @param entity entidad a eliminar
	 */
	void remove(@NonNull T entity);

}
