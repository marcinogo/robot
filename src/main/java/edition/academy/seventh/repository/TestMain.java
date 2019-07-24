package edition.academy.seventh.repository;

import edition.academy.seventh.database.connector.ConnectorFactory;
import edition.academy.seventh.database.connector.ConnectorProvider;
import edition.academy.seventh.model.*;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import static edition.academy.seventh.database.connector.DatabaseTypes.H2;

/** @author Agnieszka Trzewik */
public class TestMain {

  static ConnectorProvider connectorProvider = ConnectorFactory.of(H2);
  static EntityManager entityManager = connectorProvider.getEntityManager();

  public static void main(String[] args) {
    //      BookDto bookDto = new BookDto("Tytul", "", "autor", "realCeba", "promoCena",
    //              "Link do obrazka", "href", "Empik");
    //      BookId bookId = new BookId(bookDto.getTitle(), bookDto.getAuthors());
    //      Book book = new Book(bookDto.getSubtitle(), bookId);
    //
    //      Bookstore bookstore = new Bookstore(bookDto.getBookstore());
    //      UrlResources urlResources = new UrlResources(bookDto.getHref(), bookDto.getImageLink());
    //      BookstoreBookId bookstoreBookId = new BookstoreBookId(bookstore, book);
    //
    //      BookstoreBook bookstoreBook = new BookstoreBook(bookstoreBookId, urlResources);
    //
    //      PriceHistory priceHistory = new PriceHistory(bookstoreBook, bookDto.getRetailPrice(),
    // bookDto.getPromotionalPrice(), LocalDateTime.now());
    //
    //      bookstoreBook.getPriceHistories().add(priceHistory);
    //

    //      entityManager = connectorProvider.getEntityManager();
    //      EntityTransaction transaction = entityManager.getTransaction();

    //      transaction.begin();
    //
    //      entityManager.persist(book);
    //      entityManager.persist(bookstore);
    //      entityManager.persist(urlResources);
    //      entityManager.persist(bookstoreBook);
    //
    //      transaction.commit();
    //      entityManager.close();
    //
    //      connectorProvider.getEntityManagerFactory().close();

    entityManager = connectorProvider.getEntityManager();

    UrlResources urlResourcesByHref =
        getUrlResourcesByHref(
            "https://ksiegarnia.pwn.pl/Ilustrowany-slownik-ortograficzny,781898502,p.html");
    BookstoreBook bookstoreBook = findBookstoreBookByUrlResources(urlResourcesByHref);

    BookstoreBook bookstoreBook1 =
        findBookstoreBookById(
            "ITBookstore",
            "Learning C++ by Building Games with Unreal Engine 4, 2nd Edition",
            "Sharan Volin");

    bookstoreBook.getPriceHistories().forEach(p -> System.out.println(p.getDate()));
    System.out.println("================================");
    bookstoreBook1.getPriceHistories().forEach(p -> System.out.println(p.getDate()));

    entityManager.close();
    connectorProvider.getEntityManagerFactory().close();
  }

  private static BookstoreBook findBookstoreBookById(
      String bookstoreName, String title, String author) {
    return entityManager.find(
        BookstoreBook.class,
        new BookstoreBookId(new Bookstore(bookstoreName), new Book("", new BookId(title, author))));
  }

  private static BookstoreBook findBookstoreBookByUrlResources(UrlResources singleResult) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

    CriteriaQuery<BookstoreBook> query = criteriaBuilder.createQuery(BookstoreBook.class);
    Root<BookstoreBook> from = query.from(BookstoreBook.class);
    Path<Object> urlResources1 = from.get("urlResources");
    query.select(from).where(criteriaBuilder.equal(urlResources1, singleResult));
    return entityManager.createQuery(query).getSingleResult();
  }

    private static UrlResources getUrlResourcesByHref(String href) {
        return entityManager.find(UrlResources.class, href);
    }

    //stworzyc tytul autor, wszytskie pricehistory
}
