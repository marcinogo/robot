package edition.academy.seventh.repository;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import edition.academy.seventh.database.connector.ConnectorFactory;
import edition.academy.seventh.database.connector.DatabaseTypes;
import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.database.model.BookstoreBookDto;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Before;
import org.testng.annotations.Test;

@Test
public class BookRepositoryImplTestIT {

  private Random random = new Random();
  private BookRepository repository;

  @Before
  public void init(){
    repository = new BookRepositoryImpl(new BookDtoParserIntoModel(),
        new ModelParserIntoBookDtos(), new BookstoreBookParserIntoBookstoreBookDto());
    repository.setConnectorProvider(ConnectorFactory.of(DatabaseTypes.H2));
  }

  public void should_generateListWithRecords_whenCallGetLatestBooksFromDatabase() {
    List<BookDto> latestBooksFromDatabase = repository.getLatestBooksFromDatabase();
    assertFalse(latestBooksFromDatabase.isEmpty());
  }

  public void should_returnBookFromDatabase(){
    BookstoreBookDto bookstoreBookDtoByHref = repository
        .getBookstoreBookDtoByHref("Href 41");

    assertNotNull(bookstoreBookDtoByHref);
  }

  public void should_returnMoreBooks_whenAddRandomBooksToDatabase(){
    List<BookDto> booksBeforeAdd = repository.getLatestBooksFromDatabase();

    List<BookDto> bookDtos = generateRandomList();

    repository.addBooksToDatabase(bookDtos);

    List<BookDto> booksAfterAdd = repository.getLatestBooksFromDatabase();

    assertTrue(booksBeforeAdd.size()<booksAfterAdd.size());

  }

  private List<BookDto> generateRandomList(){

    return Stream.generate(
        () -> new BookDto(generateRandomString("Title"),
            generateRandomString("Subtitle"),
            generateRandomString("Authors"),
            generateRandomString(""),
            generateRandomString(""),
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