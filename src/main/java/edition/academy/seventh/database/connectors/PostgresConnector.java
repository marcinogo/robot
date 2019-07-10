package edition.academy.seventh.database.connectors;

/** @author Kamil Rojek */
public class PostgresConnector extends EntityConnector {

  @Override
  void setPersistenceUnitName() {
    super.persistenceUnitName = "myEntityManagerPostgreSQL";
  }
}
