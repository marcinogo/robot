package edition.academy.seventh.serivces;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import edition.academy.seventh.database.models.Book;

/** @author Bartosz Kupajski */
@Service
class ITBookMapper {

  private ObjectMapper objectMapper;

  ITBookMapper() {
    this.objectMapper = new ObjectMapper();
  }

  /**
   * Maps JSON books file into List<Book>
   *
   * @param listOfJSONBook list with books' JSONs
   * @return List<Book>
   * @throws IOException
   */
  List<Book> mapListOfJSON(List<String> listOfJSONBook) throws IOException {

    List<Book> listOfBooks = new LinkedList<>();

    for (String bookJSON : listOfJSONBook) {
      Book book = objectMapper.readValue(bookJSON, Book.class);
      listOfBooks.add(book);
    }

    return listOfBooks;
  }
}
