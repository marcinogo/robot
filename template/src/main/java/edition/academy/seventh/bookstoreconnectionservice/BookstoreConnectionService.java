package edition.academy.seventh.bookstoreconnectionservice;

import java.util.List;

/**
 * Connects with bookstore
 *
 * @author Marcin Ogorzałek
 * @author Ola Podorska
 */
public interface BookstoreConnectionService {

    /**
     * connects with specified url and gets books list as string
     *
     * @return list of books as strings
     */
    List<String> getListOfBooksAsString();
}
