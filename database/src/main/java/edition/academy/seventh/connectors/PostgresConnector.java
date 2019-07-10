package edition.academy.seventh.connectors;

/** @author Kamil Rojek */
public class PostgresConnector extends EntityConnector {

  @Override
  void setPersistenceUnitName() {
    super.persistenceUnitName = "myEntityManagerPostgreSQL";
  }
}
