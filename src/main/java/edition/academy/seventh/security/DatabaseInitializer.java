package edition.academy.seventh.security;

import edition.academy.seventh.connector.ConnectorFactory;
import edition.academy.seventh.connector.ConnectorProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

import static edition.academy.seventh.connector.DatabaseType.*;

/** @author Patryk Kucharski */
@Service
class DatabaseInitializer {

  private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseInitializer.class);
  private AuthenticationService authenticationService;
  private EntityManager entityManager;
  private ConnectorProvider connectorProvider;

  @Autowired
  DatabaseInitializer(
      AuthenticationService authenticationService) {
    connectorProvider = ConnectorFactory.of(POSTGRESQL);
    this.authenticationService = authenticationService;
  }
}
