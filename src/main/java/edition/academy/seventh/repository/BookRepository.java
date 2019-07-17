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
  private CriteriaBuilder criteriaBuilder;

  public BookRepository() {
    connectorProvider = ConnectorFactory.of(POSTGRESQL);
    entityManager = connectorProvider.getEntityManager();
    criteriaBuilder = entityManager.getCriteriaBuilder();
  }

  public void addBooksToDataBase(List<Book> books) {
    EntityTransaction transaction = entityManager.getTransaction();

    transaction.begin();
    books.forEach(this::addBookToDataBase);
    transaction.commit();

    entityManager.close();
  }

  public List<Book> getBooksFromDataBase() {
    CriteriaQuery<Book> query = criteriaBuilder.createQuery(Book.class);
    Root<Book> from = query.from(Book.class);
    query.select(from);
    List<Book> bookList = entityManager.createQuery(query).getResultList();

    entityManager.close();
    return bookList;
  }

  private void addBookToDataBase(Book book) {
    entityManager.persist(book);
  }
}
