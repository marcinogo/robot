package edition.academy.seventh.pagination;

import edition.academy.seventh.connector.ConnectorFactory;
import edition.academy.seventh.connector.DatabaseType;
import edition.academy.seventh.persistence.response.BookDto;
import edition.academy.seventh.persistence.BookDtoParser;
import edition.academy.seventh.persistence.BookRepository;
import edition.academy.seventh.persistence.BookService;

/**
 * Obtains an instance of specific implementation of {@link Pagination} API.
 *
 * @author Kamil Rojek
 */
class PaginationFactory {

  private static BookRepository bookRepository;
  private static BookDtoParser parser;

  static {
    parser = new BookDtoParser(null);
    bookRepository = new BookRepository(parser);
    parser.setRepository(bookRepository);
  }

  /**
   * Creates eager book pagination object.
   *
   * @return {@link EagerBookPagination}.
   */
  static Pagination<BookDto> createEagerBookPagination() {
    return new EagerBookPagination(new BookService(bookRepository));
  }

  /**
   * Creates lazy book pagination object.
   *
   * @return {@link LazyBookPagination}.
   */
  static Pagination<BookDto> createLazyBookPagination(DatabaseType databaseType) {
    return new LazyBookPagination(ConnectorFactory.of(databaseType));
  }
}
