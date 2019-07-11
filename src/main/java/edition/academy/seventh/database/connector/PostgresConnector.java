package edition.academy.seventh.database.connector;

import java.util.HashMap;
import java.util.Map;

/** @author Kamil Rojek */
class PostgresConnector extends EntityConnector {
  private Map<String, String> credentials = new HashMap<>();

  PostgresConnector() {
    super("PostgreSQLUnit");
    credentials = System.getenv();
  }

  @Override
  final Map<String, String> loadPersistenceSettings() {
    Map<String, String> settings = new HashMap<>();
    settings.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");
    settings.put("javax.persistence.jdbc.user", credentials.get("DB_USER"));
    settings.put("javax.persistence.jdbc.password", credentials.get("DB_PASSWORD"));
    settings.put("javax.persistence.jdbc.url", credentials.get("DB_URL"));
    settings.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
    settings.put("hibernate.show_sql", "true");
    settings.put("hibernate.hbm2ddl.auto", "create");
    return settings;
  }
}
