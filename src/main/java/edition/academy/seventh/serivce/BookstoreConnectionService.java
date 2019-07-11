package edition.academy.seventh.serivce;

import java.util.List;

/**
 * Provide API to get books on sale from given bookstore.
 *
 * @author Marcin Ogorzałek
 * @author Ola Podorska
 */
public interface BookstoreConnectionService {

  /**
   * Return books from bookstore as list JSON String to further processing.
   *
   * @return list of book JSON as strings
   */
  List<String> getListOfBooksAsString();
}
