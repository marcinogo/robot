package edition.academy.seventh.connectors;

import org.hibernate.cfg.Environment;

import java.util.Properties;

/**
 * @author Kamil Rojek
 */
public class PostgresConnector extends EntityConnector {

    @Override
    void setPersistenceUnitName() {
        super.persistenceUnitName = "myEntityManagerPostgreSQL";
    }
}
