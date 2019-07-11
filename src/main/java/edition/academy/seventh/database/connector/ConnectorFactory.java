package edition.academy.seventh.database.connector;

/**
 * Factory for connector providers.
 *
 * @author Kamil Rojek
 */
public class ConnectorFactory {

  /**
   * Return concrete implementation of connector provider.
   *
   * @param type {@link DatabaseTypes}
   * @return {@link ConnectorProvider}
   */
  public static ConnectorProvider of(DatabaseTypes type) {
    switch (type) {
      case H2:
        return new H2Connector();
      case POSTGRESQL:
      default:
        return new PostgresConnector();
    }
  }

  /** Available types for database's connector. */
  public enum DatabaseTypes {
    H2,
    POSTGRESQL
  }
}
