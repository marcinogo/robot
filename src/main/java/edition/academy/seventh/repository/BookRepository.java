package edition.academy.seventh.repository;

import edition.academy.seventh.database.connector.ConnectorFactory;
import edition.academy.seventh.database.connector.ConnectorProvider;
import edition.academy.seventh.database.model.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static edition.academy.seventh.database.connector.ConnectorFactory.DatabaseTypes.POSTGRESQL;
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
    connectorProvider = ConnectorFactory.of(POSTGRESQL);
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

  public List<Book> getBooksFromDataBase() {
    entityManager = connectorProvider.getEntityManager();
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

    CriteriaQuery<Book> query = criteriaBuilder.createQuery(Book.class);
    Root<Book> from = query.from(Book.class);
    query.select(from);
    List<Book> bookList = entityManager.createQuery(query).getResultList();

    entityManager.close();
    return bookList;
  }
}
