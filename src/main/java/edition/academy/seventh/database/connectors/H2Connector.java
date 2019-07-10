package edition.academy.seventh.database.connectors;

/** @author Kamil Rojek */
public class H2Connector extends EntityConnector {

  @Override
  void setPersistenceUnitName() {
    super.persistenceUnitName = "myEntityManagerH2";
  }
}
