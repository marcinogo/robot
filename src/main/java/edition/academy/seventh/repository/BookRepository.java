package edition.academy.seventh.repository;

import static edition.academy.seventh.database.connector.ConnectorFactory.DatabaseTypes.H2;
import static edition.academy.seventh.database.connector.ConnectorFactory.DatabaseTypes.POSTGRESQL;

import edition.academy.seventh.database.connector.ConnectorFactory;
import edition.academy.seventh.database.connector.ConnectorProvider;
import edition.academy.seventh.database.model.Book;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.springframework.stereotype.Repository;
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

  /**
   * Add books records to the database.
   *
   * @param books list of books
   */
  public void addBooksToDatabase(List<Book> books) {
    entityManager = connectorProvider.getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();

    transaction.begin();
    books.forEach(this::addBookToDataBase);
    transaction.commit();

    entityManager.close();
  }

  /**
   * Retrieves all books from database.
   *
   * @return list of books
   */
  public List<Book> getBooksFromDatabase() {
    entityManager = connectorProvider.getEntityManager();
    List<Book> bookList = entityManager.createQuery("from Book", Book.class).getResultList();
    entityManager.close();
    return bookList;
  }

  private void addBookToDataBase(Book book) {
    entityManager.persist(book);
  }
}
