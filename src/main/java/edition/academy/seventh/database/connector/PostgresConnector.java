package edition.academy.seventh.database.connector;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

/** @author Kamil Rojek */
class PostgresConnector extends EntityConnector {
  private Map<String, String> credentials;

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
    settings.put("hibernate.hbm2ddl.auto", "update");
    settings.put("connection.provider_class", "org.hibernate.connection.C3P0ConnectionProvider");
    settings.put("hibernate.c3p0.acquire_increment", "1");
    settings.put("hibernate.c3p0.idle_test_period", "60");
    settings.put("hibernate.c3p0.min_size", "1");
    settings.put("hibernate.c3p0.max_size", "2");
    settings.put("hibernate.c3p0.max_statements", "50");
    settings.put("hibernate.c3p0.timeout", "0");
    settings.put("hibernate.c3p0.acquireRetryAttempts", "1");
    settings.put("hibernate.c3p0.acquireRetryDelay", "250");

    return settings;
  }

  @Override
  public EntityManagerFactory getEntityManagerFactory() {
    return super.getEntityManagerFactory();
  }
}
