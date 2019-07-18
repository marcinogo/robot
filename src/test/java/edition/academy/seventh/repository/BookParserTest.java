package edition.academy.seventh.repository;

import edition.academy.seventh.database.model.DTBook;
import edition.academy.seventh.model.*;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;

import static edition.academy.seventh.repository.BookParser.parseBookstoreBookListIntoDTBookList;
import static edition.academy.seventh.repository.BookParser.parseDTBookIntoModel;
import static org.testng.Assert.*;

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
      String image,
      String bookstore) {

    // Given
    DTBook dtBook =
        new DTBook(
            title, subtitle, author, retailPrice, promotionalPrice, image, hyperLink, bookstore);
    BookstoreBook expectedBookstoreBook =
        new BookstoreBook(
            new Book(subtitle, new BookId(title, author)),
            retailPrice,
            promotionalPrice,
            LocalDate.now(),
            new HrefAndImage(hyperLink, image),
            new Bookstore(bookstore));

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
      String image,
      String hyperLink,
      String bookstore) {

    // Given
    BookstoreBook bookstoreBook =
        new BookstoreBook(
            new Book(subtitle, new BookId(title, author)),
            retailPrice,
            promotionalPrice,
            LocalDate.now(),
            new HrefAndImage(hyperLink, image),
            new Bookstore(bookstore));

    DTBook dtBook =
        new DTBook(
            title, subtitle, author, retailPrice, promotionalPrice, image, hyperLink, bookstore);

    List<BookstoreBook> bookstoreBooks = List.of(bookstoreBook);

    List<DTBook> expectedDTBooks = List.of(dtBook);

    // When
    List<DTBook> dtBooks = parseBookstoreBookListIntoDTBookList(bookstoreBooks);

    // Then
    assertEquals(dtBooks, expectedDTBooks);
  }
}
