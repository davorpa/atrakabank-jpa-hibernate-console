package business.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Helper de soporte para acceso de datos (DAO) en repositorios basados en JPA.
 *
 * @param <T>  el tipo de dato de la entidad al que accede
 */
@NoArgsConstructor
public abstract class AbstractRepositoryDAO<T extends Serializable> implements RepositoryDAO<T>
{

	@SuppressWarnings("unchecked")
	protected Class<T> entityClazz() {
		ParameterizedType clazz = (ParameterizedType) getClass().getGenericSuperclass();
		// 0: <T> value type
		return (Class<T>) clazz.getActualTypeArguments()[0];
	}

	protected String entityClazzName() {
		return entityClazz().getName();
	}


	private EntityManager entityManager;

	EntityManager getEntityManager() {
		if (entityManager == null || !entityManager.isOpen()) {
			entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		}
		return entityManager;
	}


	// ===========================================
	//
	// Helpers de ejecución
	//

	protected <R> R run(final @NonNull Function<EntityManager, R> function) {
		EntityManager em = getEntityManager();
		try {
			return function.apply(em);
		} catch (PersistenceException pex) {
			em.getTransaction().rollback();
			throw pex;
		} finally {
			if (Boolean.getBoolean("persistence.dao.close.em.per.operation")) {
				JPAUtil.closeEntityManager(em);
			}
		}
	}

	protected void run(final @NonNull Consumer<EntityManager> function) {
		run(em -> {
			function.accept(em);
			return null;
		});
	}

	protected <R> R runInTransaction(final @NonNull Function<EntityManager, R> function) {
		return run(em -> {
			if (!em.isJoinedToTransaction()) {
				em.getTransaction().begin();
			}
			final R result = function.apply(em);
			em.getTransaction().commit();
			return result;
		});
	}

	protected void runInTransaction(final @NonNull Consumer<EntityManager> function) {
		runInTransaction(em -> {
			function.accept(em);
			return null;
		});
	}

}
