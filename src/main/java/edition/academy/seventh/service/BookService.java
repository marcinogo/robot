package edition.academy.seventh.service;

import edition.academy.seventh.database.model.DTBook;
import edition.academy.seventh.repository.BookRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
   * Adds given books to database {@link DTBook}.
   *
   * @param dtBooks {@code List<DTBook>} to be added
   */
  public void addBooksToDatabase(List<DTBook> dtBooks) {
    bookRepository.addBooksToDatabase(dtBooks);
  }

  /**
   * Retrieves book entities from database and packs them into the {@code List<DTBook>}.
   *
   * @return {@code List<DTBook>}.
   */
  public List<DTBook> getBooksFromDatabase() {
    return bookRepository.getBooksFromDatabase();
  }
}
