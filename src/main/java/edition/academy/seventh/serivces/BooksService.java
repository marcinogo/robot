package edition.academy.seventh.serivces;


import edition.academy.seventh.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edition.academy.seventh.database.models.Book;
import java.util.List;

/**
 * Service which connect with repository {@link BooksRepository} to persist books in PostgreSQL
 * database.
 *
 * @author Agnieszka Trzewik
 */
@Service
public class BooksService {

    private BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    void addBooksToDataBase(List<Book> books) {
        booksRepository.addRecordsToDataBase(books);
    }
}