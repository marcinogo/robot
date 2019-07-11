package edition.academy.seventh.database.connectors;

import javax.persistence.EntityManager;

/** @author Kamil Rojek */
public interface ConnectorProvider {
  /**
   * Provides entity manager with specific configuration.
   *
   * @return {@link javax.persistence.EntityManager}
   */
  EntityManager getEntityManager();
}
