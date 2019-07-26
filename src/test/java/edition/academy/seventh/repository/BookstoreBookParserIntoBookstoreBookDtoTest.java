package edition.academy.seventh.repository;

import static org.testng.Assert.assertTrue;

import edition.academy.seventh.database.model.BookstoreBookDto;
import edition.academy.seventh.model.Book;
import edition.academy.seventh.model.BookId;
import edition.academy.seventh.model.Bookstore;
import edition.academy.seventh.model.BookstoreBook;
import edition.academy.seventh.model.PriceAtTheMoment;
import java.util.List;
import org.testng.annotations.Test;

@Test
public class BookstoreBookParserIntoBookstoreBookDtoTest {


  @Test(invocationCount = 100,
      dataProviderClass = DataProviderForBookstoreBookParser.class,
      dataProvider = "dataProviderBookstoreBookParser")
  public void should_createProperBookstoreBookDto_from_givenBookstoreBook(
      String title, String author, String subtitle, String bookstoreName,
      String hyperlink, String img, List<PriceAtTheMoment> priceAtTheMomentList) {

    // Given
    BookstoreBookParserIntoBookstoreBookDto parser = new BookstoreBookParserIntoBookstoreBookDto();

    BookstoreBook bookstoreBook = createBookstoreBook(title, author, subtitle, bookstoreName,
        hyperlink, img);

    bookstoreBook.setPriceHistories(priceAtTheMomentList);

    //  When
    BookstoreBookDto bookstoreBookDto = parser
        .parseBookstoreBookIntoBookstoreBookDto(bookstoreBook);

    //   Then
    assertTrue(compare(bookstoreBook, bookstoreBookDto));

  }

  private BookstoreBook createBookstoreBook(String title, String author, String subtitle,
      String bookstoreName, String hyperlink, String img) {
    BookId bookId = new BookId(title, author);
    Book book = new Book(bookId,subtitle);
    Bookstore bookstore = new Bookstore(bookstoreName);
    return new BookstoreBook(hyperlink, img, bookstore, book);
  }

  private boolean compare(BookstoreBook bookstoreBook, BookstoreBookDto bookstoreBookDto) {
    boolean titles = bookstoreBookDto.getTitle()
        .equals(bookstoreBook.getBook().getBookId().getTitle());
    boolean historySize =
        bookstoreBookDto.getPriceAtTheMomentDtos().size() == bookstoreBook.getPriceHistories().size();
    boolean href = bookstoreBookDto.getHref().equals(bookstoreBook.getHyperlink());
    boolean image = bookstoreBookDto.getImageLink().equals(bookstoreBook.getImageLink());
    boolean bookstore = bookstoreBookDto.getBookstore()
        .equals(bookstoreBook.getBookstore().getName());
    boolean date = bookstoreBookDto.getPriceAtTheMomentDtos().get(0).getDate()
        .equals(bookstoreBook.getPriceHistories().get(0).getDate());
    return titles && historySize && href && image && bookstore && date;
  }
}