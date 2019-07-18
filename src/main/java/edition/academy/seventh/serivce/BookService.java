package edition.academy.seventh.serivce;

import edition.academy.seventh.database.model.Book;
import edition.academy.seventh.repository.BookRepository;
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
   * Adds given books to database {@link edition.academy.seventh.database.model.Book}.
   *
   * @param books list of books to be added
   */
  public void addBooksToDatabase(List<Book> books) {
    bookRepository.addBooksToDatabase(books);
  }

  /**
   * Retrieves book entities from database and packs them into the List
   *
   * @return List of books.
   */
  public List<Book> getBooksFromDatabase() {
    return bookRepository.getBooksFromDatabase();
  }
}
