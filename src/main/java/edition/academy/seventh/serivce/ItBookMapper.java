package edition.academy.seventh.serivce;

import com.fasterxml.jackson.databind.ObjectMapper;
import edition.academy.seventh.database.model.DTBook;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

/** @author Bartosz Kupajski */
@Service
public class ItBookMapper {

  private ObjectMapper objectMapper;

  public ItBookMapper() {
    this.objectMapper = new ObjectMapper();
  }

  /**
   * Maps JSON books file into {@code List<BookClass>}
   *
   * @param listOfJSONBook list with books' JSONs
   * @return {@code List<BookClass>}
   * @throws IOException when JSON isn't read properly
   */
  public List<DTBook> mapListOfJson(List<String> listOfJSONBook) throws IOException {
    List<DTBook> listOfBooks = new LinkedList<>();

    for (String bookJSON : listOfJSONBook) {
      DTBook book = objectMapper.readValue(bookJSON, DTBook.class);
      listOfBooks.add(book);
    }

    return listOfBooks;
  }
}
