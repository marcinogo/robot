package edition.academy.seventh.database.connector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/** @author Kamil Rojek */
public interface ConnectorProvider {
  /**
   * Provides entity manager with specific configuration.
   *
   * @return {@link javax.persistence.EntityManager}
   */
  EntityManager getEntityManager();
  EntityManagerFactory getEntityManagerFactory();
}
