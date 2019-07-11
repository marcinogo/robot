package edition.academy.seventh.repositories;

import edition.academy.seventh.database.connectors.ConnectorFactory;
import edition.academy.seventh.database.connectors.ConnectorProvider;
import edition.academy.seventh.database.models.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

import static edition.academy.seventh.database.connectors.ConnectorFactory.DatabaseTypes.H2;

/**
 * Repository that persists book entities in database.
 *
 * @author Agnieszka Trzewik
 */
@Repository
public class BookRepository {
  private EntityManager entityManager;

  public BookRepository() {
    ConnectorProvider connectorProvider = ConnectorFactory.of(H2);
    entityManager = connectorProvider.getEntityManager();
  }

  public void addBooksToDataBase(List<Book> books) {
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
