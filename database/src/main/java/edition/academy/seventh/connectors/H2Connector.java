package edition.academy.seventh.connectors;

/** @author Kamil Rojek */
public class H2Connector extends EntityConnector {

  @Override
  void setPersistenceUnitName() {
    super.persistenceUnitName = "myEntityManagerH2";
  }
}
