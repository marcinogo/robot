package edition.academy.seventh.service;

import edition.academy.seventh.database.connector.ConnectorFactory;
import edition.academy.seventh.database.connector.ConnectorProvider;
import edition.academy.seventh.security.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

import static edition.academy.seventh.database.connector.DatabaseType.*;

/** @author Patryk Kucharski */
@Service
public class DatabaseInitializer {

  private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseInitializer.class);
  private AuthenticationService authenticationService;
  private EntityManager entityManager;
  private ConnectorProvider connectorProvider;

  @Autowired
  public DatabaseInitializer(
      AuthenticationService authenticationService) {
    connectorProvider = ConnectorFactory.of(POSTGRESQL);
    this.authenticationService = authenticationService;
  }
}
