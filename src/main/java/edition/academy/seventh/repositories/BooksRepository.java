package edition.academy.seventh.repositories;

import edition.academy.seventh.database.connectors.EntityConnector;
import edition.academy.seventh.database.connectors.H2Connector;
import edition.academy.seventh.database.connectors.PostgresConnector;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import edition.academy.seventh.database.models.Book;
import java.util.List;

/**
 * Repository to persist books in PostgreSQL database.
 *
 * @author Agnieszka Trzewik
 */
@Repository
public class BooksRepository {

    private EntityManager entityManager;
    private EntityConnector connector;


    public BooksRepository() {
        this.connector = new H2Connector();
    }

    public void addRecordsToDataBase(List<Book> books) {
        entityManager = connector.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        books.forEach(this::addRecordToDataBase);
        transaction.commit();
        entityManager.close();
    }

    private void addRecordToDataBase(Book book) {
        entityManager.persist(book);
    }
}