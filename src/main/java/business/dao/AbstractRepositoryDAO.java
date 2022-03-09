package business.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import business.model.IEntity;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Helper de soporte para acceso de datos (DAO) en repositorios basados en JPA.
 *
 * @param <T>  el tipo de dato de la entidad al que accede.
 * @param <PK> como tipo de dato que es su Primary Key o identificador.
 */
@NoArgsConstructor
public abstract class AbstractRepositoryDAO<T extends IEntity<PK>, PK extends Serializable>
		implements RepositoryDAO<T, PK>
{
	private Class<T> entityClass;

	@SuppressWarnings("unchecked")
	protected Class<T> getEntityClass() {
		if (entityClass == null) {
			//only works if one extends AbstractRepositoryDAO, we will take care of it with CDI
			ParameterizedType clazz = (ParameterizedType) getClass().getGenericSuperclass();
			// 0: <T> value type
			// 1: <PK> value type
			entityClass = (Class<T>) clazz.getActualTypeArguments()[0];
		}
		return entityClass;
	}

	private Class<PK> primaryKeyClass;

	@SuppressWarnings("unchecked")
	protected Class<PK> getPrimaryKeyClass() {
		if (primaryKeyClass == null) {
			//only works if one extends AbstractRepositoryDAO, we will take care of it with CDI
			ParameterizedType clazz = (ParameterizedType) getClass().getGenericSuperclass();
			// 0: <T> value type
			// 1: <PK> value type
			primaryKeyClass = (Class<PK>) clazz.getActualTypeArguments()[1];
		}
		return primaryKeyClass;
	}

	protected String entityClassName() {
		String name = getEntityClass().getTypeName();
		// unproxied name
		int index = name.indexOf('$');
		return index > 0 ? name.substring(0, index) : name;
	}


	private EntityManager entityManager;

	EntityManager getEntityManager() {
		if (entityManager == null || !entityManager.isOpen()) {
			// on initialize or on closed
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
		EntityTransaction tx = null;
		try {
			return function.apply(em);
		}
		catch (RuntimeException cause) {
			// isolation: avoid double rollback race when commit fails
			if (cause instanceof RollbackException) {
				throw cause;
			}

			// isolation: avoid process on read-only / non-transactional operations
			try {
				if (em.isJoinedToTransaction()) {
					tx = em.getTransaction();
				}
				if (tx != null && tx.isActive()) {
					tx.rollback();
				}
			} catch (IllegalStateException | PersistenceException ignored) {
				// avoid mask real catched exception
			}

			throw cause;
		} finally {
			if (Boolean.getBoolean("persistence.close-entity-manager-per-execution")) {
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
			final EntityTransaction tx = em.getTransaction();
			// avoid reentrance: it raises an exception
			if (!em.isJoinedToTransaction() || !tx.isActive()) {
				tx.begin();
			}

			final R result = function.apply(em);

			// avoid reentrance: it raises an exception
			if (em.isJoinedToTransaction() && tx.isActive()) {
				if ( !tx.getRollbackOnly() ) {
					tx.commit();
				} else {
					tx.rollback();
				}
			}
			return result;
		});
	}

	protected void runInTransaction(final @NonNull Consumer<EntityManager> function) {
		runInTransaction(em -> {
			function.accept(em);
			return null;
		});
	}


	// ===========================================
	//
	// Operaciones CRUD
	//

	@Override
	public @NonNull T create(final @NonNull T entity) {
		final Consumer<EntityManager> persister =
				em -> em.persist(entity);
		runInTransaction(persister);
		return entity;
	}

	@Override
	public @NonNull List<T> findAll() {
//		return findAllUsingJQL();
		return findAllUsingCriterias();
	}

	@SuppressWarnings("unchecked")
	protected @NonNull List<T> findAllUsingJQL() {
		final String type = entityClassName();
		final String jql = String.format("SELECT entity FROM %s entity", type);
		final Function<EntityManager, List<T>> retriever =
				em -> em.createQuery(jql).getResultList();
		return run(retriever);
	}

	protected @NonNull List<T> findAllUsingCriterias() {
		final Class<T> type = getEntityClass();
		final Function<EntityManager, List<T>> retriever = em -> {
			final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

			final CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
			criteriaQuery.select(criteriaQuery.from(type));

			return em.createQuery(criteriaQuery).getResultList();
		};
		return run(retriever);
	}

	@Override
	public long countAll() {
		final Class<T> type = getEntityClass();
		final Function<EntityManager, Long> retriever = em -> {
			final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

			final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(long.class);
			criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(type)));

			return em.createQuery(criteriaQuery)
					.getSingleResult()
					.longValue();
		};
		return run(retriever);
	}

	@Override
	public Optional<T> findOne(@NonNull final PK id) {
		final Class<T> type = getEntityClass();
		final Function<EntityManager, T> retriever =
				em -> em.find(type, id);
		final T entity = run(retriever);
		return Optional.ofNullable(entity);
	}

	@Override
	public @NonNull T update(final @NonNull T entity) {
		final Function<EntityManager, T> persister =
				em -> em.merge(entity);
		return runInTransaction(persister);
	}

	@Override
	public void persist(final @NonNull Collection<T> entities) {
		// create or update each one
		final Consumer<EntityManager> persister =
				em -> entities.forEach(em::merge);
		runInTransaction(persister);
	}

	@Override
	public void delete(final @NonNull T entity) {
		final Consumer<EntityManager> persister =
				em -> em.remove(entity);
		runInTransaction(persister);
	}

	@Override
	public void delete(final @NonNull PK id) {
		final Class<T> type = getEntityClass();
		final Consumer<EntityManager> persister = em -> Optional.
				// ensure managed instance
				ofNullable(
//						em.getReference(type, id)
						em.find(type, id)
				)
				// if exists, detach it
				.ifPresent(em::remove);
		runInTransaction(persister);
	}

	@Override
	public void delete(final @NonNull Collection<T> entities) {
		final Class<T> type = getEntityClass();
		final Consumer<EntityManager> persister = em -> entities.stream()
				// ensure managed instance
				.map(T::getId)
//				.map(id -> em.getReference(type, id))
				.map(id -> em.find(type, id))
				.filter(Objects::nonNull)
				// if found, detach each one
				.forEach(em::remove);
		runInTransaction(persister);
	}

}
