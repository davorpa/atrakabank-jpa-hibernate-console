package business.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private static final String PERSISTENCE_UNIT = "persistencia";


    private JPAUtil() {
        super();
    }


    private static volatile EntityManagerFactory entityManagerFactory;

    /**
     * Obtiene la factoria desde la que generar manejadores JPA
     *
     * @return
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        // Use singleton but with thread safe lazy strategy instantiation
        // (volatile + double race sync)
        EntityManagerFactory emf;
        if ((emf = entityManagerFactory) == null) {
            synchronized (JPAUtil.class) {
                if ((emf = entityManagerFactory) == null) {
                    emf = entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
                }
            }
        }
        return emf;
    }

    /**
     * Libera la factoria desde la que generar manejadores JPA de entidades
     */
    public static void closeEntityManagerFactory() {
        try {
            if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
                entityManagerFactory.close();
            }
            entityManagerFactory = null;
        } catch (Exception e) {
            // NE: sillent close
        }
    }

    /**
     * Libera el manejador manejadores JPA pasado por parametro
     *
     * @param entityManager manejador JPA de entidades
     */
    public static void closeEntityManager(final EntityManager entityManager) {
        try {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        } catch (Exception e) {
            // NE: sillent close
        }
    }
}
