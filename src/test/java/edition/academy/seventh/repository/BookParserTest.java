package edition.academy.seventh.repository;

import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.model.*;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static edition.academy.seventh.repository.BookParser.parseBookstoreBookListIntoDTBookList;
import static edition.academy.seventh.repository.BookParser.parseDTBookIntoModel;
import static org.testng.Assert.assertEquals;

@Test
public class BookParserTest {

  @Test(
      dataProviderClass = DataProviderForBookParser.class,
      dataProvider = "dataProviderForBookParsing")
  public void should_createBookstoreBook_when_givenDTBook(
      String title,
      String subtitle,
      String author,
      String retailPrice,
      String promotionalPrice,
      String hyperLink,
      String imageLink,
      String bookstore) {

    // Given
    BookDto dtBook =
        new BookDto(
            title,
            subtitle,
            author,
            retailPrice,
            promotionalPrice,
            imageLink,
            hyperLink,
            bookstore);
    BookstoreBook expectedBookstoreBook =
        new BookstoreBook(
            new BookstoreBookId(
                new Bookstore(bookstore), new Book(subtitle, new BookId(title, author))),
            new UrlResources(hyperLink, imageLink));

    // When
    BookstoreBook bookstoreBook = parseDTBookIntoModel(dtBook);

    // Then
    assertEquals(bookstoreBook, expectedBookstoreBook);
  }

  @Test(
      dataProviderClass = DataProviderForBookParser.class,
      dataProvider = "dataProviderForBookParsing")
  public void should_createDTBookList_when_givenBookstoreBookList(
      String title,
      String subtitle,
      String author,
      String retailPrice,
      String promotionalPrice,
      String imageLink,
      String hyperLink,
      String bookstore) {

    // Given
    BookstoreBook bookstoreBook =
        new BookstoreBook(
            new BookstoreBookId(
                new Bookstore(bookstore), new Book(subtitle, new BookId(title, author))),
            new UrlResources(hyperLink, imageLink));
    PriceHistory priceHistory =
        new PriceHistory(bookstoreBook, retailPrice, promotionalPrice, LocalDateTime.now());
    bookstoreBook.getPriceHistories().add(priceHistory);

    BookDto dtBook =
        new BookDto(
            title,
            subtitle,
            author,
            retailPrice,
            promotionalPrice,
            imageLink,
            hyperLink,
            bookstore);

    List<BookstoreBook> bookstoreBooks = List.of(bookstoreBook);

    List<BookDto> expectedDTBooks = List.of(dtBook);

    // When
    List<BookDto> dtBooks = parseBookstoreBookListIntoDTBookList(bookstoreBooks);

    // Then
    assertEquals(dtBooks, expectedDTBooks);
  }
}
