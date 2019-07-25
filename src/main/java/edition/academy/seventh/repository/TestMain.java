package edition.academy.seventh.repository;

/** @author Agnieszka Trzewik */
public class TestMain {
}
//  static ConnectorProvider connectorProvider = ConnectorFactory.of(H2);
//  static EntityManager entityManager = connectorProvider.getEntityManager();
//
//  public static void main(String[] args) {
//    BookDto bookDto =
//        new BookDto(
//            "Tytul", "", "autor", "realCeba", "promoCena", "Link do obrazka", "href", "Empik");
//    BookId bookId = new BookId(bookDto.getTitle(), bookDto.getAuthors());
//    Book book = new Book(bookDto.getSubtitle(), bookId);
//
//    Bookstore bookstore = new Bookstore(bookDto.getBookstore());
//
//    BookstoreBook bookstoreBook =
//        new BookstoreBook(bookDto.getHref(), bookDto.getImageLink(), bookstore, book);
//
//    PriceHistory priceHistory =
//        new PriceHistory(
//            bookstoreBook,
//            bookDto.getRetailPrice(),
//            bookDto.getPromotionalPrice(),
//            LocalDateTime.now());
//
//    bookstoreBook.getPriceHistories().add(priceHistory);
//
//    BookDto bookDto2 =
//        new BookDto(
//            "Tytul", "", "autor", "realCeba", "promoCena", "Link do obrazka", "href", "Empik");
//    BookId bookId2 = new BookId(bookDto.getTitle(), bookDto.getAuthors());
//    Book book2 = new Book(bookDto.getSubtitle(), bookId);
//
//    Bookstore bookstore2 = new Bookstore(bookDto.getBookstore());
//
//    BookstoreBook bookstoreBook2 =
//        new BookstoreBook(bookDto.getHref(), "fff", bookstore, book);
//
//    PriceHistory priceHistory2 =
//        new PriceHistory(
//            bookstoreBook,
//            bookDto.getRetailPrice(),
//            bookDto.getPromotionalPrice(),
//            LocalDateTime.now());
//
//    bookstoreBook.getPriceHistories().add(priceHistory);
//
//    entityManager = connectorProvider.getEntityManager();
//    EntityTransaction transaction = entityManager.getTransaction();
//
//    transaction.begin();
//
//    entityManager.persist(book);
//    entityManager.persist(bookstore);
//    entityManager.persist(bookstoreBook);
//
//    Book book1 = entityManager.find(Book.class, book2.getBookId());
//    book.setBookId(book2.getBookId());
//    entityManager.refresh(book);
//    Bookstore bookstore1 = entityManager.find(Bookstore.class, bookstore2.getName());
//    bookstore.setName(bookstore2.getName());
//    entityManager.refresh(bookstore);
//    BookstoreBook bookstoreBook1 = entityManager.find(BookstoreBook.class, bookstoreBook2.getHyperLink());
//    if (bookstoreBook1 != null) {
//      bookstoreBook2.setHyperLink(bookstoreBook1.getHyperLink());
//      entityManager.merge(bookstoreBook2);
//
//    }
//    else entityManager.persist(bookstoreBook2);
//
//
//    transaction.commit();
//    entityManager.close();
//
//    connectorProvider.getEntityManagerFactory().close();
//
//    //    entityManager = connectorProvider.getEntityManager();
//    //
//    //
//    //    BookstoreBook bookstoreBook =
//    //
//    // findBookstoreBookById("https://ksiegarnia.pwn.pl/Ilustrowany-slownik-ortograficzny,781898502,p.html");
//    //
//    //    bookstoreBook.getPriceHistories().forEach(p -> System.out.println(p.getDate()));
//    //
//    //    entityManager.close();
//    //    connectorProvider.getEntityManagerFactory().close();
//  }
//
//  private static BookstoreBook findBookstoreBookById(String href) {
//    return entityManager.find(BookstoreBook.class, href);
//  }

  // stworzyc tytul autor, wszytskie pricehistory
