// package edition.academy.seventh.pagination;
//
// import edition.academy.seventh.connector.ConnectorFactory;
// import edition.academy.seventh.connector.ConnectorProvider;
// import edition.academy.seventh.connector.DatabaseType;
// import edition.academy.seventh.persistence.model.BookstoreBook;
// import org.hibernate.Session;
// import org.hibernate.query.Query;
//
// import javax.persistence.EntityManager;
// import java.util.List;
//
/// ** @author Agnieszka Trzewik */
// public class PaginationExample {
//
//  public static void main(String[] args) {
//    ConnectorProvider connectorProvider = ConnectorFactory.of(DatabaseType.valueOf("H2"));
//    EntityManager entityManager = connectorProvider.getEntityManager();
//    Session session = entityManager.unwrap(Session.class);
//    String sql = "FROM " + BookstoreBook.class.getName();
////            + " WHERE ID > :bId ";
//    Query<BookstoreBook> query = session.createQuery(sql, BookstoreBook.class);
////    query.setParameter("bId", 100);
//
//    int page = 1;
//    int maxResult = 20;
//    int maxNavigationResult = 10;
//
//    PaginationResult paginationResult = new PaginationResult(query);
//
//    PaginationResult result =
//        paginationResult.changePaginationResult(page, maxResult, maxNavigationResult);
//
//    // Result:
//    List<BookstoreBook> bookstoreBooks = result.getList();
//    int totalPages = result.getTotalRecords();
//    int totalRecords = result.getTotalRecords();
//
//    // 1 2 3 4 5 ... 11 12 13
//    List<Integer> navPages = result.getNavigationPages();
//
//    System.out.println(result.getCurrentPage());
//    System.out.println(result.getTotalRecords());
//    bookstoreBooks.forEach(bookstoreBook ->
// System.out.println(bookstoreBook.getBook().getBookId().getTitle()));
//  }
// }
