package edition.academy.seventh.database.connectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Map;

/**
 * Adapter for connection to database using {@link javax.persistence.EntityManager}.
 *
 * @author Kamil Rojek
 */
public abstract class EntityConnector {
  private EntityManagerFactory entityManagerFactory;
  private final String PERSISTENCE_UNIT_NAME;

  EntityConnector(String persistenceUnitName) {
    this.PERSISTENCE_UNIT_NAME = persistenceUnitName;
  }

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

    return entityManagerFactory =
        Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, loadPersistenceSettings());
  }

  abstract Map<String, String> loadPersistenceSettings();
}
