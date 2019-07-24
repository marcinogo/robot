package edition.academy.seventh.service;

import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.database.model.BookstoreBookDto;
import edition.academy.seventh.model.BookstoreBook;
import edition.academy.seventh.repository.BookRepositoryImpl;
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
  private BookRepositoryImpl bookRepository;

  @Autowired
  public BookService(BookRepositoryImpl bookRepository) {
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
   * Retrieves specific {@link BookstoreBook} with {@link BookService#bookRepository} based on the book's hyperlink.
   *
   * @param href link to the book we are looking for.
   * @return {@link BookstoreBook} found by id.
   */
  public BookstoreBookDto getBookstoreBookDtoByHref(String href) {
    return bookRepository.getBookstoreBookDtoByHref(href);
  }
}
