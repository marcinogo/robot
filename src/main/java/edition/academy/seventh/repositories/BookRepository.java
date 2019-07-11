package edition.academy.seventh.repositories;

import edition.academy.seventh.database.connectors.EntityConnector;
import edition.academy.seventh.database.connectors.H2Connector;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import edition.academy.seventh.database.models.Book;
import java.util.List;

/**
 * Repository that persists book entities in database.
 *
 * @author Agnieszka Trzewik
 */
@Repository
public class BookRepository {
  private EntityManager entityManager;

  public BookRepository() {
    EntityConnector connector = new H2Connector();
    entityManager = connector.getEntityManager();
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
