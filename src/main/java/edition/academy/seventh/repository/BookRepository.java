package edition.academy.seventh.repository;

import edition.academy.seventh.database.connector.ConnectorFactory;
import edition.academy.seventh.database.connector.ConnectorProvider;
import edition.academy.seventh.database.model.DTBook;
import edition.academy.seventh.model.Book;
import edition.academy.seventh.model.Bookstore;
import edition.academy.seventh.model.BookstoreBook;
import edition.academy.seventh.model.HrefAndImage;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

import static edition.academy.seventh.database.connector.ConnectorFactory.DatabaseTypes.H2;
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
    connectorProvider = ConnectorFactory.of(H2);
  }

  public void addBooksToDataBase(List<BookstoreBook> bookstoreBooks) {
    entityManager = connectorProvider.getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();

    transaction.begin();
    bookstoreBooks.forEach(this::addObject);
    transaction.commit();

    entityManager.close();
  }

  public List<DTBook> getBooksFromDataBase() {
    entityManager = connectorProvider.getEntityManager();
    List<DTBook> bookList = entityManager.createQuery("from DTBook", DTBook.class).getResultList();
    entityManager.close();
    return bookList;
  }

  private void addBookToDataBase(BookstoreBook bookstoreBook) {
    entityManager.persist(bookstoreBook);
  }

  private void addObject(BookstoreBook bookstoreBook) {
    Bookstore bookstore =
        entityManager.find(Bookstore.class, bookstoreBook.getBookstore().getName());
    Book book = entityManager.find(Book.class, bookstoreBook.getBook().getBookId());
    HrefAndImage hrefAndImage =
        entityManager.find(HrefAndImage.class, bookstoreBook.getHrefAndImage().getHyperLink());
    if (bookstore != null) {
      entityManager.refresh(bookstore);
      bookstoreBook.setBookstore(bookstore);
    }
    if (book != null) {
      entityManager.refresh(book);
      bookstoreBook.setBook(book);
    }
    if (hrefAndImage != null){
      entityManager.refresh(hrefAndImage);
      bookstoreBook.setHrefAndImage(hrefAndImage);
    }
      entityManager.persist(bookstoreBook);

  }
}
