package edition.academy.seventh.connectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Kamil Rojek
 */
public abstract class EntityConnector {
    private EntityManagerFactory entityManagerFactory;
    protected String persistenceUnitName;

    /**
     * Opens session from SessionFactory.
     *
     * @return Session object.
     */
    public EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    private EntityManagerFactory getEntityManagerFactory() {

        if (entityManagerFactory != null) {
            return entityManagerFactory;
        }
        setPersistenceUnitName();

        return entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
    }

    abstract void setPersistenceUnitName();
}
