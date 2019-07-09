package edition.academy.seventh;

import edition.academy.seventh.connectors.EntityConnector;
import edition.academy.seventh.connectors.H2Connector;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * @author Kamil Rojek
 */
class Main {
  public static void main(String[] args) {
    EntityConnector connector = new H2Connector();
    EntityManager entityManager = connector.getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
  }
}
