package edition.academy.seventh.serivces;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import edition.academy.seventh.database.models.Book;
import java.util.LinkedList;
import java.util.List;

/** @author Bartosz Kupajski */
@Service
public class ITBookMapper {

  private ObjectMapper objectMapper;

  public ITBookMapper() {
    this.objectMapper = new ObjectMapper();
  }

  public List<Book> mapListOfJSON(List<String> listOfJSONBook) throws IOException {

    List<Book> listOfBooks = new LinkedList<>();

    for (String bookJSON : listOfJSONBook) {
      Book book = objectMapper.readValue(bookJSON, Book.class);
      listOfBooks.add(book);
    }

    return listOfBooks;
  }
}
