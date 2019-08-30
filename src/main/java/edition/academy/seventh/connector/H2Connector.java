package edition.academy.seventh.connector;

import java.util.HashMap;
import java.util.Map;

/** @author Kamil Rojek */
class H2Connector extends EntityConnector {
  H2Connector() {
    super("H2Unit", false);
  }

  @Override
  final Map<String, String> loadPersistenceSettings() {
    Map<String, String> settings = new HashMap<>();
    settings.put("javax.persistence.jdbc.driver", "org.h2.Driver");
    settings.put("javax.persistence.jdbc.user", "sa");
    settings.put("javax.persistence.jdbc.password", "sa");
    settings.put("javax.persistence.jdbc.url", "jdbc:h2:tcp://localhost:9092/~/test");
    settings.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
    settings.put("hibernate.show_sql", "true");
    settings.put("hibernate.hbm2ddl.auto", "create-drop");

    return settings;
  }
}
