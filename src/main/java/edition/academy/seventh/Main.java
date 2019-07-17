package edition.academy.seventh;

/**
 * Entry point for entire app.
 *
 * @author Kamil Rojek
 */
//@SpringBootApplication(scanBasePackages = "edition.academy.seventh")
//public class Main {
//  public static void main(String[] args) {
//    ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
//
//    BookstoreConnectionService connectionService =
//        context.getBean(BookstoreConnectionService.class);
//    List<String> listOfBooksAsString = connectionService.getListOfBooksAsString();
//
//    ItBookMapper bookMapper = context.getBean(ItBookMapper.class);
//    List<DTBook> books = null;
//
//    try {
//      books = bookMapper.mapListOfJson(listOfBooksAsString);
//    } catch (IOException e) {
//      System.err.println(e.getMessage());
//    }
//
//    BookService bookService = context.getBean(BookService.class);
//    //    bookService.addBooksToDataBase(books);
//    bookService.getBooksFromDataBase().forEach(b -> System.out.println(b.getAuthors()));
//  }
//}
