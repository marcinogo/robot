package edition.academy.seventh.database.connector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Map;

/**
 * Adapter for connection to database using {@link javax.persistence.EntityManager}.
 *
 * @author Kamil Rojek
 */
abstract class EntityConnector implements ConnectorProvider {
  private final String persistenceUnitName;
  private EntityManagerFactory entityManagerFactory;

  EntityConnector(final String persistenceUnitName) {
    this.persistenceUnitName = persistenceUnitName;
  }

  /**
   * Return entity manager based on persistence unit name.
   *
   * @return Entity manager factory.
   */
  @Override
  public final EntityManager getEntityManager() {
    return getEntityManagerFactory().createEntityManager();
  }

  private EntityManagerFactory getEntityManagerFactory() {

    if (entityManagerFactory != null) {
      return entityManagerFactory;
    }

    return entityManagerFactory =
        Persistence.createEntityManagerFactory(persistenceUnitName, loadPersistenceSettings());
  }

  /**
   * Loads programmatically child database properties.
   *
   * @return {@code Map<String, String>} database properties.
   */
  abstract Map<String, String> loadPersistenceSettings();
}
