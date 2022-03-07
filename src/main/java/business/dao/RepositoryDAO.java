package business.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import business.model.IEntity;
import lombok.NonNull;

/**
 * Contrato de marca para todos los DAO (Data Access Objects) con las
 * operaciones CRUD.
 *
 * @param <T>  el tipo de dato de la entidad al que accede.
 * @param <PK> como tipo de dato que es su Primary Key o identificador.
 */
public interface RepositoryDAO<T extends IEntity<PK>, PK extends Serializable> {

	/**
	 * Persiste una nueva entidad no disponible aún en el motor de persistencia y
	 * por consiguiente también en base de datos.
	 *
	 * @param entity entidad a persistir
	 * @return la entidad en estado persistente
	 */
	T create(@NonNull T entity);

	/**
	 * Obtiene todas las entidades.
	 *
	 * @return una lista con todas las entidades. Nunca {@code null}.
	 */
	@NonNull
	List<T> findAll();

	/**
	 * Cuenta todas las entidades.
	 *
	 * @return un valor positivo, cero si no hay registros aún.
	 */
	long countAll();

	/**
	 * Recupera una entidad a partir de su identificador (también conocido como
	 * Primary Key).
	 *
	 * @param id el identificador por el que consultar.
	 * @return Nunca {@code null}. {@link Optional#empty()} si no se encuentra.
	 */
	@NonNull
	Optional<T> findOne(@NonNull PK id);

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
	 * Persiste una coleccion de entidades independientemente de si están o no
	 * en el motor de persistencia y por consiguiente en base de datos.
	 *
	 * @param entities entidades a persistir o mergear
	 * @return la entidad persistida
	 */
	void persist(@NonNull Collection<T> entities);

	/**
	 * Borra una entidad de base de datos que debe estar ya cargada en
	 * el motor de persistencia.
	 *
	 * @param entity entidad a eliminar
	 */
	void delete(@NonNull T entity);

	/**
	 * Borra una entidad de base de datos a partir de su identificador.
	 *
	 * @param id su identificador (también conocido como Primary Key).
	 */
	void delete(@NonNull PK id);

	/**
	 * Borra una coleccion de entidades independientemente de si están o no
	 * en el motor de persistencia.
	 *
	 * @param entities entidades a eliminar
	 */
	void delete(@NonNull Collection<T> entities);

}
