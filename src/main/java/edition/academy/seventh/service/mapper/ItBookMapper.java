package edition.academy.seventh.service.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.service.BookstoreConnectionService;
import edition.academy.seventh.service.PromotionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Responsible for mapping JSON objects from ITBook library to {@link BookDto}.
 *
 * @author Bartosz Kupajski
 */
@Service
public class ItBookMapper implements PromotionProvider {

  private static final Logger logger = LoggerFactory.getLogger(ItBookMapper.class);
  private ObjectMapper objectMapper;
  private BookstoreConnectionService bookstoreConnectionService;

  @Autowired
  public ItBookMapper(BookstoreConnectionService bookstoreConnectionService) {
    this.objectMapper = new ObjectMapper();
    this.bookstoreConnectionService = bookstoreConnectionService;
  }

  /**
   * Maps JSON books file into {@code List<Book>}.
   *
   * @return {@code List<Book>}
   */
  public List<BookDto> getPromotions() {
    List<BookDto> listOfBooks = new LinkedList<>();
    List<String> booksAsJson = bookstoreConnectionService.getListOfBooksAsString();
    String nameOfTheBookstore = "ITBookstore";
    for (String bookJSON : booksAsJson) {
      BookDto book;
      try {
        book = objectMapper.readValue(bookJSON, BookDto.class);
      } catch (IOException e) {
        logger.info("Error occurred during mapping JSON to BookDto" + e.getMessage());
        continue;
      }
      book.setBookstore(nameOfTheBookstore);
      book.setPromotionalPrice("");
      listOfBooks.add(book);
    }
    return listOfBooks;
  }
}
