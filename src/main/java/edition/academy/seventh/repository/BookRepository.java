package edition.academy.seventh.repository;

import edition.academy.seventh.database.connector.ConnectorFactory;
import edition.academy.seventh.database.connector.ConnectorProvider;
import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.model.Book;
import edition.academy.seventh.model.Bookstore;
import edition.academy.seventh.model.BookstoreBook;
import edition.academy.seventh.model.UrlResources;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static edition.academy.seventh.database.connector.DatabaseTypes.H2;
import static edition.academy.seventh.repository.BookParser.parseBookstoreBookListIntoDTBookList;
import static edition.academy.seventh.repository.BookParser.parseDTBookIntoModel;

/**
 * Allows to persists and retrieve data about books from the database. This information is
 * transfered through the application as {@link BookDto}.
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
   * Adds books records to the database.
   *
   * @param bookDtos {@code List<BookDto>} to be added
   */
  public void addBooksToDatabase(List<BookDto> bookDtos) {
    entityManager = connectorProvider.getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();

    transaction.begin();
    bookDtos.forEach(this::addBookToDatabase);
    transaction.commit();
    entityManager.close();
    connectorProvider.getEntityManagerFactory().close();
  }

  /**
   * Retrieves all books from database.
   *
   * @return {@code List<BookDto>}
   */
  public List<BookDto> getLatestBooksFromDatabase() {

    entityManager = connectorProvider.getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<BookstoreBook> query = criteriaBuilder.createQuery(BookstoreBook.class);

    Root<BookstoreBook> from = query.from(BookstoreBook.class);
    query.select(from);
    List<BookstoreBook> bookstoreBookList = entityManager.createQuery(query).getResultList();
    List<BookDto> booksFromDataBase = parseBookstoreBookListIntoDTBookList(bookstoreBookList);

    entityManager.close();
    return booksFromDataBase;
  }

  private void addBookToDatabase(BookDto bookDtos) {
    BookstoreBook bookstoreBook = parseDTBookIntoModel(bookDtos);

    Bookstore bookstore =
        entityManager.find(
            Bookstore.class, bookstoreBook.getBookstoreBookId().getBookstore().getName());
    Book book =
        entityManager.find(Book.class, bookstoreBook.getBookstoreBookId().getBook().getBookId());
    UrlResources urlResources =
        entityManager.find(UrlResources.class, bookstoreBook.getUrlResources().getHyperLink());
    BookstoreBook previousBookstoreBook =
        entityManager.find(BookstoreBook.class, bookstoreBook.getBookstoreBookId());

    refreshVariablesFromBookstoreBook(
        bookstoreBook, bookstore, book, urlResources, previousBookstoreBook);

    if (previousBookstoreBook == null) entityManager.persist(bookstoreBook);
  }

  private void refreshVariablesFromBookstoreBook(
      BookstoreBook bookstoreBook,
      Bookstore bookstore,
      Book book,
      UrlResources urlResources,
      BookstoreBook previousBookstoreBook) {
    if (bookstore != null) {
      entityManager.refresh(bookstore);
      bookstoreBook.getBookstoreBookId().setBookstore(bookstore);
    }
    if (book != null) {
      entityManager.refresh(book);
      bookstoreBook.getBookstoreBookId().setBook(book);
    }
    if (urlResources != null) {
      entityManager.refresh(urlResources);
      bookstoreBook.setUrlResources(urlResources);
    }
    if (previousBookstoreBook != null) {
      bookstoreBook.setBookstoreBookId(previousBookstoreBook.getBookstoreBookId());
      entityManager.refresh(bookstoreBook);
    }
  }
}
