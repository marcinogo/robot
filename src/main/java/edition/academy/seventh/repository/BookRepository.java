package edition.academy.seventh.repository;

import static edition.academy.seventh.database.connector.DatabaseTypes.POSTGRESQL;
import static edition.academy.seventh.repository.BookParser.parseBookstoreBookListIntoDTBookList;
import static edition.academy.seventh.repository.BookParser.parseDTBookIntoModel;

import edition.academy.seventh.database.connector.ConnectorFactory;
import edition.academy.seventh.database.connector.ConnectorProvider;
import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.model.Book;
import edition.academy.seventh.model.Bookstore;
import edition.academy.seventh.model.BookstoreBook;
import edition.academy.seventh.model.UrlResources;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Allows to persists and retrieve data about books from the database. This information is
 * transferred through the application as {@link BookDto}.
 *
 * @author Agnieszka Trzewik
 */
@Repository
public class BookRepository {
  private final static Logger logger = LoggerFactory.getLogger(BookRepository.class);
  private EntityManager entityManager;
  private ConnectorProvider connectorProvider;

  public BookRepository() {
    connectorProvider = ConnectorFactory.of(POSTGRESQL);
  }

  /**
   * Adds books records to the database.
   *
   * @param bookDtos {@code List<BookDto>} to be added
   */
  public void addBooksToDatabase(List<BookDto> bookDtos) {
    entityManager = connectorProvider.getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();

    transaction.begin();
    bookDtos.forEach(this::addBookToDatabase);
    logger.info("Saving " + bookDtos.size() + " books in database");
    transaction.commit();

    connectorProvider.close();
  }

  /**
   * Retrieves all books from database.
   *
   * @return {@code List<BookDto>}
   */
  public List<BookDto> getBooksFromDatabase() {

    entityManager = connectorProvider.getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<BookstoreBook> query = criteriaBuilder.createQuery(BookstoreBook.class);

    Root<BookstoreBook> from = query.from(BookstoreBook.class);
    query.select(from);
    List<BookstoreBook> bookstoreBookList = entityManager.createQuery(query).getResultList();

    connectorProvider.close();
    logger.info("Called getBooksFromDatebase(), returning " + bookstoreBookList.size() + " books");
    return parseBookstoreBookListIntoDTBookList(bookstoreBookList);
  }

  private void addBookToDatabase(BookDto bookDtos) {
    BookstoreBook bookstoreBook = parseDTBookIntoModel(bookDtos);

    Bookstore bookstore =
        entityManager.find(Bookstore.class, bookstoreBook.getBookstore().getName());
    Book book = entityManager.find(Book.class, bookstoreBook.getBook().getBookId());
    UrlResources hrefAndImage =
        entityManager.find(UrlResources.class, bookstoreBook.getUrlResources().getHyperLink());

    refreshVariablesFromBookstoreBook(bookstoreBook, bookstore, book, hrefAndImage);

    entityManager.persist(bookstoreBook);
  }

  private void refreshVariablesFromBookstoreBook(
      BookstoreBook bookstoreBook, Bookstore bookstore, Book book, UrlResources hrefAndImage) {
    if (bookstore != null) {
      entityManager.refresh(bookstore);
      bookstoreBook.setBookstore(bookstore);
    }
    if (book != null) {
      entityManager.refresh(book);
      bookstoreBook.setBook(book);
    }
    if (hrefAndImage != null) {
      entityManager.refresh(hrefAndImage);
      bookstoreBook.setUrlResources(hrefAndImage);
    }
  }
}
