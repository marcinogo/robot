package edition.academy.seventh.repository;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import edition.academy.seventh.database.connector.ConnectorFactory;
import edition.academy.seventh.database.connector.DatabaseTypes;
import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.database.model.BookstoreBookDto;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@Test
public class BookRepositoryTestIT {

  private Random random = new Random();
  private BookRepository repository;

  @BeforeTest
  public void init(){
    BookDtoParser bookDtoParser = new BookDtoParser(repository);
    repository = new BookRepository(bookDtoParser,
        new ModelParserIntoBookDtos());

    bookDtoParser.setRepository(repository);

    repository.setConnectorProvider(ConnectorFactory.of(DatabaseTypes.H2));
    repository.addBooksToDatabase(
        Collections.singletonList(new BookDto("TEST", "TEST", "TEST", "$",
            new BigDecimal("13.05"), new BigDecimal("15.88")
            , "TEST", "TEST", "TEST")));
  }

  public void should_returnBookFromDatabase(){
    BookstoreBookDto bookstoreBookDtoByHref = repository
        .getBookstoreBookDtoByHref("TEST");

    assertNotNull(bookstoreBookDtoByHref);
  }

  public void should_returnNull_whenGivenHrefDoNotExists(){
    BookstoreBookDto bookstoreBookDtoByHref = repository
        .getBookstoreBookDtoByHref("");

    assertNull(bookstoreBookDtoByHref);
  }

  public void should_returnMoreBooks_whenAddRandomBooksToDatabase(){
    List<BookDto> booksBeforeAdd = repository.getLatestBooksFromDatabase();

    List<BookDto> bookDtos = generateRandomList();

    repository.addBooksToDatabase(bookDtos);

    List<BookDto> booksAfterAdd = repository.getLatestBooksFromDatabase();

    assertTrue(booksBeforeAdd.size()<booksAfterAdd.size());

  }

  public void should_generateListWithRecords_whenCallGetLatestBooksFromDatabase() {
    List<BookDto> latestBooksFromDatabase = repository.getLatestBooksFromDatabase();
    assertFalse(latestBooksFromDatabase.isEmpty());
  }

  private List<BookDto> generateRandomList(){

    return Stream.generate(
        () -> new BookDto(generateRandomString("Title"),
            generateRandomString("Subtitle"),
            generateRandomString("Authors"),
            "$",
            new BigDecimal("10.50"),
            new BigDecimal("11.50"),
            generateRandomString(""),
            generateRandomString("Href") ,
            generateRandomString("Bookstore")))
        .limit(5)
        .collect(Collectors.toList());
  }

  private String generateRandomString(String attribute){
    return attribute+" "+random.nextInt(200);
  }
}