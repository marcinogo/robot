package edition.academy.seventh.repository;

import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.database.model.BookstoreBookDto;
import edition.academy.seventh.model.BookstoreBook;

import java.util.List;

/**
 * Provides basic operations on repository.
 *
 * @author Agnieszka Trzewik
 */
public interface BookRepository {
  /**
   * Persists given books to database.
   *
   * @param bookDtos list of books to persist into database
   */
  void addBooksToDatabase(List<BookDto> bookDtos);

  /** @return latest information about books. */
  List<BookDto> getLatestBooksFromDatabase();

  /**
   * @param href link to the book we are looking for.
   * @return specific {@link BookstoreBook} found by id.
   */
  BookstoreBookDto getBookstoreBookDtoByHref(String href);
}
