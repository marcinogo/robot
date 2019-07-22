package edition.academy.seventh.service;

import edition.academy.seventh.database.model.DtoBook;
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
   * Adds given books to database {@link DtoBook}.
   *
   * @param dtBooks {@code List<DtoBook>} to be added
   */
  public void addBooksToDatabase(List<DtoBook> dtBooks) {
    bookRepository.addBooksToDatabase(dtBooks);
  }

  /**
   * Retrieves book entities from database and packs them into the {@code List<DtoBook>}.
   *
   * @return {@code List<DtoBook>}.
   */
  public List<DtoBook> getBooksFromDatabase() {
    return bookRepository.getBooksFromDatabase();
  }
}
