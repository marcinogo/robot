package edition.academy.seventh.persistence;

import edition.academy.seventh.persistence.response.BookDto;
import edition.academy.seventh.persistence.response.BookstoreBookDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.testng.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class BookRepositoryTestIT {

    private Random random = new Random();
    @Autowired
    private  BookRepository repository;

    @Before
    public void init() {

        repository.addBooksToDatabase(
                Collections.singletonList(
                        new BookDto(
                                "TEST",
                                "TEST",
                                "TEST",
                                "$",
                                new BigDecimal("13.05"),
                                new BigDecimal("15.88"),
                                "TEST",
                                "TEST",
                                "TEST")));
    }

    @Test
    public void should_returnBookFromDatabase() {
        BookstoreBookDto bookstoreBookDtoByHref = repository.getBookstoreBookDtoByHref("TEST");

        assertNotNull(bookstoreBookDtoByHref);
    }

    @Test
    public void should_returnNull_whenGivenHrefDoNotExists() {
        BookstoreBookDto bookstoreBookDtoByHref = repository.getBookstoreBookDtoByHref("");

        assertNull(bookstoreBookDtoByHref);
    }

    @Test
    public void should_returnMoreBooks_whenAddRandomBooksToDatabase() {
        List<BookDto> booksBeforeAdd = repository.getLatestBooksFromDatabase();

        List<BookDto> bookDtos = generateRandomList();

        repository.addBooksToDatabase(bookDtos);

        List<BookDto> booksAfterAdd = repository.getLatestBooksFromDatabase();

        assertTrue(booksBeforeAdd.size() < booksAfterAdd.size());
    }

    @Test
    public void should_generateListWithRecords_whenCallGetLatestBooksFromDatabase() {
        List<BookDto> latestBooksFromDatabase = repository.getLatestBooksFromDatabase();
        assertFalse(latestBooksFromDatabase.isEmpty());
    }

    private List<BookDto> generateRandomList() {

        return Stream.generate(
                () ->
                        new BookDto(
                                generateRandomString("Title"),
                                generateRandomString("Subtitle"),
                                generateRandomString("Authors"),
                                "$",
                                new BigDecimal("10.50"),
                                new BigDecimal("11.50"),
                                generateRandomString(""),
                                generateRandomString("Href"),
                                generateRandomString("Bookstore")))
                .limit(5)
                .collect(Collectors.toList());
    }

    private String generateRandomString(String attribute) {
        return attribute + " " + random.nextInt(200);
    }
}
