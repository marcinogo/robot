package edition.academy.seventh.database.connector;

/**
 * Factory for connector providers.
 *
 * @author Kamil Rojek
 */
public class ConnectorFactory {

  private static final H2Connector H2_CONNECTOR = new H2Connector();
  private static final PostgresConnector POSTGRES_CONNECTOR = new PostgresConnector();
  /**
   * Return concrete implementation of connector provider.
   *
   * @param type {@link DatabaseType}
   * @return {@link ConnectorProvider}
   */
  public static ConnectorProvider of(DatabaseType type) {
    switch (type) {
      case H2:
        return H2_CONNECTOR;
      case POSTGRESQL:
      default:
        return POSTGRES_CONNECTOR;
    }
  }
}
