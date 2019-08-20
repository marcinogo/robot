package edition.academy.seventh.persistence;

import edition.academy.seventh.persistence.model.BookstoreBook;
import edition.academy.seventh.persistence.response.BookDto;
import edition.academy.seventh.persistence.response.BookstoreBookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Provides API responsible for adding books to database.
 *
 * @author Agnieszka Trzewik
 */
@Service
public class BookService {
  private BookRepository bookRepository;

  @Autowired
  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  /**
   * Adds given books to database {@link BookDto}.
   *
   * @param bookDtos {@code List<BookDto>} to be added
   */
  public void addBooksToDatabase(List<BookDto> bookDtos) {
    bookRepository.addBooksToDatabase(bookDtos);
  }

  /**
   * Retrieves book entities from database and packs them into the {@code List<BookDto>}.
   *
   * @return all books currently stored in database.
   */
  public List<BookDto> getBooksFromDatabase() {
    return bookRepository.getLatestBooksFromDatabase();
  }

  /**
   * Retrieves specific {@link BookstoreBook} with {@link BookService#bookRepository} based on the
   * book's hyperlink.
   *
   * @param href link to the book to be found.
   * @return {@link BookstoreBook} found by id.
   */
  BookstoreBookDto getBookstoreBookDtoByHref(String href) {
    return bookRepository.getBookstoreBookDtoByHref(href);
  }
}
