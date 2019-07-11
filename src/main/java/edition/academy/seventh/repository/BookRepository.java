package edition.academy.seventh.repository;

import edition.academy.seventh.database.connector.ConnectorFactory;
import edition.academy.seventh.database.connector.ConnectorProvider;
import edition.academy.seventh.database.model.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

import static edition.academy.seventh.database.connector.ConnectorFactory.DatabaseTypes.H2;

/**
 * Repository that persists book entities in database.
 *
 * @author Agnieszka Trzewik
 */
@Repository
public class BookRepository {
  private EntityManager entityManager;
  private ConnectorProvider connectorProvider;

  public BookRepository() {
    connectorProvider = ConnectorFactory.of(H2);
  }

  public void addBooksToDataBase(List<Book> books) {
    entityManager = connectorProvider.getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    books.forEach(this::addBookToDataBase);
    transaction.commit();
    entityManager.close();
  }

  private void addBookToDataBase(Book book) {
    entityManager.persist(book);
  }
}
