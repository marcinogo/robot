package edition.academy.seventh.serivce;

import edition.academy.seventh.database.model.Book;
import edition.academy.seventh.repository.BookRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service that provides API responsible for adding books into database.
 *
 * @author Agnieszka Trzewik
 */
@Service
public class BooksService {

  private BookRepository bookRepository;

  @Autowired
  public BooksService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  void addBooksToDataBase(List<Book> books) {
    bookRepository.addBooksToDataBase(books);
  }
}
