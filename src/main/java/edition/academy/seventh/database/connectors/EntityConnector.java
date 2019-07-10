package edition.academy.seventh.database.connectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Adapter for connection to database using {@link javax.persistence.EntityManager}.
 *
 * @author Kamil Rojek
 */
public abstract class EntityConnector {
  private EntityManagerFactory entityManagerFactory;
  protected String persistenceUnitName;

  /**
   * Return entity manager based on persistence unit name.
   *
   * @return Entity manager factory.
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
