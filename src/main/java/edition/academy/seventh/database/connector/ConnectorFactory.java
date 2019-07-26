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
   * @param type {@link DatabaseType}
   * @return {@link ConnectorProvider}
   */
  public static ConnectorProvider of(DatabaseType type) {
    switch (type) {
      case H2:
        return new H2Connector();
      case POSTGRESQL:
      default:
        return new PostgresConnector();
    }
  }
}
