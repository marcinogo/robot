package edition.academy.seventh.service.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import edition.academy.seventh.database.model.BookDto;
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
public class ItBookMapper {

  private ObjectMapper objectMapper;

  public ItBookMapper() {
    this.objectMapper = new ObjectMapper();
  }

  /**
   * Maps JSON books file into {@code List<Book>}.
   *
   * @param booksAsJson list with books' JSONs
   * @return {@code List<Book>}
   * @throws IOException when JSON isn't read properly
   */
  public List<BookDto> mapListOfJson(List<String> booksAsJson) throws IOException {
    List<BookDto> listOfBooks = new LinkedList<>();
    String nameOfTheBookstore = "ITBookstore";

    for (String bookJSON : booksAsJson) {
      BookDto book = objectMapper.readValue(bookJSON, BookDto.class);
      book.setBookstore(nameOfTheBookstore);
      book.setPromotionalPrice("");
      listOfBooks.add(book);
    }

    return listOfBooks;
  }
}
